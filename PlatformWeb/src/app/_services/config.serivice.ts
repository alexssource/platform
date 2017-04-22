/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 1/17/17.
 */
@Injectable()
export class ConfigService {

    constructor(private http: HttpClient) {
    }

    getConfig(): Observable<Config> {
        return this.http.get('/api/config')
                   .map((res: Response) => JsonDeserializer.createType(res.json(), Domain.CONFIG));
    }

}
