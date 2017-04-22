const appRoutes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'login', component: LoginComponent }

    // admin routes
    { path: 'control/mail-templates', component: MailTemplatesAdminComponent, canActivate: [AdminAuthGuard] },

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];


export const routing = RouterModule.forRoot(appRoutes);
