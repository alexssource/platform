package com.platform.data.service.impl;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 26.03.17.
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MailingServiceImpl implements MailingService {

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;


    @Override
    public EmailTemplate getEmailTemplate(EmailTemplateType type) {
        return emailTemplateRepository.findOne(type);
    }


    @Override
    public List<EmailTemplate> getEmailTemplates() {
        return emailTemplateRepository.findAll();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EmailTemplate update(EmailTemplateType type, String template) {
        EmailTemplate emailTemplate = getEmailTemplate(type);
        emailTemplate.setTemplate(template);
        return emailTemplate;
    }
}
