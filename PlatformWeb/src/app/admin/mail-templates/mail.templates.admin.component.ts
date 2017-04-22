@Component({
    selector: 'app-mail-templates-admin',
    templateUrl: 'mail.templates.admin.component.html',
    styleUrls: ['mail.templates.admin.component.css']
})
export class MailTemplatesAdminComponent implements OnInit {
    private EmailTemplateType = EmailTemplateType;

    private templates: Array<EmailTemplateAdmin>;

    constructor(private _mailingAdminService: MailingAdminService, private _notificationService: NotificationService) {
    }

    ngOnInit() {
        this._mailingAdminService.getEmailTemplates()
            .subscribe(
                (templates: Array<EmailTemplateAdmin>) => {
                    this.templates = templates;
                },
                (error: any) => {
                    let platformError: PlatformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                    this._notificationService.error(platformError.message, "Попробуйте повторить попытку позже", false);
                }
            );
    }


    getTemplateByType(type: EmailTemplateType): EmailTemplateAdmin {
        return this.templates.find(template => template.type == type);
    }


    saveEmailTemplates(): void {
        let entries: Array<EmailTemplateAdminEntry> = this.templates.map(
            (templateDomain) => {
                return new EmailTemplateAdminEntry(EmailTemplateType[templateDomain.type], templateDomain.template);
            }
        );

        let form: EmailTemplateAdminForm = new EmailTemplateAdminForm(entries);

        this._mailingAdminService.updateEmailTemplates(form)
            .subscribe(
                (res: Response) => {
                    this._notificationService.info("Настройки успешно сохранены!", null, false);
                },
                (error: any) => {
                    let platformError: PlatformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                    this._notificationService.error(platformError.message, "Попробуйте повторить попытку позже", false);
                }
            )
    }

}
