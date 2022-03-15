package bll;

import be.Events;
import dal.DALController;

import java.util.List;

public class EventManager {

    private DALController dalController;
    public EventManager() throws Exception {
        dalController = new DALController();
    }

    public List<Events> getAllEvents() throws Exception {
        return dalController.getAllEvents();
    }
}
