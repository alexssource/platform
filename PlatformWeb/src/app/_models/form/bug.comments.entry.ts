**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/4/17.
 */
export class BugCommentsEntry {
    bugId: number;
    comments: Page<BugComment>;
    isLoading: boolean;


    constructor(bugId: number) {
        this.bugId = bugId;
        this.comments = Page.createEmpty<BugComment>();
        this.isLoading = false;
    }


    addComment(comment: BugComment): void {
        this.comments.content.push(comment);
    }
}
