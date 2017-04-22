package com.platform.data.domain;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 25.03.17.
 */
@Entity
@Table(name = "PROFILE")
public class Profile {

    /**
     * Ид пользователя, первичный ключ
     * Ид пользователя и Ид профиля совпадают
     */
    @Id
    @Column(name = "TRADER_ID")
    private Long traderId;

    /**
     * Может ли продавец получать общую информацию, новости
     */
    @Column(name = "RECEIVE_GENERAL_MAILING", nullable = false)
    private boolean canReceiveGeneralMailing;

    /**
     * Может ли пользователь получать информацию об обновлениях задач в багтрекере
     */
    @Column(name = "RECEIVE_BUG_FIXES_MAILING", nullable = false)
    private boolean canReceiveBugFixesMailing;

    /**
     * Может ли пользователь получать информацию о новых созданных задачах в багтрекере
     */
    @Column(name = "RECEIVE_NEW_BUG_TASKS_MAILING", nullable = false)
    private boolean canReceiveNewTasksMailing;

    /**
     * Трейдер
     */
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "TRADER_ID", unique = true, nullable = false)
    private Trader trader;


    public Profile() {
    }


    public Profile(Trader trader, boolean canReceiveGeneralMailing, boolean canReceiveBugFixesMailing,
            boolean canReceiveNewTasksMailing)
    {
        this.traderId = trader.getId();
        this.trader = trader;
        this.canReceiveGeneralMailing = canReceiveGeneralMailing;
        this.canReceiveBugFixesMailing = canReceiveBugFixesMailing;
        this.canReceiveNewTasksMailing = canReceiveNewTasksMailing;
    }


    public Long getTraderId() {
        return traderId;
    }


    public void setTraderId(Long traderId) {
        this.traderId = traderId;
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


    public Trader getTrader() {
        return trader;
    }


    public void setTrader(Trader trader) {
        this.trader = trader;
    }
}
