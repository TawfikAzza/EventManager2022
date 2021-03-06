package gui.Controller.ECControllers;

import be.Events;
import be.Participant;
import be.Ticket;
import be.TicketType;
import bll.exception.*;
import bll.utils.DisplayError;
import gui.Model.CoordinatorModel;
import gui.Model.ParticipantModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

public class SellTicketViewController implements Initializable {

    @FXML
    private Button btnCreateTicket,btnCreateParticipant,btnTicket;
    @FXML
    private TableColumn<Events, String> columnNameEvent,columnDateEvent;
    @FXML
    private TableColumn<Participant, String> columnFirstName,columnLastName,columnPhoneNumber;
    @FXML
    private TableColumn<TicketType, String> columnTicketType,columnTicketDescription;
    @FXML
    private TextField searchQuery;
    @FXML
    private TableView<Events> tableEvent;
    @FXML
    private TableView<Participant> tableParticipant;
    @FXML
    private TableView<TicketType> tableTicket;

    private CoordinatorModel coordinatorModel;
    private ParticipantModel participantModel;
    private RootLayoutEvenController rootLayoutEvenController;
    private ObservableList<Participant> allParticipants;
    private Participant ticketParticipant;
    private Events ticketEvent;
    private Ticket ticketSold;
    private TicketType ticketTypeSold;
    //TODO: Create the page to add/modify/delete a Participant in the db.

    public SellTicketViewController() {
        try {
            coordinatorModel = new CoordinatorModel();
            participantModel = new ParticipantModel();
        } catch (EventManagerException | Exception | AdminDAOException | ParticipantManagerException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnTicket.setVisible(false);
        searchQuery.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchParticipant(new ActionEvent());
            }
        });
        updateTableEvents();
        updateTableParticipant();
    }

    public void updateTableParticipant() {
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("fname"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lname"));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        try {
            allParticipants= participantModel.getAllParticipants();
            tableParticipant.getItems().clear();
            tableParticipant.getItems().addAll(allParticipants);
        } catch (ParticipantManagerException e) {
            e.printStackTrace();
        }
    }
    private void updateTableEvents() {
        columnNameEvent.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDateEvent.setCellValueFactory(new PropertyValueFactory<>("strStartDate"));
        try {
            tableEvent.getItems().addAll(coordinatorModel.getAllEventsWithTicketType());
        } catch (EventManagerException | EventDAOException e) {
            e.printStackTrace();
        }

    }
    private void updateTableTicketType(Events event) {
        if(tableEvent.getSelectionModel().getSelectedIndex()==-1)
            return;;
        columnTicketType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnTicketDescription.setCellValueFactory(new PropertyValueFactory<>("benefit"));
        tableTicket.getItems().addAll(event.getTicketAvailable());
    }
    @FXML
    void displayTicketType(MouseEvent event) {
        btnCreateTicket.setVisible(true);
        if(tableEvent.getSelectionModel().getSelectedIndex()==-1)
            return;
        tableTicket.getItems().clear();
        updateTableTicketType(tableEvent.getSelectionModel().getSelectedItem());
    }



    public void setMainApp(RootLayoutEvenController rootLayoutEvenController) {
        this.rootLayoutEvenController=rootLayoutEvenController;
    }
    @FXML
    void searchParticipant(ActionEvent event) {
        ObservableList<Participant> searchedParticipants = FXCollections.observableArrayList();

        for (Participant participant:allParticipants) {
            if(participant.getFname().toLowerCase(Locale.ROOT).contains(searchQuery.getText().toLowerCase(Locale.ROOT))
                    || participant.getLname().toLowerCase(Locale.ROOT).contains(searchQuery.getText().toLowerCase(Locale.ROOT))
                    || participant.getPhoneNumber().toLowerCase(Locale.ROOT).contains(searchQuery.getText().toLowerCase(Locale.ROOT))
                    || participant.getEmail().toLowerCase(Locale.ROOT).contains(searchQuery.getText().toLowerCase(Locale.ROOT))) {
                searchedParticipants.add(participant);
            }

        }
        tableParticipant.getItems().clear();
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("fname"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lname"));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableParticipant.getItems().addAll(searchedParticipants);

    }
    @FXML
    void createParticipant(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ECViews/NewParticipantView.fxml"));
            Parent root = loader.load();
            NewParticipantViewController newParticipantViewController = loader.getController();
            newParticipantViewController.setSellTicketViewController(this);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            DisplayError.displayError(e);}
    }


    @FXML
    void createTicket(ActionEvent event) {
        try {
            String message = "";
            if (tableEvent.getSelectionModel().getSelectedIndex() == -1) {
                message += "Select an Event \n";
            }
            if (tableTicket.getSelectionModel().getSelectedIndex() == -1)
                message += "Select a ticket type \n";
            if (tableParticipant.getSelectionModel().getSelectedIndex() == -1)
                message += "Select a participant ";
            if (!message.equals("")) {
                displayMessage(message);
                return;
            }
            Events eventChosen = tableEvent.getSelectionModel().getSelectedItem();
            Participant participant = tableParticipant.getSelectionModel().getSelectedItem();
            ticketParticipant = participant;
            ticketEvent = eventChosen;
            String ticketNumber = getAlphaNumericString(10).toUpperCase(Locale.ROOT);
            ticketTypeSold = tableTicket.getSelectionModel().getSelectedItem();
            ticketSold = new Ticket(0, ticketNumber, ticketTypeSold.getId());
            ticketSold = coordinatorModel.sellTicket(ticketSold, eventChosen, participant);
            btnCreateTicket.setVisible(false);
            btnTicket.setVisible(true);
        }
        catch (EventManagerException e) {DisplayError.displayError(e);}
    }

    @FXML
    void openTicket(ActionEvent event) {
      /* String message ="";
        if(tableEvent.getSelectionModel().getSelectedIndex()==-1) {
            message += "Select an Event \n";
        }
        if(tableTicket.getSelectionModel().getSelectedIndex()==-1)
            message += "Select a ticket type \n";
        if(tableParticipant.getSelectionModel().getSelectedIndex()==-1)
            message += "Select a participant ";
        if(!message.equals("")) {
            displayMessage(message);
            return;
        }
        //TEST PART TO DELETE !!!!!
        ticketParticipant=tableParticipant.getSelectionModel().getSelectedItem();
        ticketSold = new Ticket(25,"THISISATEST",tableTicket.getSelectionModel().getSelectedItem().getId());
        ticketEvent = tableEvent.getSelectionModel().getSelectedItem();
        ticketTypeSold = tableTicket.getSelectionModel().getSelectedItem();
        //END TEST PART*/

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ECViews/TicketParticipant.fxml"));
            Parent root = loader.load();
            TicketParticipantController ticketParticipantController = loader.getController();
            AnchorPane anchorPane = (AnchorPane) root;
            ticketParticipantController.setParticipant(ticketParticipant);
            ticketParticipantController.setEvent(ticketEvent);
            ticketParticipantController.setTicket(ticketSold);
            ticketParticipantController.setTicketType(ticketTypeSold);
            ticketParticipantController.setAnchorPane(anchorPane);
            ticketParticipantController.setValues();
    //ticketParticipantController.captureAndSaveDisplay();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {DisplayError.displayError(e);}
    }

    private void displayMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("You are missing data");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
    private String getAlphaNumericString(int n)
    {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
