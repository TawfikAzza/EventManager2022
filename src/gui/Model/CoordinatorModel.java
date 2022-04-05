package gui.Model;

import be.Coordinator;
import be.Events;
import be.Participant;
import be.Ticket;
import bll.AdminLogic;
import bll.EventManager;
import bll.ParticipantManager;
import bll.exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CoordinatorModel {

    private ObservableList<Events> allEvents = FXCollections.observableArrayList();
    private ObservableList<Coordinator> allCoordinator = FXCollections.observableArrayList();
    private EventManager eventManager;
    private AdminLogic adminManager;
    private ParticipantManager participantManager;
    public CoordinatorModel() throws EventManagerException, AdminDAOException, ParticipantManagerException {
        eventManager = new EventManager();
        adminManager = new AdminLogic();
        participantManager = new ParticipantManager();
    }
    public ObservableList<Events> getAllEvents() throws EventManagerException {
        allEvents.clear();
        allEvents.addAll(eventManager.getAllEvents());
        return allEvents;
    }
    public ObservableList<Coordinator> getAllCollaborators() throws AdminDAOException {
        allCoordinator.clear();
        allCoordinator.addAll(adminManager.getAllCoordinators());
        return allCoordinator;
    }


    public Events createEvent(Events event) throws EventManagerException {
        return eventManager.createEvent(event);
    }

    public void updateEvent(Events event) throws EventManagerException {
        eventManager.updateEvent(event);
    }

    public List<Events> getParticipantEvent(Participant participant) throws  EventManagerException {
        return eventManager.getParticipantEvent(participant);
    }

    public List<Events> getAllEventsWithTicketType() throws EventDAOException, EventManagerException {
        return eventManager.getAllEventsWithTicketType();
    }

    public Ticket sellTicket(Ticket ticketSold,Events eventChosen, Participant participant) throws EventManagerException {
        return eventManager.sellTicket(ticketSold,eventChosen,participant);
    }

    public void deleteParticipant(Participant selectedItem) throws ParticipantManagerException {
        participantManager.deleteParticipant(selectedItem);
    }
}
