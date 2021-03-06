package gui.Model;

import be.Events;
import be.Ticket;
import be.TicketType;
import bll.EventManager;
import bll.FileManager;
import bll.exception.EventDAOException;
import bll.exception.EventManagerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.List;

public class EventModel {

    ObservableList<Events> eventsObservableList;
    private EventManager eventManager;
    private FileManager fileManager;

    public EventModel() throws Exception, EventDAOException, EventManagerException {
        this.eventManager = new EventManager();
        this.eventsObservableList = FXCollections.observableArrayList();
        refresh();
        fileManager = new FileManager();
    }

    public List<Events> getAllEvents() throws EventManagerException {
        return eventManager.getAllEvents();
    }

    public ObservableList<Events> getEventsObservableList()
    {
        return eventsObservableList;
    }

    public void deleteAll(){
        this.eventsObservableList.remove(0, this.eventsObservableList.size());
    }

    public void refresh() throws EventManagerException {
        this.deleteAll();
        this.eventsObservableList.addAll(getAllEvents());
    }

    public Workbook exportExcelFile (int idOfEvent) throws IOException {
        return fileManager.exportExcelFile(idOfEvent);
    }

    public Ticket getTicket(int ticketID) throws EventManagerException {
        return eventManager.getTicket(ticketID);
    }

    public TicketType getTicketType(int ticketTypeID) throws EventManagerException {
        return eventManager.getTicketType(ticketTypeID);
    }

    public boolean validTicketScan(String text) throws EventManagerException {
        return eventManager.validTicketScan(text);
    }

    public TicketType getTicketTypeFromTicket(String text) throws EventManagerException {
        return eventManager.getTicketTypeFromTicket(text);
    }
}
