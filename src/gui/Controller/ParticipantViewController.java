package gui.Controller;

import be.Events;
import be.Participant;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ParticipantViewController implements Initializable {

    @FXML
    private Button btnSearch;
    @FXML
    private TableColumn<Participant, String> columnFname,columnPhone,columnLname;
    @FXML
    private TextField query;
    @FXML
    private TableView<Participant> tableParticipant;
    @FXML
    private Label lblMail,lblName,lblPhoneNumber;
    @FXML
    private ListView<Events> lstEventParticipant;

    private RootLayoutEvenController rootLayoutEvenController;
    private ParticipantModel participantModel;
    private ObservableList<Participant> allParticipants;
    private Participant currentParticipant;
    private CoordinatorModel coordinatorModel;
    public ParticipantViewController() {
        try {
            coordinatorModel = new CoordinatorModel();
            participantModel = new ParticipantModel();
        } catch (Exception | AdminLogicException | EventManagerException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allParticipants = FXCollections.observableArrayList();
        updateTable();
        query.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchParticipant(new ActionEvent());
            }
        });
    }

    public void updateTable() {
        columnFname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        columnLname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        try {
            allParticipants= participantModel.getAllParticipants();
            tableParticipant.getItems().addAll(allParticipants);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void searchParticipant(ActionEvent event) {
        ObservableList<Participant> searchedParticipants = FXCollections.observableArrayList();

        for (Participant participant:allParticipants) {
            if(participant.getFname().toLowerCase(Locale.ROOT).contains(query.getText().toLowerCase(Locale.ROOT))
                || participant.getLname().toLowerCase(Locale.ROOT).contains(query.getText().toLowerCase(Locale.ROOT))
                || participant.getPhoneNumber().toLowerCase(Locale.ROOT).contains(query.getText().toLowerCase(Locale.ROOT))
                || participant.getEmail().toLowerCase(Locale.ROOT).contains(query.getText().toLowerCase(Locale.ROOT))) {
                searchedParticipants.add(participant);
            }

        }
        tableParticipant.getItems().clear();
        columnFname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        columnLname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableParticipant.getItems().addAll(searchedParticipants);

    }

    public void setMainApp(RootLayoutEvenController rootLayoutEvenController) {
        this.rootLayoutEvenController=rootLayoutEvenController;
    }
    private void setLabelParticipant(Participant participant) {
        if(participant==null)
            return;
        lblName.setText(participant.getFname()+" "+participant.getLname());
        lblMail.setText(participant.getEmail());
        lblPhoneNumber.setText(participant.getPhoneNumber());
    }
    private void setListViewEvents(Participant participant) {
        List<Events> listEvents = new ArrayList<>();
        try {
            lstEventParticipant.getItems().clear();
            listEvents = coordinatorModel.getParticipantEvent(participant);
            lstEventParticipant.getItems().addAll(listEvents);
        } catch (EventManagerException e) {
            displayError(e);
        }
    }
    public void displayParticipant(MouseEvent mouseEvent) {
        if(tableParticipant.getSelectionModel().getSelectedIndex()==-1)
            return;
        currentParticipant = tableParticipant.getSelectionModel().getSelectedItem();
        setLabelParticipant(currentParticipant);
        setListViewEvents(currentParticipant);
    }

    private void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong...");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
}
