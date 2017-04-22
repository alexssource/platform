package com.platform.api.filter;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
public class ResponseHeadersFilter extends OncePerRequestFilter {

    private ConfigService configService;


    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        configService =
                WebApplicationContextUtils.getRequiredWebApplicationContext(getFilterConfig().getServletContext())
                        .getBean(ConfigService.class);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        response.setHeader("Access-Control-Allow-Origin", configService.getCorsAllowedHost());
        response.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }
}
