package gui.Controller.ECControllers;

import be.Events;
import be.TicketType;
import bll.exception.AdminDAOException;
import bll.exception.EventDAOException;
import bll.exception.EventManagerException;
import bll.exception.ParticipantManagerException;
import bll.utils.DisplayError;
import bll.utils.SceneSetter;
import gui.Model.CoordinatorModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventsController implements Initializable {

    @FXML
    private AnchorPane topPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TableColumn<Events, String> eventName;
    @FXML
    private TableColumn<Events, String> numParticipants;
    @FXML
    private TableView<Events> tableEvents;
    @FXML
    private TableColumn<Events, String> eventDate;
    @FXML
    private Button btnAdd,btnAllParticipants;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Label lblName,lblEndDate,lblLocation,lblStartDate,lblStartTime,lblNameTicket;
   @FXML
   private TextFlow lblDescription,lblItinerary,lblDescriptionTicket;
    @FXML
    private Button testBtn;
    @FXML
    private TextField txtName,txtLocation,txtStartDate,txtStartTime,txtEndDate;
    @FXML
    private TextArea txtDescription,txtItinerary;
    @FXML
    private ListView<TicketType> lstTickets;
    private Events currentEvent;

    private RootLayoutEvenController rootLayoutEvenController;
    private CoordinatorModel coordinatorModel;
    public EventsController() {
        try {
            coordinatorModel = new CoordinatorModel();
        } catch (EventManagerException | AdminDAOException | ParticipantManagerException e) {
            displayError(e);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleTextFieldOff();
        toggleLabelsOff();
        updateTableView();
    }

    public void updateTableView() {
        eventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        eventDate.setCellValueFactory(new PropertyValueFactory<>("strStartDate"));
        numParticipants.setCellValueFactory((new PropertyValueFactory<>("numberParticipants")));
        try {
            tableEvents.getItems().addAll(coordinatorModel.getAllEventsWithTicketType());
        } catch (EventDAOException | EventManagerException e) {
            displayError(e);
        }
    }
    private void displayError(Throwable t) {
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

    }
    public void displayEvent(MouseEvent mouseEvent) {
        if(tableEvents.getSelectionModel().getSelectedItem()!=null) {
            Events event = tableEvents.getSelectionModel().getSelectedItem();
            toggleTextFieldOff();
            toggleLabelsOn();
            setLabelText(event);
            setListView();
        }
    }

    private void setListView() {

            lstTickets.getItems().clear();

        for (TicketType ticket:tableEvents.getSelectionModel().getSelectedItem().getTicketAvailable()) {
            if(ticket.getType()!=null)
             lstTickets.getItems().add(ticket);
        }

    }
    private void setTextFieldText(Events event) {
        txtName.setText(event.getName());
        txtLocation.setText(event.getLocation());
        txtStartDate.setText(event.getStrStartDate());


        txtEndDate.setText(event.getStrEndDate());

    }
    private void setLabelText(Events event) {
        lblName.setText(event.getName());
        lblLocation.setText(event.getLocation());
        lblStartDate.setText(event.getStrStartDate());
        //lblStartTime.setText(event.getStrStartDate().substring(10,event.getStrStartDate().length()));
        //System.out.println(event.getStrStartDate().substring(0,10));
        Text eventDescription = new Text(event.getDescription());
        lblDescription.getChildren().clear();
        lblDescription.getChildren().add(eventDescription);
        lblEndDate.setText(event.getStrEndDate());
        Text eventItinerary = new Text(event.getItinerary());
        lblItinerary.getChildren().clear();
        lblItinerary.getChildren().add(eventItinerary);

    }

    public void toggleVisible(ActionEvent actionEvent) {

    }

    @FXML
    void deleteEvent(ActionEvent event) {
        

    }

    @FXML
    void editEvent(ActionEvent event) {
        if(tableEvents.getSelectionModel().getSelectedIndex()==-1)
            return;
        currentEvent=tableEvents.getSelectionModel().getSelectedItem();
        FXMLLoader loaderPage = new FXMLLoader();
        loaderPage.setLocation(getClass().getResource("/gui/View/ECViews/NewEventView.fxml"));
        GridPane eventOverview = null;
        try {
            eventOverview = (GridPane) loaderPage.load();

            NewEventController newEventController = loaderPage.getController();
            newEventController.setOperationType("modification");
            newEventController.setValue(currentEvent);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ECViews/RootLayoutEvent.fxml"));
            Parent root = loader.load();

            RootLayoutEvenController rootLayoutEvenController = loader.getController();
            rootLayoutEvenController.setCenter(eventOverview);
            Scene scene = new Scene(root, 800, 600);
            Stage primaryStage = (Stage) btnEdit.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            DisplayError.displayError(e);
        }
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


    public void setMainApp(RootLayoutEvenController rootLayoutEvenController) {
        this.rootLayoutEvenController=rootLayoutEvenController;
    }

    public void displayTicket(MouseEvent mouseEvent) {
        if(lstTickets.getSelectionModel().getSelectedIndex()==-1)
            return;
        TicketType ticket = lstTickets.getSelectionModel().getSelectedItem();
        Text ticketDescription = new Text(ticket.getBenefit());
        lblDescriptionTicket.getChildren().clear();
        lblDescriptionTicket.getChildren().add(ticketDescription);
    }
}
