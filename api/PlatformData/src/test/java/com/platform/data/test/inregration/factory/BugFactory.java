package com.platform.data.test.inregration.factory;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 3/26/17.
 */
public class BugFactory {

    public static Bug createBug(String name, BugType type, Trader trader) {
        return new Bug(name, "description", type, trader);
    }


    public static Bug createBug(String name, BugType type, BugStatus status, Trader trader) {
        Bug bug = BugFactory.createBug(name, type, trader);
        bug.setStatus(status);
        return bug;
    }


    public static Bug createResolvedBug(String name, BugStatus resolvedStatus, ZonedDateTime resolvedDate, Trader trader) {
        Bug bug = BugFactory.createBug(name, BugType.BUG, resolvedStatus, trader);
        bug.setResolvedDate(resolvedDate);
        return bug;
    }


    public static Bug createResolvedBug(String name, ZonedDateTime resolvedDate, Trader trader) {
        return BugFactory.createResolvedBug(name, BugStatus.FIXED, resolvedDate, trader);
    }

}
