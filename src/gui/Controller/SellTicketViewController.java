package gui.Controller;

import be.Events;
import be.Participant;
import be.Ticket;
import be.TicketType;
import bll.exception.*;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SellTicketViewController implements Initializable {

    @FXML
    private Button btnCreateTicket,btnCreateParticipant;
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

    //TODO: Create the page to add/modify/delete a Participant in the db.

    public SellTicketViewController() {
        try {
            coordinatorModel = new CoordinatorModel();
            participantModel = new ParticipantModel();
        } catch (EventManagerException | Exception | AdminDAOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    void createParticipant(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/NewParticipantView.fxml"));
        Parent root = loader.load();
        NewParticipantViewController newParticipantViewController = loader.getController();
        newParticipantViewController.setSellTicketViewController(this);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void createTicket(ActionEvent event) throws EventManagerException {
        String message ="";
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
        Events eventChosen = tableEvent.getSelectionModel().getSelectedItem();
        Participant participant = tableParticipant.getSelectionModel().getSelectedItem();
        String ticketNumber = getAlphaNumericString(10).toUpperCase(Locale.ROOT);
        Ticket ticketSold = new Ticket(0,ticketNumber,tableTicket.getSelectionModel().getSelectedItem().getId());
        ticketSold = coordinatorModel.sellTicket(ticketSold,eventChosen,participant);
        btnCreateTicket.setVisible(false);

    }

    private void displayMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("You are missing data");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
    private String getAlphaNumericString(int n)
    {

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
