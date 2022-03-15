package bll;

import be.Events;
import bll.exception.EventDAOException;
import dal.DALController;

import java.util.List;

public class EventManager {

    private DALController dalController;
    public EventManager() throws Exception {
        dalController = new DALController();
    }

    public List<Events> getAllEvents() throws EventDAOException {
        try {
            return dalController.getAllEvents();
        } catch (Exception e) {
            throw  new EventDAOException("Error while getting the events form the database!",e);
        }
    }
}
