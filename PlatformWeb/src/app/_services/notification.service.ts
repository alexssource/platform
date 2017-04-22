@Injectable()
export class NotificationService {
    /**
     * Массив сообщений внутреннего типа Growl-модуля
     *
     * @type {Array}
     */
    private _messages: Message[] = [];

    /**
     * Если true - то сообщения не будут автоматически закрываться
     *
     * @type {boolean}
     */
    private _sticky: boolean = true;

    /**
     * Количество миллисекунд, по истечении которого сообщения закроются,
     * если не установлен в true флаг _sticky
     *
     * @type {number}
     */
    private _lifetime: number = 3000;


    constructor() {
    }


    info(message: string, description?: string, sticky?: boolean): void {
        if (sticky != null) {
            this._sticky = sticky.valueOf();
        }
        this._messages.push({ severity: 'info', summary: message, detail: description });
    }


    success(message: string, description?: string, sticky?: boolean): void {
        if (sticky != null) {
            this._sticky = sticky.valueOf();
        }
        this._messages.push({ severity: 'success', summary: message, detail: description });
    }


    warn(message: string, description?: string, sticky?: boolean): void {
        if (sticky != null) {
            this._sticky = sticky.valueOf();
        }
        this._messages.push({ severity: 'warn', summary: message, detail: description });
    }


    error(message: string, description?: string, sticky?: boolean): void {
        if (sticky != null) {
            this._sticky = sticky.valueOf();
        }
        this._messages.push({ severity: 'error', summary: message, detail: description });
    }


    clear(): void {
        this._messages = [];
    }


    get messages(): Message[] {
        return this._messages;
    }


    get sticky(): boolean {
        return this._sticky;
    }


    get lifetime(): number {
        return this._lifetime;
    }
}
