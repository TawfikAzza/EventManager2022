package gui.Controller;

import be.Participant;
import gui.Model.ParticipantModel;
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

import java.net.URL;
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

    private RootLayoutEvenController rootLayoutEvenController;
    private ParticipantModel participantModel;
    private ObservableList<Participant> allParticipants;

    public ParticipantViewController() {
        try {
            participantModel = new ParticipantModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allParticipants = FXCollections.observableArrayList();
        updateTable();
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
            System.out.println(participant.getFname().toLowerCase(Locale.ROOT).contains(query.getText().toLowerCase(Locale.ROOT)));
            if(participant.getFname().toLowerCase(Locale.ROOT).contains(query.getText().toLowerCase(Locale.ROOT))
                || participant.getLname().contains(query.getText())
                || participant.getPhoneNumber().contains(query.getText())) {
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
}
