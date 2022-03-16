package bll.utils;

import be.Coordinator;

public class CurrentEventCoordinator {

    static private Coordinator instance;

    private CurrentEventCoordinator()
    {
    }

    public static Coordinator getInstance()
    {
        return instance;
    }

    public static void setInstance(Coordinator coordinator)
    {
        instance = coordinator;
    }

}
