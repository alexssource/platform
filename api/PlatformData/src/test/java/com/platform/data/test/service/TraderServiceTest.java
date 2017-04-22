package com.platform.data.test.service;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 23.12.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TraderServiceTest {

    private final String password = "generated-password";

    private final String encodedPassword = "dedhe83eh3";

    @Mock
    private ContextHolder contextHolder;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private TraderServiceImpl userService = new TraderServiceImpl("support@emailru");

    @Mock
    private TraderRepository traderRepository;

    @Mock
    private MailService mailService;

    @Mock
    private MailingService mailingService;


    @Before
    public void init() {
        Mockito.when(contextHolder.generatePassword()).thenReturn(password);
        Mockito.when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        Mockito.when(mailingService.getEmailTemplate(EmailTemplateType.REGISTRATION))
                .thenReturn(new EmailTemplate(EmailTemplateType.REGISTRATION, "%s%s"));
    }


    @Test
    public void testRegisterShouldInvokePasswordEncoder() {
        String email = UserTestHelper.generateUniqueEmail();
        Trader trader = Mockito.mock(Trader.class);

        Mockito.when(traderRepository.save(Mockito.any(Trader.class))).thenReturn(trader);
        userService.register(email);

        Mockito.verify(contextHolder).generatePassword();
        Mockito.verify(passwordEncoder).encode(password);
    }


    @Test
    public void testRegisterShouldInvokeSendEmail() {
        String email = UserTestHelper.generateUniqueEmail();
        Trader trader = new Trader();
        trader.setId(1L);
        Mockito.when(traderRepository.save(Mockito.any(Trader.class))).thenReturn(trader);
        userService.register(email);

        Mockito.verify(mailService).sendEmail(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
                Mockito.anyString(), Mockito.any());
    }


    @Test
    public void testLoginShouldInvokePasswordEncoder() throws PlatformException {
        String email = UserTestHelper.generateUniqueEmail();
        Mockito.when(traderRepository.findByEmail(email))
                .thenReturn(new Trader("", "", false, null, null, false, null, null));

        userService.login(email, password);

        Mockito.verify(passwordEncoder).matches(Mockito.anyString(), Mockito.anyString());
    }

}
