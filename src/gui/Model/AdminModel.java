package gui.Model;

import be.Admin;
import be.Coordinator;
import be.Users;
import bll.AdminLogic;
import bll.exception.AdminLogicException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class AdminModel {

    ObservableList<Coordinator> coordinatorObservableList;
    ObservableList<Admin> adminObservableList;
    ObservableList<String> accountTypesObsList;
    private AdminLogic logic;

    public AdminModel() throws AdminLogicException {
        this.coordinatorObservableList = FXCollections.observableArrayList();
        this.accountTypesObsList = FXCollections.observableArrayList();
        this.adminObservableList = FXCollections.observableArrayList();
        this.logic = new AdminLogic();
        refresh();
    }

    public void addLoginUser(Users user)
    {
        logic.addLoginUser(user);
    }

    public void deleteUser(Users user)
    {
        logic.deleteUser(user);
    }
    public void editUser(Users user)
    {
        logic.editUser(user);
    }

    public ObservableList<Coordinator> getCoordinatorObservableList()
    {
        return coordinatorObservableList;
    }

    public ObservableList<String> getAccountTypes(){
        return accountTypesObsList;
    }

    public ObservableList<Admin> getAdminObservableList() {
        return adminObservableList;
    }

    public Users getUser(String username, String password)
    {
        try {
            return logic.getUser(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAll(){
        this.coordinatorObservableList.remove(0, this.coordinatorObservableList.size());
    }

    public void refresh() {
        this.deleteAll();
        this.adminObservableList.addAll(logic.getAllAdmins());
        this.coordinatorObservableList.addAll(logic.getAllCoordinators());
        this.accountTypesObsList.addAll(logic.getAccountTypes());
    }

        /*public ArrayList<Coordinator> getAllCoordinators()
    {
        return logic.getAllCoordinators();
    }

     */

}
