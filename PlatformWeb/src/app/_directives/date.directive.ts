import { Directive, OnInit, Input, ElementRef, OnChanges } from "@angular/core";
import { LocalStorageService } from "../_services/local.storage.service";


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/3/17.
 */
@Directive({
    selector: '[date]',
})
export class DateDirective implements OnInit, OnChanges {

    @Input()
    date: any;

    constructor(private el: ElementRef, private _localStorageService: LocalStorageService) {
    }


    ngOnInit() {
        let locale: string = this._localStorageService.getLocale();
        this.el.nativeElement.textContent = this.date.toLocaleString(locale).replace(',', '');
    }

    ngOnChanges() {
    }
}
