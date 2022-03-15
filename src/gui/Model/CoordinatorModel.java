package gui.Model;

import be.Events;
import bll.EventManager;
import bll.EventManagerFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CoordinatorModel {

    private ObservableList<Events> allEvents = FXCollections.observableArrayList();
    private EventManagerFacade eventManagerFacade;
    public CoordinatorModel() {
        eventManagerFacade = new EventManager();
    }
    public ObservableList<Events> getAllEvents() throws Exception {
        allEvents.clear();
        allEvents.addAll(eventManagerFacade.getAllEvents());
        return allEvents;
    }
}
