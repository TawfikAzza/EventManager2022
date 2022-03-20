package gui.Controller;

import be.Events;
import be.Participant;
import be.Ticket;
import bll.exception.AdminLogicException;
import bll.exception.EventManagerException;
import gui.Model.CoordinatorModel;
import gui.Model.ParticipantModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SellTicketViewController implements Initializable {

    @FXML
    private Button btnCreateParticipant;
    @FXML
    private TableColumn<Events, String> columnNameEvent,columnDateEvent;
    @FXML
    private TableColumn<Participant, String> columnFirstName,columnLastName,columnPhoneNumber;
    @FXML
    private TableColumn<Ticket, String> columnTicketType,columnTicketDescription;
    @FXML
    private TextField searchQuery;
    @FXML
    private TableView<Events> tableEvent;
    @FXML
    private TableView<Participant> tableParticipant;
    @FXML
    private TableView<Ticket> tableTicket;

    private CoordinatorModel coordinatorModel;
    private ParticipantModel participantModel;
    private RootLayoutEvenController rootLayoutEvenController;
    private ObservableList<Participant> allParticipants;

    //TODO: Create the page to add/modify/delete a Participant in the db.

    public SellTicketViewController() {
        try {
            coordinatorModel = new CoordinatorModel();
            participantModel = new ParticipantModel();
        } catch (EventManagerException | AdminLogicException | Exception e) {
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

    private void updateTableParticipant() {
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("fname"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lname"));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        try {
            allParticipants= participantModel.getAllParticipants();
            tableParticipant.getItems().addAll(allParticipants);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateTableEvents() {
        columnNameEvent.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDateEvent.setCellValueFactory(new PropertyValueFactory<>("strStartDate"));
        try {
            tableEvent.getItems().addAll(coordinatorModel.getAllEvents());
        } catch (EventManagerException e) {
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
}
