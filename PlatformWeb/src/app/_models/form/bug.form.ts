/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/1/17.
 */
export class BugForm {
    id: number;
    name: string;
    description: string;
    type: string;


    constructor(id: number = null, name: string = null, description: string = null,
        type: string = BugType[BugType.BUG])
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
    }
}
