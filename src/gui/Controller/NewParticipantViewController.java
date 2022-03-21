package gui.Controller;

import be.Participant;
import bll.exception.ParticipantManagerException;
import gui.Model.ParticipantModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewParticipantViewController {

    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtEmail,txtFname,txtLname,txtPhoneNumber;

    private ParticipantModel participantModel;
    private SellTicketViewController sellTicketViewController;
    private String operationType ="creation";

    public NewParticipantViewController() {
        try {
            participantModel = new ParticipantModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void createParticipant(ActionEvent event) {
        if(!checkFields())
            return;
        if(operationType=="creation") {
            Participant participant = new Participant(0,txtFname.getText(),txtLname.getText(),txtEmail.getText(),txtPhoneNumber.getText());
            try {
                participant = participantModel.addParticipant(participant);
            } catch (ParticipantManagerException e) {
                e.printStackTrace();
            }
            sellTicketViewController.updateTableParticipant();
            Stage stage = (Stage) btnAdd.getScene().getWindow();
            stage.close();
        }
    }

    private boolean checkFields() {
        if(txtFname.getText().equals("")
        || txtLname.getText().equals("")
        || txtEmail.getText().equals("")
        || txtPhoneNumber.getText().equals(""))
            return false;
        return true;
    }

    public void setSellTicketViewController(SellTicketViewController sellTicketViewController) {
        this.sellTicketViewController = sellTicketViewController;
    }
}
