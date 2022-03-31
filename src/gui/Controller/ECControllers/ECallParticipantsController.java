package gui.Controller.ECControllers;

import be.Participant;
import bll.exception.ParticipantManagerException;
import gui.Model.ParticipantModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ECallParticipantsController implements Initializable {

    ParticipantModel participantModel;
    Participant chosenParticipant;

    @FXML
    private Label labelEmail, labelFName, labelLName, labelTelNumber;

    @FXML
    private ListView<String> listViewEvents;

    @FXML
    private TableColumn<Participant, String> tableColumnFName;

    @FXML
    private TableColumn<Participant, String> tableColumnLName;

    @FXML
    private TableView<Participant> tableViewPartName;

    public ECallParticipantsController() throws Exception {
        participantModel = ParticipantModel.getInstance();
    }

    private void updateTableView() throws ParticipantManagerException {
        tableColumnFName.setCellValueFactory(new PropertyValueFactory<>("fname"));
        tableColumnLName.setCellValueFactory(new PropertyValueFactory<>("lname"));

        tableViewPartName.getItems().addAll(participantModel.getAllParticipants());
    }

    @FXML
    void toShowCurrentParticipants(MouseEvent event) throws ParticipantManagerException {
        chosenParticipant = tableViewPartName.getSelectionModel().getSelectedItem();
        labelEmail.setText(chosenParticipant.getEmail());
        labelFName.setText(chosenParticipant.getFname());
        labelLName.setText(chosenParticipant.getLname());
        labelTelNumber.setText(chosenParticipant.getPhoneNumber());
        listViewEvents.setItems(participantModel.participantsShowEventsById(chosenParticipant.getId()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            updateTableView();
        } catch (ParticipantManagerException e) {
            e.printStackTrace();
        }
    }
}
