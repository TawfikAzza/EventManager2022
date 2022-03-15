package gui.Controller;

import be.Events;
import bll.exception.EventManagerDAOException;
import gui.Model.CoordinatorModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EventsController implements Initializable {

    @FXML
    private TableColumn<Events, String> eventName;
    @FXML
    private TableColumn<Events, String> numParticipants;
    @FXML
    private TableView<Events> tableEvents;
    @FXML
    private TableColumn<Events, String> eventDate;

    private CoordinatorModel coordinatorModel;
    public EventsController() {
        try {
            coordinatorModel = new CoordinatorModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTableView();
    }

    private void updateTableView() {
        eventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        eventDate.setCellValueFactory(new PropertyValueFactory<>("strStartDate"));
        try {
            tableEvents.getItems().addAll(coordinatorModel.getAllEvents());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
