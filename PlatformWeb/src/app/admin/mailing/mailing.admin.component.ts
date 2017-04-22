@Component({
    selector: 'app-mailing-admin',
    templateUrl: 'mailing.admin.component.html',
    styleUrls: ['mailing.admin.component.css']
})
export class MailingAdminComponent implements OnInit {
    private EmailTemplateType = EmailTemplateType;
    private static INCLUDE_STATEMENT_REGEXP: string =  "\\${#include\\('([A-Z_]+)', (\\d+)\\)}";
    private static BUG_NAME_STATEMENT_REGEXP: string =  "\\${bug\\.name}";
    private static BUG_OWNER_STATEMENT_REGEXP: string =  "\\${bug\\.owner}";
    private static BUG_RESOLVED_DATE_STATEMENT_REGEXP: string =  "\\${bug\\.resolvedDate}";
    private static BUG_CREATED_DATE_STATEMENT_REGEXP: string =  "\\${bug\\.createdDate}";

    private type: string = EmailTemplateType[EmailTemplateType.MAILING_LAYOUT];
    private message: string = "";
    private loading: boolean = false;

    constructor(private _mailingService: MailingAdminService, private _notificationService: NotificationService,
        private _bugtrackerService: BugtrackerService, private _confirmationService: ConfirmationService) {
    }


    ngOnInit() {
    }


    getTemplateTypes(): string[] {
        return EnumHelper.getNames(EmailTemplateType);
    }


    loadTemplate(): void {
        this._mailingService.loadTemplateByType(this.type)
            .subscribe(
                (template: EmailTemplateAdmin) => {
                    this.message = template.template;
                },
                (error: any) => {
                    let platformError: PlatformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                    this._notificationService.warn(platformError.message);
                }
            )
    }


    preprocessor(): void {
        let includingParams: Array<string> = this.message.match(
            new RegExp(MailingAdminComponent.INCLUDE_STATEMENT_REGEXP));

        if (includingParams == null) {
            return;
        }

        if (includingParams != null && includingParams.length > 0) {
            if (EmailTemplateType[EmailTemplateType.BUGTRACKER_BUG_FIXES] == includingParams[1]) {
                this.preprocessBugFixes(includingParams).then((response) => {
                    this.preprocessor();
                });
            } else if (EmailTemplateType[EmailTemplateType.BUGTRACKER_NEW_TASKS] == includingParams[1]) {
                this.preprocessNewTasks(includingParams).then((response) => {
                    this.preprocessor();
                });
            }
        }
    }


    clipboardCopied(): void {
        this._notificationService.info('Скопировано', 'Текст был скопирован в буфер обмена', false);
    }


    sendEmails(): void {
        this._confirmationService.confirm({
            header: 'Подумай получше',
            message: 'Ты хорошо подумал?',
            accept: () => {
                this.loading = true;
                this._mailingService.sendEmails(this.message)
                    .subscribe(
                        (res: Response) => {
                            this.loading = false;
                            this._notificationService.warn("Рассылка отправлена адресатам", null, false);
                        },
                        (error: any) => {
                            this.loading = false;
                            let platformError: PlatformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                            this._notificationService.error(platformError.message, "Возможно почта отошла части адресатов");
                        }
                    )
            }
        });
    }


    private preprocessBugFixes(includingParams: Array<string>): Promise<Array<Bug>> {
        if (includingParams.length != 3) {
            this._notificationService.warn('Ошибка препроцессора',
                'Вызов препроцессора для шаблона BUGTRACKER_BUG_FIXES должел содержать 2 параметра');
            return;
        }

        return new Promise((resolve, reject) => {
            this._mailingService.loadTemplateByType(EmailTemplateType[EmailTemplateType.BUGTRACKER_BUG_FIXES])
                .subscribe(
                    (template: EmailTemplateAdmin) => {
                        let bugTemplate: string = template.template;

                        this._bugtrackerService.getRecentlyFixedBugs(Number.parseInt(includingParams[2]))
                            .subscribe(
                                (bugs: Array<Bug>) => {
                                    let bugsTemplate: string = "";
                                    let bugNameRegExp: RegExp = new RegExp(MailingAdminComponent.BUG_NAME_STATEMENT_REGEXP, 'g');
                                    let bugResolvedDateRegExp: RegExp = new RegExp(MailingAdminComponent.BUG_RESOLVED_DATE_STATEMENT_REGEXP, 'g');

                                    for (let bug of bugs) {
                                        bugsTemplate = bugsTemplate + bugTemplate
                                                .replace(bugNameRegExp, bug.name)
                                                .replace(bugResolvedDateRegExp, bug.resolvedDate.toLocaleDateString());
                                    }

                                    this.message = this.message.replace(
                                        new RegExp(MailingAdminComponent.INCLUDE_STATEMENT_REGEXP), bugsTemplate);
                                    resolve(bugs);
                                },
                            );
                    },
                    (error: any) => {
                        let platformError: PlatformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                        this._notificationService.warn(platformError.message, 'Не удалось загрузить шаблон BUG_FIXES');
                        reject(error);
                    }
                );
        });
    }


    private preprocessNewTasks(includingParams: Array<string>): Promise<Array<Bug>> {
        if (includingParams.length != 3) {
            this._notificationService.warn('Ошибка препроцессора',
                'Вызов препроцессора для шаблона BUGTRACKER_NEW_TASKS должел содержать 2 параметра');
            return;
        }

        return new Promise((resolve, reject) => {
            this._mailingService.loadTemplateByType(EmailTemplateType[EmailTemplateType.BUGTRACKER_NEW_TASKS])
                .subscribe(
                    (template: EmailTemplateAdmin) => {
                        let bugTemplate: string = template.template;

                        this._bugtrackerService.getRecentlyCreatedBugs(Number.parseInt(includingParams[2]))
                            .subscribe(
                                (bugs: Array<Bug>) => {
                                    let bugsTemplate: string = "";
                                    let bugNameRegExp: RegExp = new RegExp(MailingAdminComponent.BUG_NAME_STATEMENT_REGEXP, 'g');
                                    let bugCreatedDateRegExp: RegExp = new RegExp(MailingAdminComponent.BUG_CREATED_DATE_STATEMENT_REGEXP, 'g');
                                    let bugOwnerRegExp: RegExp = new RegExp(MailingAdminComponent.BUG_OWNER_STATEMENT_REGEXP, 'g');

                                    for (let bug of bugs) {
                                        bugsTemplate = bugsTemplate + bugTemplate
                                                .replace(bugNameRegExp, bug.name)
                                                .replace(bugCreatedDateRegExp, bug.createdDate.toLocaleDateString())
                                                .replace(bugOwnerRegExp, bug.user.email);
                                    }

                                    this.message = this.message.replace(
                                        new RegExp(MailingAdminComponent.INCLUDE_STATEMENT_REGEXP), bugsTemplate);
                                    resolve(bugs);
                                },
                            );
                    },
                    (error: any) => {
                        let platformError: PlatformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                        this._notificationService.warn(platformError.message, 'Не удалось загрузить шаблон BUG_FIXES');
                        reject(error);
                    }
                );
        });
    }
}
