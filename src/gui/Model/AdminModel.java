package gui.Model;

import be.Coordinator;
import be.Users;
import bll.AdminLogic;
import bll.exception.AdminLogicException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminModel {

    ObservableList<Coordinator> coordinatorObservableList;
    ObservableList<String> accountTypesObsList;
    private AdminLogic logic;

    public AdminModel() throws AdminLogicException {
        this.coordinatorObservableList = FXCollections.observableArrayList();
        this.accountTypesObsList = FXCollections.observableArrayList();
        this.logic = new AdminLogic();
        refresh();
    }

    public void addLoginUser(Users user)
    {
        logic.addLoginUser(user);
    }

    public void deleteEventCoordinator(Coordinator coordinator)
    {
        logic.deleteEventCoordinator(coordinator);
    }
    public void updateEventCoordinator(Coordinator coordinator)
    {
        logic.updateEventCoordinator(coordinator);
    }

    public ObservableList<Coordinator> getCoordinatorObservableList()
    {
        return coordinatorObservableList;
    }

    public ObservableList<String> getAccountTypes(){
        return accountTypesObsList;
    }

    public void deleteAll(){
        this.coordinatorObservableList.remove(0, this.coordinatorObservableList.size());
    }

    public void refresh() {
        this.deleteAll();
        this.coordinatorObservableList.addAll(logic.getAllCoordinators());
        this.accountTypesObsList.addAll(logic.getAccountTypes());
    }

        /*public ArrayList<Coordinator> getAllCoordinators()
    {
        return logic.getAllCoordinators();
    }

     */

}
