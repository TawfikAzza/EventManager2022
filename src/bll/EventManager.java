package bll;

import be.Events;
import be.Participant;
import be.Ticket;
import bll.exception.EventDAOException;
import bll.exception.EventManagerException;
import dal.db.EventDAO;
import dal.db.TicketDAO;
import dal.interfaces.IEventDAO;

import java.sql.SQLException;
import java.util.List;

public class EventManager {
    /**
     * Warning: this class turn out to be clase to a facade for the procedure to create events involve the ccreation of multiple
     * values in the database which need to be executed in a certain way.
     * Do think twice before changing any of the methods in this class.
     *
     * */
    private IEventDAO eventDAO;
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
            throw new EventManagerException("Error while updating the Event in database",e);
        }
    }

    public void deleteEvent(Events event) throws Exception {
     //   eventDAO.removeEvent(event);
    }

    public List<Events> getParticipantEvent(Participant participant) throws EventManagerException {
        try {
            return eventDAO.getParticipantEvent(participant);
        } catch (Exception e) {
            throw new EventManagerException("Error while retrieving the participants list in database",e);
        }
    }

    public List<Events> getAllEventsWithTicketType() throws EventManagerException {
        try {
            return eventDAO.getAllEventsWithTicketType();
        } catch (Exception e) {
            throw new EventManagerException("Error while retrieving the events from the database",e);
        }
    }

    public Ticket sellTicket(Ticket ticketSold, Events eventChosen, Participant participant) throws EventManagerException {
        try {
            ticketSold = ticketDAO.addTicketSold(ticketSold);
            eventDAO.addParticipantToEvent(eventChosen,participant,ticketSold);
            return ticketSold;

        } catch (SQLException e) {
            throw new EventManagerException("Error while creating the ticket in the database! ",e);
        }
    }
}
