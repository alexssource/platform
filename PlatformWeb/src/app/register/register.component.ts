@Component({
    templateUrl: 'register.component.html',
    styleUrls: ['register.component.css'],
    providers: [UserService]
})
export class RegisterComponent implements OnInit {
    loading = false;
    registerForm: FormGroup;
    submitted: boolean = false;
    isEmpty: boolean = false;
    returnUrl: string;
    private successRegisterMessage: string = 'Регистрация успешна! Вам на почту выслано письмо, которое содержит ваш пароль для входа на сайт...';
    private errorRegisterMessage: string = 'Возникла ошибка при регистрации! Возможно вы уже зарегистрированы.<br/> Пожалуйста, проверьте почту!';


    constructor(private route: ActivatedRoute, private router: Router, private authenticationService: UserService,
        private formBuilder: FormBuilder, private modalService: ModalService)
    {
        this.registerForm = this.formBuilder.group({
            email: ['', [Validators.required, Validators.pattern(ValidationRules.EMAIL_REGEXP)]]
        });
    }


    ngOnInit() {
        this.returnUrl = this.route.snapshot.params['returnUrl'] || '/login';
    }


    register(): void {
        this.loading = true;
        let email = this.registerForm.value.email;
        this.authenticationService.register(email)
            .subscribe(
                (result) => {
                    if (result) {
                        this.modalService.info(this.successRegisterMessage);
                    } else {
                        this.modalService.error(this.errorRegisterMessage);
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
        this.isEmpty = (this.registerForm.value.email == '') ? this.isEmpty = true : this.isEmpty = false;
    }
}
