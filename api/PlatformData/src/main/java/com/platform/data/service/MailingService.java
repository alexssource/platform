package com.platform.data.service;

/**
 * Сервис рассылки электронной почты подписчикам через mail service
 *
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 26.03.17.
 */
public interface MailingService {
    EmailTemplate getEmailTemplate(EmailTemplateType type);
    List<EmailTemplate> getEmailTemplates();
    EmailTemplate update(EmailTemplateType type, String template);
}
