/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/7/17.
 */
export class InplaceBugStatusEntry {
    bugId: number;
    status: BugStatus;


    constructor(bugId: number = null, status: BugStatus = null) {
        this.bugId = bugId;
        this.status = status;
    }
}
