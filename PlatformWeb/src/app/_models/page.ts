i/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/3/17.
 */
export class Page<T> {

    /**
     * Returns the page content as List.
     */
    content: Array<T>;

    /**
     * Returns whether the current Slice is the first one.
     */
    first: boolean;

    /**
     * Returns whether the current Slice is the last one.
     */
    last: boolean;

    /**
     * Returns the number of the current Slice. Is always non-negative.
     */
    number: number;

    /**
     * Returns the number of elements currently on this Slice.
     */
    numberOfElements: number;

    /**
     * Returns the size of the Slice.
     */
    size: number;

    /**
     * Returns the sorting parameters for the Slice.
     */
    sort: Array<Sort>;

    /**
     * Returns the total amount of elements.
     */
    totalElements: number;

    /**
     * Returns the number of total pages.
     */
    totalPages: number;


    constructor(content: Array<T>, first: boolean, last: boolean, number: number, numberOfElements: number,
        size: number,
        sort: Array<Sort>, totalElements: number, totalPages: number)
    {
        this.content = content;
        this.first = first;
        this.last = last;
        this.number = number;
        this.numberOfElements = numberOfElements;
        this.size = size;
        this.sort = sort;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }


    static createEmpty<T>(): Page<T> {
        return new Page(new Array<T>(), false, false, 0, 0, 0, new Array<Sort>(), 0, 0);
    }
}
