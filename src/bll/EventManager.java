package bll;

import be.Events;
import bll.exception.EventDAOException;
import dal.db.EventDAO;
import dal.interfaces.IEventDAO;

import java.util.List;

public class EventManager {

    private IEventDAO eventDAO;
    public EventManager() throws Exception {
        eventDAO = new EventDAO();
    }

    public List<Events> getAllEvents() throws EventDAOException {
        try {
            return eventDAO.getAllEvents();
        } catch (Exception e) {
            throw  new EventDAOException("Error while getting the events form the database!",e);
        }
    }
}
