package com.platform.data.test.inregration.factory;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 25.03.17.
 */
public class ProfileFactory {

    public static Profile createProfile(Trader trader) {
        return new Profile(trader, true, true, true);
    }


    public static Profile createProfile(Trader trader, boolean canReceiveGeneralMailing, boolean canReceiveBugFixesMailing,
            boolean canReceiveNewTasksMailing)
    {
        return new Profile(trader, canReceiveGeneralMailing, canReceiveBugFixesMailing, canReceiveNewTasksMailing);
    }

}
