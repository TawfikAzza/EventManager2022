package bll;

import be.Events;
import dal.DALController;

import java.util.List;

public class EventManager implements EventManagerFacade{

    private DALController dalController;
    public EventManager() throws Exception {
        dalController = new DALController();
    }
    @Override
    public List<Events> getAllEvents() throws Exception {
        return dalController.getAllEvents();
    }
}
