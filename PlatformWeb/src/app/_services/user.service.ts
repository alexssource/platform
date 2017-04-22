@Injectable()
export class UserService {

    constructor(private http: HttpClient, private localStorageService: LocalStorageService, private auth: AuthGuard) {
    }


    login(email: string, password: string): Observable<User> {
        let body = JSON.stringify({ email, password });

        return this.http.post('/api/auth/login', body)
                   .map((res: Response) => {
                       let user = JsonDeserializer.createType(res.json(), Domain.USER);

                       this.saveUser(user);
                       return user;
                   });
    }


    register(email: string): Observable<boolean> {
        let body = JSON.stringify({ email });

        return this.http.post('/api/auth/signup', body)
                   .map((res: Response) => res.json());
    }


    passwordChange(email: string): Observable<boolean> {
        let body = JSON.stringify({ email });

        return this.http.post('/api/auth/change-password', body)
                   .map((res: Response) => res.json());
    }


    refresh(): void {
        this.http.get('/api/auth/refresh')
            .map((res: Response) => {
                return JsonDeserializer.createType(res.json(), Domain.USER);
            })
            .subscribe((user: User) => {
                this.saveUser(user);
            });
    }


    getProfile(): Observable<Profile> {
        return this.http.get('/api/auth/profile')
            .map((res: Response) => {
                return JsonDeserializer.createType(res.json(), Domain.PROFILE);
            });
    }


    updateProfileMailingSettings(mailingSettings: MailingSettingsForm): Observable<{}> {
        let body = JSON.stringify(mailingSettings);
        return this.http.post('/api/auth/profile/settings/mailing/update', body);
    }


    private saveUser(user: User): void {
        if (user.token) {
            this.localStorageService.saveUser(user);
            this.localStorageService.updateTokenExpiration();
        }
    }


    logout(): void {
        this.auth.logout();
    }


    currentUser(): User {
        if (this.auth.isLoggedIn()) {
            return this.localStorageService.readUser();
        }
        return null;
    }
}
