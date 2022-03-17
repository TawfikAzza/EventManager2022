package gui.Model;

import be.Coordinator;
import bll.AdminLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public class AdminModel {

    ObservableList<Coordinator> coordinatorObservableList;
    private AdminLogic logic;

    public AdminModel() throws IOException {
        this.coordinatorObservableList = FXCollections.observableArrayList();
        this.logic = new AdminLogic();
        refresh();
    }

    public void addEventCoordinator(Coordinator coordinator)
    {
        logic.addEventCoordinator(coordinator);
    }

    public void deleteEventCoordinator(Coordinator coordinator)
    {
        logic.deleteEventCoordinator(coordinator);
    }
    public void updateEventCoordinator(Coordinator coordinator)
    {
        logic.updateEventCoordinator(coordinator);
    }

    public ArrayList<Coordinator> getAllCoordinators()
    {
        return logic.getAllCoordinators();
    }

    public ObservableList<Coordinator> getCoordinatorObservableList()
    {
        return coordinatorObservableList;
    }

    public void deleteAll(){
        this.coordinatorObservableList.remove(0, this.coordinatorObservableList.size());
    }

    public void refresh() {
        this.deleteAll();
        this.coordinatorObservableList.addAll(getAllCoordinators());
    }

}
