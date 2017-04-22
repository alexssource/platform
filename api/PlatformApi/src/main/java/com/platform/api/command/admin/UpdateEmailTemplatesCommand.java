package com.platform.api.command.admin;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 26.03.17.
 */
public class UpdateEmailTemplatesCommand {

    @NotNull
    private List<EmailTemplateCommandField> emailTemplates;


    public UpdateEmailTemplatesCommand() {
    }


    public UpdateEmailTemplatesCommand(List<EmailTemplateCommandField> emailTemplates)
    {
        this.emailTemplates = emailTemplates;
    }


    public static class EmailTemplateCommandField {

        @NotNull
        private EmailTemplateType type;

        @NotNull
        @Size(min = 3)
        private String template;


        public EmailTemplateCommandField() {
        }


        public EmailTemplateType getType() {
            return type;
        }


        public void setType(EmailTemplateType type) {
            this.type = type;
        }


        public String getTemplate() {
            return template;
        }


        public void setTemplate(String template) {
            this.template = template;
        }
    }


    public List<EmailTemplateCommandField> getEmailTemplates() {
        return emailTemplates;
    }


    public void setEmailTemplates(
            List<EmailTemplateCommandField> emailTemplates)
    {
        this.emailTemplates = emailTemplates;
    }
}
