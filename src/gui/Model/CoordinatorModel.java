package gui.Model;

import be.Coordinator;
import be.Events;
import be.Users;
import bll.AdminLogic;
import bll.EventManager;
import bll.exception.EventDAOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CoordinatorModel {

    private ObservableList<Events> allEvents = FXCollections.observableArrayList();
    private ObservableList<Coordinator> allCoordinator = FXCollections.observableArrayList();
    private EventManager eventManager;
    private AdminLogic adminManager;
    public CoordinatorModel() throws Exception {
        eventManager = new EventManager();
        adminManager = new AdminLogic();
    }
    public ObservableList<Events> getAllEvents() throws EventDAOException {
        allEvents.clear();
        allEvents.addAll(eventManager.getAllEvents());
        return allEvents;
    }
    public ObservableList<Coordinator> getAllCollaborators() {
        allCoordinator.clear();
        allCoordinator.addAll(adminManager.getAllCoordinators());
        return allCoordinator;
    }
}
