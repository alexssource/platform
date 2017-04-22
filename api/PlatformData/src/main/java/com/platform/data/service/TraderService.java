package com.platform.data.service;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
public interface TraderService {
    /**
     * Поиск трейдера по ид
     * @param id ид трейдера
     * @return найденный трейдер или null, если трейдер не найден
     */
    Trader findById(Long id);

    /**
     * Регистрация нового трейдера
     *
     * @param email Email-адрес трейдера
     * @return результат регистрации
     */
    boolean register(String email);

    /**
     * Логин пользователя
     *
     * @param email Email-адрес трейдера
     * @param password пароль трейдера
     * @return объект трейдера
     * @throws PlatformException в случае если пользователь не найден, выключен (disabled), заблокирован,
     * либо неверный пароль
     */
    Trader login(String email, String password) throws PlatformException;

    /**
     * Обновление роли пользователя
     *
     * @param trader трейдер
     * @param role новая роль
     * @return трейдер
     */
    Trader updateRole(Trader trader, Role role);

    /**
     * Проверяет, истекло ли время роли пользователя и время токена сессии пользователя.
     * Если время не истекло, обновляет время токена сессии
     *
     * @param id ид трейдера
     * @throws PlatformException если трейдер с переданным ид не найден, роль истекла либо время токена истекло
     */
    void validateAndUpdateUserExpiredTime(Long id) throws PlatformException;

    /**
     * Генерирует новый пароль для пользователя с указанным Email, и высылает его ему на почту
     *
     * @param email Email-трейдера
     * @return результат смены
     */
    boolean changePassword(String email);

    /**
     * Обновляет настройки подписки для текущего пользователя
     * @param canReceiveGeneralMailing может ли пользователь получать общую информацию
     * @param canReceiveBugFixesMailing может ли пользователь получать информацио об обновлениях задач в багтрекере
     * @param canReceiveNewTasksMailing может ли пользователь получать информацию о новых задачах в багтрекере
     */
    void updateMailingSettings(boolean canReceiveGeneralMailing, boolean canReceiveBugFixesMailing, boolean canReceiveNewTasksMailing);

    /**
     * Получает профиль трейдера по Id
     *
     * @return профиль трейдера
     */
    Profile getProfile(Long traderId);

    /**
     * Получает список профилей трейдеров для рассылки.
     * Если у трейдера все 3 опции рассылки выключены, такой трейдер в результат не попадает
     *
     * @param pageable требуемая страница списка
     * @return страница списка трейдеров, желающих получать рассылку
     */
    Slice<Profile> findTraderProfilesForMailing(Pageable pageable);
}
