@Injectable()
export class HttpClient {
    baseUrl: string;

    constructor(private http: Http, private localStorageService: LocalStorageService, private auth: AuthGuard) {
        let protocol: string = window.location.protocol;
        let port: number;

        if ('https:' == protocol) {
            port = 8443;
        } else {
            port = 8080;
        }

        this.baseUrl = `${protocol}//${window.location.hostname}:${port}`;
    }


    createRequestHeaders(requestMethodType: string): RequestOptions {
        let headers: Headers = new Headers();
        let hasCurrentUser: boolean = this.localStorageService.isExistUser();
        let locale: string = this.localStorageService.getLocale();

        headers.append('Content-Type', 'application/json');

        if (hasCurrentUser) {
            headers.append('Authorization', 'Bearer ' + this.localStorageService.readUser().token);
        }

        if (locale) {
            headers.append('Accept-Language', locale);
        }

        let options: RequestOptions = new RequestOptions({
            method: requestMethodType,
            headers: headers,
            withCredentials: hasCurrentUser
        });

        return options;
    }


    get(url: any, params?: URLSearchParams): Observable<{}> {
        let requestOptions = this.createRequestHeaders('get');
        requestOptions.search = params;

        return this.http.get(`${this.baseUrl}${url}`, requestOptions)
                   .catch((err: any, caught: Observable<Response>) => {
                       if (err.status == 401 || err.status == 403) {
                           this.auth.logout();
                       }
                       throw err;
                   });
    }


    post(url: any, data: any): Observable<{}> {
        return this.http.post(`${this.baseUrl}${url}`, data, this.createRequestHeaders('post'))
                   .catch((err: any, caught: Observable<Response>) => {
                       if (err.status == 401 || err.status == 403) {
                           this.auth.logout();
                       }
                       throw err;
                   });
    }

}
