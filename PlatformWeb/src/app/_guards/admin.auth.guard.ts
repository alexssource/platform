import { AuthGuard } from "./auth.guard";
import { UserService } from "../_services/user.service";
import { LocalStorageService } from "../_services/local.storage.service";
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { Injectable } from "@angular/core";


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 25.03.17.
 */
@Injectable()
export class AdminAuthGuard extends AuthGuard {
    constructor(router: Router, localStorageService: LocalStorageService, private userService: UserService) {
        super(router, localStorageService);
    }


    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        if (!super.canActivate(route, state)) {
            this.router.navigate(['/login', { returnUrl: state.url }]);
            return false;
        }

        let user = this.userService.currentUser();

        if (!user.isAdmin()) {
            this.router.navigate(['/profile']);
            return false;
        }

        return true;
    }
}