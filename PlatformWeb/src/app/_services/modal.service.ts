@Injectable()
export class ModalService {
    message: string;
    header?: string;
    showFooter: boolean;
    backgroundColorClass: string;

    constructor() {
    }


    showModal(message: string, showFooter: boolean, header: string, backgroundColorClass: string): void {
        this.message = message;
        this.header = header;
        this.showFooter = showFooter;
        this.backgroundColorClass = backgroundColorClass;
        jQuery('#platformModal').modal('toggle');
    }

    close(): void {
        jQuery('#platformModal').hide();
    }

    error(message: string): void {
        this.showModal(message, false, null, 'modal-error')
    }

    info(message: string): void {
        this.showModal(message, false, null, 'modal-success');
    }
}
