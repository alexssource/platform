package com.platform.api.utils;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtTokenValidator jwtTokenValidator;

    @Autowired
    private TraderService traderService;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException
    {
    }


    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException
    {
        UserAuthenticationToken userAuthenticationToken = (UserAuthenticationToken) authentication;
        String token = userAuthenticationToken.getToken();

        UserDto parsedUser = jwtTokenValidator.parseToken(token);

        if (parsedUser == null) {
            throw new JwtTokenMalformedException("JWT token is not valid");
        }

        try {
            traderService.validateAndUpdateUserExpiredTime(parsedUser.getId());
        } catch (PlatformException e) {
            throw new JwtTokenMalformedException(e.getMessage());
        }

        return parsedUser;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return (UserAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
