package com.platform.data.enums;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 26.03.17.
 */
public enum EmailTemplateType {
    /**
     * Шаблон письма о успешной регистрации
     */
    REGISTRATION,

    /**
     * Шаблон письма о смене пароля
     */
    CHANGED_PASSWORD,

    /**
     * Шаблон письма общей рассылки
     */
    MAILING_LAYOUT,

    /**
     * Шаблон письма о багфиксах
     */
    BUGTRACKER_BUG_FIXES,

    /**
     * Шаблон письма о новых задачах
     */
    BUGTRACKER_NEW_TASKS
}
