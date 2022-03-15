package gui.Model;

import be.Events;
import bll.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CoordinatorModel {

    private ObservableList<Events> allEvents = FXCollections.observableArrayList();
    private EventManager eventManager;
    public CoordinatorModel() throws Exception {
        eventManager = new EventManager();
    }
    public ObservableList<Events> getAllEvents() throws Exception {
        allEvents.clear();
        allEvents.addAll(eventManager.getAllEvents());
        return allEvents;
    }
}
