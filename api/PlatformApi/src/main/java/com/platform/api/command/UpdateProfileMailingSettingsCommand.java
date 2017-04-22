package com.platform.api.command;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 25.03.17.
 */
public class UpdateProfileMailingSettingsCommand {
    /**
     * Может ли продавец получать общую информацию, новости
     */
    @NotNull
    private boolean canReceiveGeneralMailing;

    /**
     * Может ли пользователь получать информацию об обновлениях задач в багтрекере
     */
    @NotNull
    private boolean canReceiveBugFixesMailing;

    /**
     * Может ли пользователь получать информацию о новых созданных задачах в багтрекере
     */
    @NotNull
    private boolean canReceiveNewTasksMailing;


    public UpdateProfileMailingSettingsCommand() {
    }


    public UpdateProfileMailingSettingsCommand(boolean canReceiveGeneralMailing, boolean canReceiveBugFixesMailing,
            boolean canReceiveNewTasksMailing)
    {
        this.canReceiveGeneralMailing = canReceiveGeneralMailing;
        this.canReceiveBugFixesMailing = canReceiveBugFixesMailing;
        this.canReceiveNewTasksMailing = canReceiveNewTasksMailing;
    }


    public boolean isCanReceiveGeneralMailing() {
        return canReceiveGeneralMailing;
    }


    public void setCanReceiveGeneralMailing(boolean canReceiveGeneralMailing) {
        this.canReceiveGeneralMailing = canReceiveGeneralMailing;
    }


    public boolean isCanReceiveBugFixesMailing() {
        return canReceiveBugFixesMailing;
    }


    public void setCanReceiveBugFixesMailing(boolean canReceiveBugFixesMailing) {
        this.canReceiveBugFixesMailing = canReceiveBugFixesMailing;
    }


    public boolean isCanReceiveNewTasksMailing() {
        return canReceiveNewTasksMailing;
    }


    public void setCanReceiveNewTasksMailing(boolean canReceiveNewTasksMailing) {
        this.canReceiveNewTasksMailing = canReceiveNewTasksMailing;
    }
}
