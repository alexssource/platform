package com.platform.data.test.inregration.service;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "development")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"classpath:beans.xml", "classpath:persistence.xml"})
public class UserServiceTestIntegration {

    private final String generatedPassword = "generated-password";

    private final String generatedEncodedPassword = "Enc0d3D";

    @Autowired
    private ContextHolder contextHolder;

    @Autowired
    private TraderService traderService;

    @Autowired
    private TraderRepository traderRepository;


    @Before
    public void init() {
        ContextHolder contextHolder = Mockito.mock(ContextHolder.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        Mockito.when(contextHolder.generatePassword()).thenReturn(generatedPassword);
        Mockito.when(passwordEncoder.encode(generatedPassword)).thenReturn(generatedEncodedPassword);
        Mockito.when(passwordEncoder.matches(generatedPassword, generatedEncodedPassword)).thenReturn(true);
        ReflectionTestUtils.setField(traderService, "contextHolder", contextHolder);
        ReflectionTestUtils.setField(traderService, "passwordEncoder", passwordEncoder);

        MailService mailService = Mockito.mock(MailService.class);
        Mockito.when(mailService.sendEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.any()))
                .thenReturn(true);
        ReflectionTestUtils.setField(traderService, "mailService", mailService);
        ReflectionTestUtils.setField(contextHolder, "traderService", traderService);
        ReflectionTestUtils.setField(traderService, "contextHolder", contextHolder);
    }


    @Test
    public void testRegister() {
        String email = UserTestHelper.generateUniqueEmail();

        boolean resultRegister = traderService.register(email);
        Trader trader = traderRepository.findByEmail(email);

        assertTrue(resultRegister);
        assertNotNull(trader);
        assertTrue(StringUtils.isNotEmpty(trader.getPassword()));
        assertEquals(generatedEncodedPassword, trader.getPassword());
    }


    @Test
    public void testRegisterShouldCreateAProfile() {
        String email = UserTestHelper.generateUniqueEmail();

        traderService.register(email);
        Trader trader = traderRepository.findByEmail(email);

        assertNotNull(trader);
        assertNotNull(trader.getProfile());
        assertEquals(trader.getId(), trader.getProfile().getTraderId());

        // Assert that new trader is subscribed on the all of mailing lists
        assertTrue(trader.getProfile().isCanReceiveGeneralMailing());
        assertTrue(trader.getProfile().isCanReceiveBugFixesMailing());
        assertTrue(trader.getProfile().isCanReceiveNewTasksMailing());
    }


    @Test
    public void testRegisterWithExistingEmail() {
        String email = UserTestHelper.generateUniqueEmail();

        boolean resultRegister = traderService.register(email);
        assertTrue(resultRegister);

        boolean resultSecondRegister = traderService.register(email);
        assertFalse(resultSecondRegister);
    }


    @Test
    public void testLogin() throws PlatformException {
        String uniqueEmail = UserTestHelper.generateUniqueEmail();

        boolean registerResult = traderService.register(uniqueEmail);
        assertTrue(registerResult);

        Trader user = traderService.login(uniqueEmail, generatedPassword);
        assertNotNull(user);
    }


    @Test
    public void testLoginWithIncorrectPassword() throws PlatformException {
        String uniqueEmail = UserTestHelper.generateUniqueEmail();
        String password = "incorrect-password";

        boolean registerResult = traderService.register(uniqueEmail);
        assertTrue(registerResult);

        Trader user = traderService.login(uniqueEmail, "%" + password);
        assertNull(user);
    }


    @Test
    public void testLoginWithIncorrectEmail() throws PlatformException {
        String uniqueEmail = UserTestHelper.generateUniqueEmail();

        boolean registerResult = traderService.register(uniqueEmail);
        assertTrue(registerResult);

        Trader user = traderService.login("%" + uniqueEmail, generatedPassword);
        assertNull(user);
    }


    @Test(expected = PlatformException.class)
    public void testLoginWithDisabledUser() throws PlatformException {
        String uniqueEmail = UserTestHelper.generateUniqueEmail();

        boolean registerResult = traderService.register(uniqueEmail);
        assertTrue(registerResult);

        Trader trader = traderRepository.findByEmail(uniqueEmail);
        trader.setEnabled(false);
        traderRepository.save(trader);

        traderService.login(uniqueEmail, generatedPassword);
    }


    @Test(expected = PlatformException.class)
    public void testLoginWithBlockedUser() throws PlatformException {
        String uniqueEmail = UserTestHelper.generateUniqueEmail();

        boolean registerResult = traderService.register(uniqueEmail);
        assertTrue(registerResult);

        Trader trader = traderRepository.findByEmail(uniqueEmail);
        trader.setLocked(true);
        traderRepository.save(trader);

        traderService.login(uniqueEmail, generatedPassword);
    }


    @Test
    public void testFindById() {
        Trader trader = new Trader(UserTestHelper.generateUniqueEmail(), generatedEncodedPassword, true,
                ZonedDateTime.now(), Role.ROLE_USER, false, ZonedDateTime.now(),
                ZonedDateTime.now());
        trader = traderRepository.save(trader);
        assertNotNull(trader);

        Trader foundEntity = traderService.findById(trader.getId());
        assertNotNull(foundEntity);
        assertEquals(trader.getId(), foundEntity.getId());
    }


    @Test
    public void testValidateAndUpdateUserExpiredTimeShouldValidIfRoleUserTokenIsNotExpired() throws PlatformException {
        String uniqueEmail = UserTestHelper.generateUniqueEmail();
        ZonedDateTime credentialsExpiredDate = ZonedDateTime.now().minus(3, ChronoUnit.HOURS);
        ZonedDateTime tokenExpiredDate = ZonedDateTime.now().plus(1, ChronoUnit.MINUTES);

        assertTrue(credentialsExpiredDate.isBefore(ZonedDateTime.now()));
        assertTrue(tokenExpiredDate.isAfter(ZonedDateTime.now()));

        Trader trader = new Trader(uniqueEmail, "aaa", true, ZonedDateTime.now(), Role.ROLE_USER, false,
                credentialsExpiredDate, tokenExpiredDate);
        trader = traderRepository.save(trader);

        traderService.validateAndUpdateUserExpiredTime(trader.getId());
    }


    @Test(expected = PlatformException.class)
    public void testValidateAndUpdateUserExpiredTimeShouldNotValidIfRoleStarterIsExpired() throws PlatformException {
        String uniqueEmail = UserTestHelper.generateUniqueEmail();
        ZonedDateTime credentialsExpiredDate = ZonedDateTime.now().minus(3, ChronoUnit.HOURS);
        ZonedDateTime tokenExpiredDate = ZonedDateTime.now().plus(1, ChronoUnit.MINUTES);

        assertTrue(credentialsExpiredDate.isBefore(ZonedDateTime.now()));
        assertTrue(tokenExpiredDate.isAfter(ZonedDateTime.now()));

        Trader trader = new Trader(uniqueEmail, "aaa", true, ZonedDateTime.now(), Role.ROLE_STARTER, false,
                credentialsExpiredDate, tokenExpiredDate);
        trader = traderRepository.save(trader);

        traderService.validateAndUpdateUserExpiredTime(trader.getId());
    }


    @Test
    public void testValidateAndUpdateUserExpiredTimeShouldChangeRoleToRoleUserIfRoleStarterIsExpired() {
        String uniqueEmail = UserTestHelper.generateUniqueEmail();
        ZonedDateTime credentialsExpiredDate = ZonedDateTime.now().minus(3, ChronoUnit.HOURS);
        ZonedDateTime tokenExpiredDate = ZonedDateTime.now().plus(1, ChronoUnit.MINUTES);

        assertTrue(credentialsExpiredDate.isBefore(ZonedDateTime.now()));
        assertTrue(tokenExpiredDate.isAfter(ZonedDateTime.now()));

        Trader trader = new Trader(uniqueEmail, "aaa", true, ZonedDateTime.now(), Role.ROLE_STARTER, false,
                credentialsExpiredDate, tokenExpiredDate);
        trader = traderRepository.save(trader);

        assertEquals(Role.ROLE_STARTER, trader.getRole());

        try {
            traderService.validateAndUpdateUserExpiredTime(trader.getId());
        } catch (PlatformException e) {
        }

        trader = traderRepository.findOne(trader.getId());
        assertEquals(Role.ROLE_USER, trader.getRole());
    }


    @Test(expected = PlatformException.class)
    public void testValidateAndUpdateUserExpiredTimeShouldNotValidIfTokenIsExpired() throws PlatformException {
        String uniqueEmail = UserTestHelper.generateUniqueEmail();

        ZonedDateTime credentialsExpiredDate = ZonedDateTime.now().plus(3, ChronoUnit.HOURS);
        ZonedDateTime tokenExpiredDate = ZonedDateTime.now().minus(1, ChronoUnit.MINUTES);

        assertTrue(credentialsExpiredDate.isAfter(ZonedDateTime.now()));
        assertTrue(tokenExpiredDate.isBefore(ZonedDateTime.now()));

        Trader trader = new Trader(uniqueEmail, "aaa", true, ZonedDateTime.now(), Role.ROLE_USER, false,
                credentialsExpiredDate, tokenExpiredDate);
        trader = traderRepository.save(trader);

        traderService.validateAndUpdateUserExpiredTime(trader.getId());
    }


    @Test
    @WithMockCustomUser
    public void testUpdateMailingSettings() {
        Trader trader = contextHolder.getCurrentTrader();

        ContextHolder contextHolderMock = (ContextHolder) ReflectionTestUtils.getField(traderService, "contextHolder");
        Mockito.when(contextHolderMock.getCurrentTrader()).thenReturn(trader);

        final boolean canReceiveGeneralMailing = trader.getProfile().isCanReceiveGeneralMailing();
        final boolean canReceiveBugFixesMailing = trader.getProfile().isCanReceiveBugFixesMailing();
        final boolean canReceiveNewTasksMailing = trader.getProfile().isCanReceiveNewTasksMailing();

        traderService.updateMailingSettings(!canReceiveGeneralMailing, !canReceiveBugFixesMailing, !canReceiveNewTasksMailing);
        trader = traderRepository.findByEmail(trader.getEmail());

        assertEquals(!canReceiveGeneralMailing, trader.getProfile().isCanReceiveGeneralMailing());
        assertEquals(!canReceiveBugFixesMailing, trader.getProfile().isCanReceiveBugFixesMailing());
        assertEquals(!canReceiveNewTasksMailing, trader.getProfile().isCanReceiveNewTasksMailing());
    }
}
