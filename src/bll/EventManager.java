package bll;

import be.Events;
import bll.exception.EventDAOException;
import bll.exception.EventManagerException;
import dal.DALController;
import dal.db.EventDAO;

import java.util.List;

public class EventManager {

    private EventDAO eventDAO;
    public EventManager() throws EventManagerException {

        try {
            eventDAO = new EventDAO();
        } catch (Exception e) {
            throw new EventManagerException("Failed to initialize Event Manager class!",e);
        }
    }

    public List<Events> getAllEvents() throws EventManagerException {
        try {
            return eventDAO.getAllEvents();
        } catch (Exception e) {
            throw  new EventManagerException("Error while getting the events form the database!",e);
        }
    }

    public Events createEvent(Events event) throws EventManagerException{
        try {
            return eventDAO.addEvent(event);
        } catch (Exception e) {
            throw new EventManagerException("Error while creating the Event in database",e);
        }
    }
}
