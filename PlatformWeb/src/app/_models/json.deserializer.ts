/**
 * Фабрика для создания доменных объектов из json
 *
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/18/17.
 */
export class JsonDeserializer {

    private static createTrader(json: any): Trader {
        return new Trader(json.id, json.email, json.enabled, new Date(json.createdAt * 1000), json.locked);
    }

    private static createUser(json: any): User {
        return new User(json.id, json.username, json.enabled, json.expired, json.locked,
            Role[json.role as string], json.authorities, json.token, json.accountNonExpired,
            json.accountNonLocked, new Date(json.credentialsExpirationDate * 1000), json.credentialsNonExpired);
    }

    private static createBug(json: any): Bug {
        return new Bug(json.id, json.name, json.description, BugStatus[json.status as string], BugType[json.type as string],
            new Date(json.createdDate * 1000), json.resolvedDate ? new Date(json.resolvedDate * 1000)
                : null, JsonDeserializer.createTrader(json.user),
            json.votesCount, json.commentsCount, json.hasVoted);
    }

    private static createBugComment(json: any): BugComment {
        return new BugComment(json.id, json.bugId, json.comment, json.owner, new Date(json.createdDate * 1000));
    }


    static createPage<T>(json: any, domain: Domain): Page<T> {
        let content: Array<T> = JsonDeserializer.createArray(json.content, domain) as Array<T>;

        return new Page(content, json.first, json.last, json.number, json.numberOfElements, json.size, json.sort,
            json.totalElements, json.totalPages) as Page<T>;
    }


    static createArray<T>(jsonArray: any, domain: Domain): Array<T> {
        let content: Array<T> = [];

        for (let record of jsonArray) {
            content.push(JsonDeserializer.createType(record, domain));
        }

        return content;
    }


    static createType<T>(json: any, domain: Domain): any {
        if (domain == Domain.TRADER) {
            return JsonDeserializer.createTrader(json);
        }

        if (domain == Domain.USER) {
            return JsonDeserializer.createUser(json);
        }


        throw new Error('Could not produce the specified type. Type is unknown');
    }

}
