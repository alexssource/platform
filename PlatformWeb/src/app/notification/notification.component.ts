@Component({
    selector: 'app-notification',
    templateUrl: './notification.component.html'
})
export class NotificationComponent implements OnInit {

    constructor(private _notificationService: NotificationService) {
    }

    ngOnInit() {
    }


    getMessages(): Message[] {
        return this._notificationService.messages;
    }


    isSticky(): boolean {
        return this._notificationService.sticky;
    }


    getLifetime(): number {
        return this._notificationService.lifetime;
    }

}
