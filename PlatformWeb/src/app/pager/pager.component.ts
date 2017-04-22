@Component({
    selector: 'app-pager',
    templateUrl: './pager.component.html',
    styleUrls: ['./pager.component.css']
})
export class PagerComponent implements OnInit {
    @Input()
    pageable: Pageable;

    @Input()
    cssClass: string;

    @Output()
    onPageChanged = new EventEmitter();


    constructor() {
    }


    ngOnInit() {
    }


    getPageRange(): number[] {
        let pageRange: number[] = [];
        for (let i = 0; i < this.pageable.totalPages; i++) {
            pageRange.push(i);
        }
        return pageRange;
    }


    changePage(page: number): void {
        this.pageable.page = page;
        this.onPageChanged.emit(this.pageable);
    }
}
