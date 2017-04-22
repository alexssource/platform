package com.platform.data.job.holder;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/29/17.
 */
public class MailingKeyHolder {
    /**
     * Может ли трейдер получать общую информацию, новости
     */
    private final boolean canReceiveGeneralMailing;

    /**
     * Может ли пользователь получать информацию об обновлениях задач в багтрекере
     */
    private final boolean canReceiveBugFixesMailing;

    /**
     * Может ли пользователь получать информацию о новых созданных задачах в багтрекере
     */
    private final boolean canReceiveNewTasksMailing;


    public MailingKeyHolder(boolean canReceiveGeneralMailing, boolean canReceiveBugFixesMailing,
            boolean canReceiveNewTasksMailing)
    {
        this.canReceiveGeneralMailing = canReceiveGeneralMailing;
        this.canReceiveBugFixesMailing = canReceiveBugFixesMailing;
        this.canReceiveNewTasksMailing = canReceiveNewTasksMailing;
    }


    public boolean isCanReceiveGeneralMailing() {
        return canReceiveGeneralMailing;
    }


    public boolean isCanReceiveBugFixesMailing() {
        return canReceiveBugFixesMailing;
    }


    public boolean isCanReceiveNewTasksMailing() {
        return canReceiveNewTasksMailing;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MailingKeyHolder that = (MailingKeyHolder) o;
        return canReceiveGeneralMailing == that.canReceiveGeneralMailing &&
                canReceiveBugFixesMailing == that.canReceiveBugFixesMailing &&
                canReceiveNewTasksMailing == that.canReceiveNewTasksMailing;
    }


    @Override
    public int hashCode() {
        return Objects.hash(canReceiveGeneralMailing, canReceiveBugFixesMailing, canReceiveNewTasksMailing);
    }
}
