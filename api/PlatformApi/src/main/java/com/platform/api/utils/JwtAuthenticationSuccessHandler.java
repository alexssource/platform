package com.platform.api.utils;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException
    {
        // We do not need to do anything extra on REST authentication success, because there is no page to redirect to
    }
}
