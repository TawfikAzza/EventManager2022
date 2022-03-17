package gui.Model;

import be.Events;
import bll.EventManager;
import bll.exception.EventDAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class EventModel {

    ObservableList<Events> eventsObservableList;
    private EventManager eventManager;

    public EventModel() throws Exception, EventDAOException {
        this.eventManager = new EventManager();
        this.eventsObservableList = FXCollections.observableArrayList();
        refresh();
    }

    public List<Events> getAllEvents() throws EventDAOException {
        return eventManager.getAllEvents();
    }

    public ObservableList<Events> getEventsObservableList()
    {
        return eventsObservableList;
    }

    public void deleteAll(){
        this.eventsObservableList.remove(0, this.eventsObservableList.size());
    }

    public void refresh() throws EventDAOException {
        this.deleteAll();
        this.eventsObservableList.addAll(getAllEvents());
    }
}
