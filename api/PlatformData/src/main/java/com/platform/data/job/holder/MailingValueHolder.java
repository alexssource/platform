package com.platform.data.job.holder;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/29/17.
 */
public class MailingValueHolder {
    /**
     * Сформированное и обработанное препроцессором сообщение рассылки
     */
    private final String message;

    /**
     * Список получателей данного письма
     */
    private List<String> subscribers;


    public MailingValueHolder(String message) {
        this.message = message;
        this.clean();
    }


    /**
     * Очищает список получателей
     * Необходимо для перехода на следующий шаг обработки
     */
    public void clean() {
        this.subscribers = new ArrayList<>();
    }


    /**
     * Добавляет подписчика на очередь обработки
     * @param subscriber email подписчика
     */
    public void addSubscriber(String subscriber) {
        this.subscribers.add(subscriber);
    }


    public List<String> getSubscribers() {
        return subscribers;
    }


    public String getMessage() {
        return message;
    }
}
