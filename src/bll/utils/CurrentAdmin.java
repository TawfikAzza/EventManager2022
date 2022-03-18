package bll.utils;

import be.Admin;
import be.Coordinator;

public class CurrentAdmin {

    static private Admin instance;

    private CurrentAdmin()
    {
    }

    public static Admin getInstance()
    {
        return instance;
    }

    public static void setInstance(Admin admin)
    {
        instance = admin;
    }

}