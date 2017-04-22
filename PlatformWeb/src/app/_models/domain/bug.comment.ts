/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/4/17.
 */
export class BugComment {
    id: number;
    bugId: number;
    comment: string;
    owner: string;
    createdDate: Date;


    constructor(id: number, bugId: number, comment: string, owner: string, createdDate: Date) {
        this.id = id;
        this.bugId = bugId;
        this.comment = comment;
        this.owner = owner;
        this.createdDate = createdDate;
    }
}
