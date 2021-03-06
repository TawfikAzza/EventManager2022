package gui.Controller.AdminControllers;

import be.Admin;
import be.Coordinator;
import be.Events;
import bll.exception.AdminDAOException;
import bll.exception.EventDAOException;
import bll.exception.EventManagerException;
import bll.utils.DisplayError;
import bll.utils.SceneSetter;
import gui.Model.AdminModel;
import gui.Model.EventModel;
import gui.util.BundleHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {
    @FXML
    private TableView<Admin> adminTableView;
    @FXML
    private TableColumn<Admin, String> tableColumnFirstNameAdmin;
    @FXML
    private TableColumn<Admin, String> tableColumnLastNameAdmin;
    @FXML
    private TableColumn<Events, LocalDateTime> tableColumnEventDate;
    @FXML
    private TableColumn<Coordinator, String> tableColumnFirstName;
    @FXML
    private TableColumn<Coordinator, String> tableColumnLastName;
    @FXML
    private TableColumn<Events, String> tableColumnEventName;
    @FXML
    private TableView<Events> eventTableView;
    @FXML
    private TableView<Coordinator> coordinatorTableView;

    private AdminModel adminModel;
    private EventModel eventmodel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.adminModel = new AdminModel();
            this.eventmodel = new EventModel();
        this.coordinatorTableView.setItems(adminModel.getCoordinatorObservableList());
        this.eventTableView.setItems(eventmodel.getEventsObservableList());
        this.adminTableView.setItems(adminModel.getAdminObservableList());
        this.initTables();
        } catch (Exception | EventDAOException | EventManagerException | AdminDAOException e) {
            DisplayError.displayError(e);
        }
    }

    private void initTables() {
        this.tableColumnFirstNameAdmin.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.tableColumnLastNameAdmin.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        this.tableColumnEventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.tableColumnEventDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        this.tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    }

    public void handleNewClick(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AddEventCoordinatorView.fxml"));
        if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
        else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
        SceneSetter.setScene(adminTableView, loader);
    }

    public void handleCoordinatorClick(MouseEvent mouseEvent) {
        Coordinator coordinator = coordinatorTableView.getSelectionModel().getSelectedItem();
        if(coordinator!=null)
        {
            if(mouseEvent.getClickCount()==2) {
                AdminEventCoordinatorViewController controller = new AdminEventCoordinatorViewController(coordinator);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminEventCoordinatorView.fxml"));
                if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
                else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
                loader.setController(controller);
                SceneSetter.setScene(adminTableView, loader);
            }
        }
    }

    public void handleAdminClick(MouseEvent mouseEvent) {
        Admin admin = adminTableView.getSelectionModel().getSelectedItem();
        if(admin!=null)
        {
            if(mouseEvent.getClickCount()==2) {
                AdminEditDeleteViewController controller = new AdminEditDeleteViewController(admin);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminEditDeleteView.fxml"));
                if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
                else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
                loader.setController(controller);
                SceneSetter.setScene(adminTableView, loader);
            }
        }
    }

    /*public void handleDeleteEvent(ActionEvent actionEvent) {
        Events event = eventTableView.getSelectionModel().getSelectedItem();
        if(event!=null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Warning. Deleting an event cannot be undone. Deleting an event will remove it from the system completely.");
            alert.show();
            alert.setResultConverter(buttonType -> {
                if(buttonType== ButtonType.OK)
                {
                    try {
                        eventmodel.deleteEvent(event);
                        eventmodel.refresh();
                    } catch (Exception | EventManagerException e) {
                        e.printStackTrace();
                    }
                }
                return null;
        });

    }
    }

     */
}
