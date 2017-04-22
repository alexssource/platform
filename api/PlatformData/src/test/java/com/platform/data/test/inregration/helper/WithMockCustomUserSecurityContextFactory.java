package com.platform.data.test.inregration.helper;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 27.12.16.
 */
public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Autowired
    private TraderRepository traderRepository;


    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Trader trader = traderRepository.findByEmail(customUser.username());

        UserDto principal = new UserDto(trader.getId(), trader.getEmail(), "Bearer token", true, false,
                false, ZonedDateTime.now(), Role.ROLE_USER);

        Authentication auth = new UsernamePasswordAuthenticationToken(principal, principal.getRole().name(),
                principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
