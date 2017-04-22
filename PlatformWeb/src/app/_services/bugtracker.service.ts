i@Injectable()
export class BugtrackerService {

    constructor(private http: HttpClient) {
    }


    create(bug: BugForm): Observable<Bug> {
        let body = JSON.stringify(bug);

        return this.http.post('/api/bug/create', body)
                   .map((res: Response) => JsonDeserializer.createType(res.json(), Domain.BUG));
    }


    edit(bug: BugForm): Observable<Bug> {
        let body = JSON.stringify(bug);

        return this.http.post('/api/bug/edit', body)
                   .map((res: Response) => JsonDeserializer.createType(res.json(), Domain.BUG));
    }


    createComment(bugComment: BugCommentForm): Observable<BugComment> {
        let body = JSON.stringify(bugComment);
        return this.http.post('/api/bug/comments/create', body)
                   .map((res: Response) => JsonDeserializer.createType(res.json(), Domain.BUG_COMMENT));
    }


    editComment(id: number, bugId: number, comment: string): Observable<BugComment> {
        let body = {
            id: id,
            bugId: bugId,
            comment: comment
        };

        return this.http.post('/api/bug/comments/edit', body)
                   .map((res: Response) => JsonDeserializer.createType(res.json(), Domain.BUG_COMMENT));
    }


    editBugStatus(id: number, status: string): Observable<Bug> {
        let body = {
            bugId: id,
            bugStatus: status
        };

        return this.http.post('/api/bug/updateStatus', body)
                   .map((res: Response) => JsonDeserializer.createType(res.json(), Domain.BUG));
    }


    removeComment(id: number): Observable<Response> {
        return this.http.get(`/api/bug/comments/${id}/delete`)
                   .map((res: Response) => res);
    }


    loadActiveBugs(pageable: Pageable): Observable<Page<Bug>> {
        let params = new URLSearchParams();
        params = pageable.populateUrlSearchParams(params);

        return this.http.get('/api/bug/active', params)
                   .map((res: Response) => JsonDeserializer.createPage<Bug>(res.json(), Domain.BUG));
    }


    loadResolvedBugs(pageable: Pageable): Observable<Page<Bug>> {
        let params = new URLSearchParams();
        params = pageable.populateUrlSearchParams(params);

        return this.http.get('/api/bug/resolved', params)
                   .map((res: Response) => JsonDeserializer.createPage<Bug>(res.json(), Domain.BUG));
    }


    loadBugComments(bugId: number, pageable: Pageable): Observable<Page<BugComment>> {
        let params = new URLSearchParams();
        params = pageable.populateUrlSearchParams(params);

        return this.http.get(`/api/bug/${bugId}/comments`, params)
                   .map((res: Response) => JsonDeserializer.createPage<BugComment>(res.json(), Domain.BUG_COMMENT));
    }


    vote(bugId: number): Observable<boolean> {
        return this.http.get(`/api/bug/${bugId}/vote`)
                   .map((res: Response) => res.text() == 'true');
    }


    getRecentlyFixedBugs(days: number): Observable<Array<Bug>> {
        return this.http.get(`/api/bug/fixed/last/${days}/days`)
            .map((res: Response) => JsonDeserializer.createArray<Bug>(res.json(), Domain.BUG));
}


    getRecentlyCreatedBugs(days: number): Observable<Array<Bug>> {
        return this.http.get(`/api/bug/created/last/${days}/days`)
            .map((res: Response) => JsonDeserializer.createArray<Bug>(res.json(), Domain.BUG));
    }
}
