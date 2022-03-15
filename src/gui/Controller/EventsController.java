package gui.Controller;

import be.Events;
import bll.exception.EventDAOException;
import com.jfoenix.controls.JFXButton;
import gui.Model.CoordinatorModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EventsController implements Initializable {
    @FXML
    private AnchorPane topPane;
    @FXML
    private TableColumn<Events, String> eventName;
    @FXML
    private TableColumn<Events, String> numParticipants;
    @FXML
    private TableView<Events> tableEvents;
    @FXML
    private TableColumn<Events, String> eventDate;
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private Label lblName,lblDescription,lblEndDate,lblItinerary,lblLocation,lblStartDate,lblStartTime;

    @FXML
    private Button testBtn;
    @FXML
    private TextField txtName,txtLocation,txtStartDate,txtStartTime,txtItinerary,txtEndDate,txtDescription;



    @FXML
    void addEvent(ActionEvent event) {

    }

    @FXML
    void deleteEvent(ActionEvent event) {

    }

    @FXML
    void editEvent(ActionEvent event) {

    }

    @FXML
    private void toggleLabels() {
        for (Node n:topPane.getChildren()) {
            if(n.toString().contains("label")) {
                Label label = (Label) n;
                if(label.getId()!=null)
                    if(label.isVisible()) {
                        label.setVisible(false);
                    } else {
                        label.setVisible(true);
                    }
            }
        }
    }

    private void toggleTextField() {
        for (Node n:topPane.getChildren()) {
            if(n.toString().contains("TextField")) {
                TextField textField = (TextField) n;
                if(textField.getId()!=null)
                    if(textField.isVisible()) {
                        textField.setVisible(false);
                    } else {
                        textField.setVisible(true);
                    }
            }
        }
    }
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
        } catch (EventDAOException e) {
            displayError(e);
        }
    }
    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong...");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
    private void hideLabels() {
        txtName.setVisible(false);
        txtDescription.setVisible(false);
        txtLocation.setVisible(false);
        txtStartDate.setVisible(false);
        txtStartTime.setVisible(false);
        txtEndDate.setVisible(false);
        txtItinerary.setVisible(false);
        lblName.setVisible(false);
        lblDescription.setVisible(false);
        lblLocation.setVisible(false);
        lblStartDate.setVisible(false);
        lblStartTime.setVisible(false);
        lblEndDate.setVisible(false);
        lblItinerary.setVisible(false);
    }
    public void displayEvent(MouseEvent mouseEvent) {
        if(tableEvents.getSelectionModel().getSelectedItem()!=null) {

        }
    }

    public void toggleVisible(ActionEvent actionEvent) {
       // toggleLabels();
        toggleTextField();
    }
}
