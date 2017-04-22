/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/7/17.
 */
export class InplaceCommentEntry {
    commentId: number;
    comment: string;


    constructor(commentId: number = null, comment: string = null) {
        this.commentId = commentId;
        this.comment = comment;
    }
}
