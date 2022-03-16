package gui.Controller;

import be.Participant;
import bll.exception.EventDAOException;
import gui.Model.ParticipantModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ECallParticipantsController implements Initializable {

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

    }

    private void updateTableView() throws Exception {
        tableColumnFName.setCellValueFactory(new PropertyValueFactory<>("fname"));
        tableColumnLName.setCellValueFactory(new PropertyValueFactory<>("lname"));

        tableViewPartName.getItems().addAll(participantModel.getAllParticipants());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            updateTableView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
