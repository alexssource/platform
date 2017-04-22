package com.platform.data.domain;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 26.03.17.
 */
@Entity
@Table(name = "EMAIL_TEMPLATE")
public class EmailTemplate {

    @Id
    @Column(name = "EMAIL_TYPE")
    @Enumerated(value = EnumType.STRING)
    private EmailTemplateType type;

    @Column(name = "TEMPLATE", nullable = false)
    private String template;


    public EmailTemplate() {
    }


    public EmailTemplate(EmailTemplateType type, String template) {
        this.type = type;
        this.template = template;
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
