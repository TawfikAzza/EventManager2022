package bll;

import be.Events;
import bll.exception.EventManagerException;
import dal.db.EventDAO;
import dal.db.TicketDAO;

import java.util.List;

public class EventManager {
    /**
     * Warning: this class turn out to be clase to a facade for the procedure to create events involve the ccreation of multiple
     * values in the database which need to be executed in a certain way.
     * Do think twice before changing any of the methods in this class.
     *
     * */
    private EventDAO eventDAO;
    private TicketDAO ticketDAO;
    public EventManager() throws EventManagerException {

        try {
            eventDAO = new EventDAO();
            ticketDAO = new TicketDAO();
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
            Events eventCreated = eventDAO.addEvent(event);
            event.setId(eventCreated.getId());
            ticketDAO.addEventTicket(event);
            return event;
        } catch (Exception e) {
            throw new EventManagerException("Error while creating the Event in database",e);
        }
    }

    public void updateEvent(Events event) throws EventManagerException {
        try {
            eventDAO.updateEvent(event);
            ticketDAO.updateEventTicket(event);
        } catch (Exception e) {
            throw new EventManagerException("Error while creating the Event in database",e);
        }
    }
}
