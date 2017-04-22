package com.platform.api.controller.admin;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 26.03.17.
 */
@Scope("prototype")
@RestController
@RequestMapping("/api/admin/mailing")
@Secured(Role.Get.ROLE_ADMIN)
public class MailingController {

    @Autowired
    private MailingService mailingService;

    @Autowired
    @Qualifier("mailingBatchJob")
    private BatchJob mailingBatchJob;


    @RequestMapping(value = "/templates")
    public List<EmailTemplateDto> getEmailTemplates() {
        return mailingService.getEmailTemplates().stream()
                .map(EntityDtoMapper::mapEmailTemplate)
                .collect(Collectors.toList());
    }


    @RequestMapping(value = "/templates/update", method = RequestMethod.POST)
    public void updateEmailTemplates(@RequestBody @Valid UpdateEmailTemplatesCommand command, BindingResult bindingResult)
            throws PlatformFieldValidationException
    {
        if (bindingResult.hasErrors()) {
            throw new PlatformFieldValidationException(ValidationErrorHelper.createPlatformFieldErrors(bindingResult));
        }

        command.getEmailTemplates().forEach(template -> mailingService.update(template.getType(), template.getTemplate()));
    }


    @RequestMapping(value = "/templates/{type}")
    public EmailTemplateDto getEmailTemplateByType(@PathVariable("type") EmailTemplateType type) {
        return EntityDtoMapper.mapEmailTemplate(mailingService.getEmailTemplate(type));
    }


    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void mailing(@RequestBody @Valid MailingCommand command, BindingResult bindingResult)
            throws PlatformException, PlatformFieldValidationException
    {
        if (bindingResult.hasErrors()) {
            throw new PlatformFieldValidationException(ValidationErrorHelper.createPlatformFieldErrors(bindingResult));
        }

        new BatchExecutor().execute(mailingBatchJob, command.getMailingTemplate());
    }
}
