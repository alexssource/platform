/**
 * @Deprecated use NotificationComponent instead
 */
@Component({
    selector: 'platform-modal',
    templateUrl: 'modal.component.html',
    styleUrls: ['modal.component.css']
})
export class ModalComponent implements OnInit {

    constructor(private modalService: ModalService) {
    }


    ngOnInit() {
    }


    public getHeader(): string {
        return this.modalService.header;
    }


    public getMessage(): string {
        return this.modalService.message;
    }


    public getCanShowFooter(): boolean {
        return this.modalService.showFooter;
    }


    public getBackgroundColorClass(): string {
        return this.modalService.backgroundColorClass;
    }
}
