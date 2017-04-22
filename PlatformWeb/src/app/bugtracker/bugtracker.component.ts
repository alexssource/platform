@Component({
    selector: 'app-bugtracker',
    templateUrl: './bugtracker.component.html',
    styleUrls: ['./bugtracker.component.css']
})
export class BugtrackerComponent implements OnInit {
    private BugType = BugType;
    private BugStatus = BugStatus;
    private Role = Role;

    private menuItems: MenuItem[];
    private selectedBugsType: BugtrackerMenuType = BugtrackerMenuType.RESOLVED;
    private bugForm: BugForm;
    private bugCommentForm: BugCommentForm;
    private pageable: Pageable;
    private bugsPage: Page<Bug> = Page.createEmpty<Bug>();
    private commentsPageable: Pageable;
    private commentsEntry: BugCommentsEntry;
    private inplaceCommentEntry: InplaceCommentEntry;
    private inplaceBugStatusEntry: InplaceBugStatusEntry;


    constructor(private _bugtrackerService: BugtrackerService, private _modalService: ModalService,
        private _userService: UserService,
        private _confirmationService: ConfirmationService)
    {
        this.init();
    }


    private init(): void {
        this.bugForm = new BugForm();
        this.bugCommentForm = new BugCommentForm();
        this.pageable = new Pageable();
        this.commentsPageable = new Pageable();
        this.inplaceCommentEntry = new InplaceCommentEntry();
        this.inplaceBugStatusEntry = new InplaceBugStatusEntry();
    }


    ngOnInit() {
        this.menuItems = [
            {
                label: 'Активные задачи', command: (event) => {
                this.selectedBugsType = BugtrackerMenuType.ACTIVE;
                this.init();
                this.loadBugs();
            }
            },
            {
                label: 'Выполненные задачи', command: (event) => {
                this.selectedBugsType = BugtrackerMenuType.RESOLVED;
                this.init();
                this.loadBugs();
            }
            },
            {
                label: 'Создать новую задачу', icon: 'fa-plus', command: (event) => {
                this.showCreateBugForm();
            }
            }
        ];
        this.loadBugs();
    }


    showCreateBugForm(): void {
        jQuery('#registerBug').modal('toggle');
    }


    showEditBugForm(bug: Bug): void {
        this.bugForm = new BugForm(bug.id, bug.name, bug.description, bug.type.toString());
        this.showCreateBugForm();
    }


    showEditCommentForm(comment: BugComment): void {
        this.inplaceCommentEntry = new InplaceCommentEntry(comment.id, comment.comment);
    }


    showEditBugStatusForm(bug: Bug): void {
        this.inplaceBugStatusEntry = new InplaceBugStatusEntry(bug.id, bug.status);
    }


    getBugTypes(): string[] {
        return EnumHelper.getNames(BugType);
    }


    getBugStatuses(): string[] {
        return EnumHelper.getNames(BugStatus);
    }


    saveBug(): void {
        let bugObservable: Observable<Bug>;

        if (this.bugForm.id != null) {
            bugObservable = this._bugtrackerService.edit(this.bugForm);
        } else {
            bugObservable = this._bugtrackerService.create(this.bugForm);
        }

        bugObservable.subscribe(
            (bug: Bug) => {
                this.selectedBugsType = BugtrackerMenuType.ACTIVE;
                this.init();
                this.loadBugs();
            },
            (error: any) => {
                let platformError: PlatformError;
                if (error.status == 400) {
                    let code = "error.general";
                    let message = "Некоторые значения введены неверно.<br />Пожалуйста, проверьте правильность введенных значений.";
                    platformError = new PlatformError(code, message);
                } else {
                    platformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                }
                this._modalService.error(platformError.message);
            }
        );
        jQuery('#registerBug').modal('toggle');
    }


    createComment(bugId: number): void {
        this.bugCommentForm.bugId = bugId;
        this._bugtrackerService.createComment(this.bugCommentForm)
            .subscribe(
                (bugComment: BugComment) => {
                    this.bugCommentForm = new BugCommentForm();
                    this.loadComments(bugId);
                    let bug = this.findBugByBugId(bugId);
                    bug.commentsCount = bug.commentsCount + 1;
                },
                (error: any) => {
                    let platformError: PlatformError;
                    if (error.status == 400) {
                        let code = "error.general";
                        let message = "Некоторые значения введены неверно.<br />Пожалуйста, проверьте правильность введенных значений.";
                        platformError = new PlatformError(code, message);
                    } else {
                        platformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                    }
                    this._modalService.error(platformError.message);
                }
            )
    }


    editComment(oldComment: BugComment): void {
        this._bugtrackerService.editComment(oldComment.id, oldComment.bugId, this.inplaceCommentEntry.comment)
            .subscribe(
                (bugComment: BugComment) => {
                    oldComment.comment = bugComment.comment;
                    this.inplaceCommentEntry = new InplaceCommentEntry();
                },
                (error: any) => {
                    let platformError: PlatformError;
                    if (error.status == 400) {
                        let code = "error.general";
                        let message = "Некоторые значения введены неверно.<br />Пожалуйста, проверьте правильность введенных значений.";
                        platformError = new PlatformError(code, message);
                    } else {
                        platformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                    }
                    this._modalService.error(platformError.message);
                }
            );
        jQuery('.ui-tooltip').remove();
    }


    editBugStatus(bug: Bug) {
        this._bugtrackerService.editBugStatus(bug.id, BugStatus[this.inplaceBugStatusEntry.status])
            .subscribe(
                (bugDto: Bug) => {
                    bug.status = bugDto.status;
                    this.inplaceBugStatusEntry = new InplaceBugStatusEntry();
                },
                (error: any) => {
                    let platformError: PlatformError;
                    if (error.status == 400) {
                        let code = "error.general";
                        let message = "Некоторые значения введены неверно.<br />Пожалуйста, проверьте правильность введенных значений.";
                        platformError = new PlatformError(code, message);
                    } else {
                        platformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                    }
                    this._modalService.error(platformError.message);
                }
            )
    }


    resetEditCommentForm(): void {
        this.inplaceCommentEntry = new InplaceCommentEntry();
        jQuery('.ui-tooltip').remove();
    }


    resetEditBugStatusForm(): void {
        this.inplaceBugStatusEntry = new InplaceBugStatusEntry();
    }


    removeComment(comment: BugComment): void {
        this._confirmationService.confirm({
            message: 'Вы действительно желаете удалить комментарий?',
            accept: () => {
                this._bugtrackerService.removeComment(comment.id)
                    .subscribe(
                        (res: Response) => {
                            if (res.ok) {
                                let index = this.commentsEntry.comments.content.indexOf(comment);
                                if (index > -1) {
                                    this.commentsEntry.comments.content.splice(index, 1);
                                    for (let bug of this.bugsPage.content) {
                                        if (bug.id == comment.bugId) {
                                            bug.commentsCount--;
                                            break;
                                        }
                                    }
                                } else {
                                    this.loadComments(this.commentsEntry.bugId);
                                }
                            } else {
                                this._modalService.error("Возникла ошибка при удалении комментария");
                            }
                        },
                        (error: any) => {
                            let platformError: PlatformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                            this._modalService.error(platformError.message);
                        }
                    )
            }
        })
    }


    loadBugs(): void {
        let bugsPagePromise: Observable<Page<Bug>>;

        if (BugtrackerMenuType.ACTIVE == this.selectedBugsType) {
            bugsPagePromise = this._bugtrackerService.loadActiveBugs(this.pageable);
        } else {
            bugsPagePromise = this._bugtrackerService.loadResolvedBugs(this.pageable);
        }

        bugsPagePromise.subscribe(
            (page: Page<Bug>) => {
                this.bugsPage = page;
                this.pageable = new Pageable(page.number, page.size, page.totalPages);
            }
        );
    }


    loadComments(bugId: number): void {
        this.commentsEntry = new BugCommentsEntry(bugId);
        this.commentsEntry.isLoading = true;

        this._bugtrackerService.loadBugComments(bugId, this.commentsPageable)
            .subscribe(
                (page: Page<BugComment>) => {
                    this.commentsEntry.comments = page;
                    this.commentsPageable = new Pageable(page.number, page.size, page.totalPages);
                    this.commentsEntry.isLoading = false;
                },
                (error: any) => {
                    this.commentsEntry.isLoading = false;
                    let platformError: PlatformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                    this._modalService.error(platformError.message);
                }
            )
    }


    vote(bugId: number): void {
        let bug = this.findBugByBugId(bugId);

        if (bug.hasVoted) {
            return;
        }

        this._bugtrackerService.vote(bugId)
            .subscribe(
                (res: boolean) => {
                    if (res) {
                        let bug = this.findBugByBugId(bugId);
                        bug.hasVoted = true;
                        ++bug.votesCount;
                    } else {
                        this._modalService.error("Возникла ошибка. Возможно вы уже проголосовали")
                    }
                },
                (error: any) => {
                    let platformError: PlatformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                    this._modalService.error(platformError.message);
                }
            )
    }


    changeBugsPage(pageable: Pageable) {
        this.pageable = pageable;
        this.loadBugs();
        this.commentsPageable = new Pageable();
    }


    changeCommentsPage(commentsPageable: Pageable, bugId: number) {
        this.commentsPageable = commentsPageable;
        this.loadComments(bugId);
    }


    canShowComments(bugId: number): boolean {
        if (this.commentsEntry == null) {
            return false;
        }

        return this.commentsEntry.bugId == bugId;
    }


    isLoadingComments(bugId: number): boolean {
        if (this.commentsEntry == null) {
            return false;
        }

        return this.commentsEntry.isLoading;
    }


    findBugByBugId(bugId: number): Bug {
        return this.bugsPage.content.find(bug => bug.id == bugId);
    }


    getUser(): User {
        return this._userService.currentUser();
    }


    getHtmlMultiLineText(plainText: string): string {
        return plainText.replace(new RegExp('\r?\n', 'g'), '<br />');
    }
}
