@Injectable()
export class AuthGuard implements CanActivate {

    constructor(protected router: Router, private localStorageService: LocalStorageService) {
    }


    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if (this.localStorageService.isExpiredToken()) {
            this.localStorageService.deleteUser();
        } else {
            this.localStorageService.updateTokenExpiration();
            return true;
        }

        if (this.localStorageService.isExistUser()) {
            // logged in so return true
            return true;
        }

        // not logged in so redirect to login page with the return url
        this.router.navigate(['/login', { returnUrl: state.url }]);
        return false;
    }

    logout(): void {
        this.localStorageService.deleteUser();
        this.router.navigate(['/login']);
    }

    isLoggedIn(): boolean {
        return this.localStorageService.isExistUser();
    }

}
