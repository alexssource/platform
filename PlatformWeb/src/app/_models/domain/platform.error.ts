/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 29.12.16.
 */
export class PlatformError {
    code: string;
    message: string;


    constructor(code: string, message: string) {
        this.code = code;
        this.message = message;
    }
}
