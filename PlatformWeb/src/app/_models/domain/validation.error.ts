export class ValidationError extends PlatformError {
    field: string;


    constructor(field: string, code: string, message: string) {
        super(code, message);
        this.field = field;
    }
}
