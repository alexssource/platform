package com.platform.data.utils;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 22.12.16.
 */
public class ContextHolder {
    private final String symbolsSet = "QWERTYUIOPASDFGHJKLZXCVBNM<>@#1234567890qwertyuiopasdfghjklzxcvbnm";

    private int passwordLength;

    @Autowired
    private TraderService traderService;


    /**
     * Получает пользователя, ассоциированного с текущей сессией
     *
     * @return пользователь из Spring Security сессии
     */
    public static UserDto getCurrentUser()
    {
        return (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    /**
     * Получает локаль текущего пользователя
     *
     * @return локаль текущего пользователя
     */
    public static Locale getUserLocale() {
        return LocaleContextHolder.getLocale();
    }


    /**
     * Генерирует пароль passwordLength длины, состоящий из symbolsSet символов
     *
     * @return сгенерированный случайный пароль
     */
    public String generatePassword() {
        return RandomStringUtils.random(passwordLength, symbolsSet);
    }


    /**
     * Получает трейдера, т.е. пользователя ассоциированного с контекстом БД, Trader Entity
     *
     * @return attached Trader entity
     */
    public Trader getCurrentTrader()
    {
        UserDto user = getCurrentUser();
        return traderService.findById(user.getId());
    }


    public int getPasswordLength() {
        return passwordLength;
    }


    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
    }
}
