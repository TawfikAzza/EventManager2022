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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
    private Label lblName,lblEndDate,lblLocation,lblStartDate,lblStartTime,lblNameTicket;
   @FXML
   private TextFlow lblDescription,lblItinerary,lblDescriptionTicket;
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
    private void toggleLabelsOn() {
        for (Node n:topPane.getChildren()) {
            if(n.toString().contains("label")) {
                Label label = (Label) n;
                if(label.getId()!=null)
                    if(!label.isVisible()) {
                        label.setVisible(true);
                    }
            }
        }
        lblDescription.setVisible(true);
        lblItinerary.setVisible(true);
        lblDescriptionTicket.setVisible(true);
    }
    @FXML
    private void toggleLabelsOff() {
        for (Node n:topPane.getChildren()) {
            if(n.toString().contains("label")) {
                Label label = (Label) n;
                if(label.getId()!=null)
                    if(label.isVisible()) {
                        label.setVisible(false);
                    }
            }
        }
        lblDescription.setVisible(false);
        lblItinerary.setVisible(false);
        lblDescriptionTicket.setVisible(false);
    }

    @FXML
    private void toggleTextFieldOn() {
        for (Node n:topPane.getChildren()) {
            if(n.toString().contains("TextField")) {
                TextField textField = (TextField) n;
                if(textField.getId()!=null)
                    if(!textField.isVisible()) {
                        textField.setVisible(true);
                    }
            }
        }
    }
    @FXML
    private void toggleTextFieldOff() {
        for (Node n:topPane.getChildren()) {
            if(n.toString().contains("TextField")) {
                TextField textField = (TextField) n;
                if(textField.getId()!=null)
                    if(textField.isVisible()) {
                        textField.setVisible(false);
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
        toggleTextFieldOff();
        toggleLabelsOff();
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
        //txtStartTime.setVisible(false);
        txtEndDate.setVisible(false);
        txtItinerary.setVisible(false);
        lblName.setVisible(false);
        lblDescription.setVisible(false);
        lblLocation.setVisible(false);
        lblStartDate.setVisible(false);
       // lblStartTime.setVisible(false);
        lblEndDate.setVisible(false);
        lblItinerary.setVisible(false);
        lblDescriptionTicket.setVisible(false);
        lblNameTicket.setVisible(false);
    }
    public void displayEvent(MouseEvent mouseEvent) {
        if(tableEvents.getSelectionModel().getSelectedItem()!=null) {
            Events event = tableEvents.getSelectionModel().getSelectedItem();
            toggleLabelsOn();
            setLabelText(event);
        }
    }


    private void setTextFieldText(Events event) {
        txtName.setText(event.getName());
        txtLocation.setText(event.getLocation());
        txtStartDate.setText(event.getStrStartDate());
       // txtStartTime.setText(event.getStrStartDate().substring(10,event.getStrStartDate().length()));
        txtDescription.setText(event.getDescription());
        txtEndDate.setText(event.getStrEndDate());
        txtItinerary.setText(event.getItinerary());
    }
    private void setLabelText(Events event) {
        lblName.setText(event.getName());
        lblLocation.setText(event.getLocation());
        lblStartDate.setText(event.getStrStartDate());
        //lblStartTime.setText(event.getStrStartDate().substring(10,event.getStrStartDate().length()));
        Text eventDescription = new Text(event.getDescription());
        lblDescription.getChildren().clear();
        lblDescription.getChildren().add(eventDescription);
        lblEndDate.setText(event.getStrEndDate());
        Text eventItinerary = new Text(event.getItinerary());
        lblItinerary.getChildren().clear();
        lblItinerary.getChildren().add(eventItinerary);
        lblNameTicket.setText(event.getName());
        Text eventDescriptionTicket = new Text(event.getDescription());
        lblDescriptionTicket.getChildren().clear();
        lblDescriptionTicket.getChildren().add(eventDescriptionTicket);
    }
    public void toggleVisible(ActionEvent actionEvent) {
       // toggleLabels();
        //toggleTextField();
    }

    public void updateEvent(ActionEvent actionEvent) {
    }
}
