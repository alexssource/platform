import { BugType } from "../enum/bug.type";
import { BugStatus } from "../enum/bug.status";
import { Trader } from "./trader";


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 02.03.17.
 */
export class Bug {
    id: number;
    name: string;
    description: string;
    status: BugStatus;
    type: BugType;
    createdDate: Date;
    resolvedDate: Date;
    user: Trader;
    votesCount: number;
    commentsCount: number;
    hasVoted: boolean;


    constructor(id: number, name: string, description: string, status: BugStatus, type: BugType, createdDate: Date,
        resolvedDate: Date, user: Trader, votesCount: number, commentsCount: number, hasVoted: boolean)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
        this.createdDate = createdDate;
        this.resolvedDate = resolvedDate;
        this.user = user;
        this.votesCount = votesCount;
        this.commentsCount = commentsCount;
        this.hasVoted = hasVoted;
    }
}
