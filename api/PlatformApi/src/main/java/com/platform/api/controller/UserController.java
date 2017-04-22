package com.platform.api.controller;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
@RestController
@RequestMapping("/api/auth")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private TraderService traderService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserDto login(@RequestBody @Valid LoginCommand loginCommand) throws PlatformException {
        Trader trader = traderService.login(loginCommand.getEmail(), loginCommand.getPassword());

        logger.info("Trying to login user with email {}", loginCommand.getEmail());

        if (trader == null) {
            throw new BadCredentialsException("Could not find trader with specified email and password");
        }

        UserDto user = createUserDto(trader);
        user = checkCredentialsAndAuthorize(trader, user);

        logger.info("User with email {} has been logged in successfully", trader.getEmail());
        return user;
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public boolean registration(@RequestBody @Valid RegistrationCommand registrationCommand) {
        logger.info("Registration of new user: {}", registrationCommand.getEmail());
        boolean res = traderService.register(registrationCommand.getEmail());

        if (!res) {
            logger.warn("Failed to register a new user with email {}", registrationCommand.getEmail());
        } else {
            logger.info("User with email {} has been registered successfully", registrationCommand.getEmail());
        }

        return res;
    }


    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public boolean changePassword(@RequestBody @Valid RegistrationCommand registrationCommand) {
        logger.info("Change password for user: {}", registrationCommand.getEmail());
        boolean res = traderService.changePassword(registrationCommand.getEmail());

        if (!res) {
            logger.warn("Failed to password change for user with email {}", registrationCommand.getEmail());
        } else {
            logger.info("Password for user with email {} has been changed successfully",
                    registrationCommand.getEmail());
        }

        return res;
    }


    @Authorized
    @RequestMapping("/refresh")
    public UserDto updateToken() {
        UserDto user = ContextHolder.getCurrentUser();
        logger.info("Refreshing token for user: {}", user.getUsername());

        Trader trader = traderService.findById(user.getId());
        user = createUserDto(trader);
        user = checkCredentialsAndAuthorize(trader, user);

        return user;
    }


    @Authorized
    @RequestMapping("/profile")
    public ProfileDto getProfile() {
        Profile profile = traderService.getProfile(ContextHolder.getCurrentUser().getId());
        return new ProfileDto(profile.isCanReceiveGeneralMailing(), profile.isCanReceiveBugFixesMailing(),
                profile.isCanReceiveNewTasksMailing());
    }


    @Authorized
    @RequestMapping(value = "/profile/settings/mailing/update", method = RequestMethod.POST)
    public void updateProfileMailingSettings(@RequestBody @Valid UpdateProfileMailingSettingsCommand command,
            BindingResult bindingResult) throws PlatformFieldValidationException
    {
        if (bindingResult.hasErrors()) {
            throw new PlatformFieldValidationException(ValidationErrorHelper.createPlatformFieldErrors(bindingResult));
        }

        traderService.updateMailingSettings(command.isCanReceiveGeneralMailing(), command.isCanReceiveBugFixesMailing(),
                command.isCanReceiveNewTasksMailing());
    }


    private UserDto checkCredentialsAndAuthorize(Trader trader, UserDto user) {
        try {
            authorize(user);
        } catch (CredentialsExpiredException ex) {
            logger.info("Credentials for user {} with role {} has expired. Expiration date is: {}", trader.getEmail(),
                    trader.getRole(), trader.getCredentialsExpirationDate());
            logger.info("Changing role for user {} to {}", trader.getEmail(), Role.ROLE_USER.name());

            trader = traderService.updateRole(trader, Role.ROLE_USER);
            user = createUserDto(trader);

            authorize(user);
        }

        return user;
    }


    private void authorize(UserDto user) throws CredentialsExpiredException {
        UserAuthenticationToken userAuthenticationToken = new UserAuthenticationToken(user.getToken(), user,
                user.getRole().name(),
                user.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(userAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private UserDto createUserDto(Trader trader) {
        UserDto user = new UserDto(trader);
        String token = jwtTokenGenerator.generateToken(user, JwtTokenValidator.secret);
        user.setToken(token);
        return user;
    }
}
