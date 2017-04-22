@Component({
    selector: 'app-password-changed',
    templateUrl: './password-change.component.html',
    styleUrls: ['./password-change.component.css']
})
export class PasswordChangeComponent implements OnInit {
    returnUrl: string;
    passwordChangeForm: FormGroup;
    loading = false;
    isEmpty: boolean = false;
    submitted: boolean = false;
    private successPasswordChangeMessage: string = 'Смена пароля успешна! Вам на почту выслано письмо, которое содержит ваш новый пароль для входа на сайт...';
    private errorPasswordChangeMessage: string = 'Возникла ошибка при смене пароля!';


    constructor(private route: ActivatedRoute, private router: Router, private authenticationService: UserService,
        private formBuilder: FormBuilder, private modalService: ModalService)
    {
        this.passwordChangeForm = this.formBuilder.group({
            email: ['', [Validators.required, Validators.pattern(ValidationRules.EMAIL_REGEXP)]]
        });
    }


    ngOnInit() {
        this.returnUrl = this.route.snapshot.params['returnUrl'] || '/login';
    }


    changePassword(): void {
        this.loading = true;
        let email = this.passwordChangeForm.value.email;
        this.authenticationService.passwordChange(email)
            .subscribe(
                (result) => {
                    if (result) {
                        this.modalService.info(this.successPasswordChangeMessage);
                    } else {
                        this.modalService.error(this.errorPasswordChangeMessage);
                    }

                    this.router.navigate([this.returnUrl]);
                },
                (error) => {
                    let platformError: PlatformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                    this.modalService.error(platformError.message);
                }
            );
    }


    setSubmitted(submitted: boolean): void {
        this.submitted = submitted;
        this.isEmpty = (this.passwordChangeForm.value.email == '') ? this.isEmpty = true : this.isEmpty = false;
    }
}
