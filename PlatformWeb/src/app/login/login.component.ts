@Component({
    templateUrl: 'login.component.html',
    styleUrls: ['login.component.css']
})
export class LoginComponent implements OnInit {
    loading = false;
    returnUrl: string;
    loginForm: FormGroup;
    submitted: boolean = false;


    constructor(private route: ActivatedRoute, private router: Router, private authenticationService: UserService,
        private formBuilder: FormBuilder, private auth: AuthGuard,
        private modalService: ModalService)
    {

        this.loginForm = this.formBuilder.group({
            email: ['', [Validators.required, Validators.pattern(ValidationRules.EMAIL_REGEXP)]],
            password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(8)]]
        });
    }


    ngOnInit() {
        if (this.auth.isLoggedIn()) {
            this.router.navigate(['/bugtracker']);
            return;
        }
        this.returnUrl = this.route.snapshot.params['returnUrl'] || '/bugtracker';
    }


    login(): void {
        this.loading = true;

        this.authenticationService.login(this.loginForm.value.email, this.loginForm.value.password)
            .subscribe(
                (user: User) => {
                    this.router.navigate([this.returnUrl || '/bugtracker']);
                },
                (error) => {
                    if (error.status != 401) {
                        let platformError: PlatformError = JsonDeserializer.createType(JSON.parse(error._body), Domain.PLATFORM_ERROR);
                        this.modalService.error(platformError.message);
                    }
                }
            )
    }


    setSubmitted(submitted: boolean): void {
        this.submitted = submitted;
    }


}
