package gui.Controller;

import be.Participant;
import bll.exception.EventDAOException;
import gui.Model.ParticipantModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ECallParticipantsController {

    ParticipantModel participantModel;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelFName;

    @FXML
    private Label labelLName;

    @FXML
    private Label labelTelNumber;

    @FXML
    private TableColumn<Participant, String> tableColumnFName;

    @FXML
    private TableColumn<Participant, String> tableColumnLName;

    @FXML
    private TableView<Participant> tableViewPartName;

    public ECallParticipantsController() throws Exception {
        participantModel = ParticipantModel.getInstance();
        updateTableView();
    }

    private void updateTableView() throws Exception {
        tableColumnFName.setCellValueFactory(new PropertyValueFactory<>("fname"));
        tableColumnLName.setCellValueFactory(new PropertyValueFactory<>("lname"));
        tableViewPartName.getItems().setAll(participantModel.getAllParticipants());
    }


}
