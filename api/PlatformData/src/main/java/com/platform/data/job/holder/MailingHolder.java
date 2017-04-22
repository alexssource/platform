package com.platform.data.job.holder;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/29/17.
 */
public class MailingHolder {
    private Map<MailingKeyHolder, MailingValueHolder> mailingContext;

    private final MailingPreprocessor preprocessor;


    public MailingHolder(MailingPreprocessor preprocessor) {
        this.mailingContext = new HashMap<>();
        this.preprocessor = preprocessor;
    }


    /**
     * Очищащет все списки подписчиков для нового шага обработки
     */
    public void clean() {
        mailingContext.values().forEach(MailingValueHolder::clean);
    }


    public void add(MailingKeyHolder key, String subscriber) {
        MailingValueHolder valueHolder = this.mailingContext.computeIfAbsent(key,
                k -> new MailingValueHolder(preprocessor.createMessage(key)));

        valueHolder.addSubscriber(subscriber);
    }


    public Map<MailingKeyHolder, MailingValueHolder> getMailingContext() {
        return mailingContext;
    }
}
