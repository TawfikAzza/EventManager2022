package gui.Model;

import be.Events;
import bll.EventManager;
import bll.exception.EventDAOException;

import java.util.List;

public class EventModel {

    private EventManager eventManager;

    public EventModel() throws Exception {
        this.eventManager = new EventManager();
    }

    public List<Events> getAllEvents() throws EventDAOException {
        return eventManager.getAllEvents();
    }
}
