package com.platform.data.test.inregration.factory;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 25.03.17.
 */
public class TraderFactory {

    public static Trader createTrader(String email, String password, boolean enabled, ZonedDateTime createdAt,
            Role role, boolean locked, ZonedDateTime credentialsExpirationDate, ZonedDateTime tokenExpirationDate)
    {
        return new Trader(email, password, enabled, createdAt, role, locked, credentialsExpirationDate,
                tokenExpirationDate);
    }


    public static Trader createTrader(String email, String password, Role role) {
        return TraderFactory.createTrader(email, password, true, ZonedDateTime.now(), role,
                false, ZonedDateTime.now(), ZonedDateTime.now());
    }


    public static Trader createTrader(String email, String password) {
        return TraderFactory.createTrader(email, password, Role.ROLE_USER);
    }


    public static Trader createTrader(String email) {
        return TraderFactory.createTrader(email, "generated-password");
    }
}
