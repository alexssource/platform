/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 02.03.17.
 */
export class Pageable {
    page: number;
    size: number;
    sort: string[];
    totalPages: number;


    constructor(page: number = null, size: number = null, totalPages: number = 0, sort: string[] = null) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.sort = sort;
    }


    populateUrlSearchParams(params: URLSearchParams): URLSearchParams {
        if (this.page != null) {
            params.append("page", this.page.toString());
        }

        if (this.size != null) {
            params.append("size", this.size.toString());
        }

        if (this.sort != null && this.sort.length > 0) {
            let sortString = "";
            for (let i = 0; i < this.sort.length; i++) {
                sortString = sortString.concat(this.sort[i]);
                if (i != this.sort.length - 1) {
                    sortString = sortString.concat(",");
                }
            }
            params.append("sort", sortString);
        }

        return params;
    }
}
