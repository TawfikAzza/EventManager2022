package bll;

import be.Events;
import bll.exception.EventDAOException;
import bll.exception.EventManagerException;
import dal.DALController;

import java.util.List;

public class EventManager {

    private DALController dalController;
    public EventManager() throws EventManagerException {

        try {
            dalController = new DALController();
        } catch (Exception e) {
            throw new EventManagerException("Failed to initialize Event Manager class!",e);
        }
    }

    public List<Events> getAllEvents() throws EventManagerException {
        try {
            return dalController.getAllEvents();
        } catch (Exception e) {
            throw  new EventManagerException("Error while getting the events form the database!",e);
        }
    }
}
