package gui.Controller.ECControllers;

import be.Participant;
import bll.exception.ParticipantManagerException;
import bll.utils.DisplayError;
import gui.Model.ParticipantModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private ParticipantViewController participantViewController;
    private String operationType ="creation";
    Participant currentParticipant;
    public NewParticipantViewController() {
        try {
            participantModel = new ParticipantModel();
        } catch (Exception | ParticipantManagerException e) {
            DisplayError.displayError(e);
        }
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @FXML
    void createParticipant(ActionEvent event) {
        if(!checkFields())
            return;
        if(operationType.equals("creation")) {
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
        if(operationType.equals("modification")) {
            currentParticipant.setFname(txtFname.getText());
            currentParticipant.setLname(txtLname.getText());
            currentParticipant.setEmail(txtEmail.getText());
            currentParticipant.setPhoneNumber(txtPhoneNumber.getText());
            try {
                participantModel.updateParticipant(currentParticipant);
            } catch (ParticipantManagerException e) {
                displayError(e);
            }
            if(sellTicketViewController!=null)
                sellTicketViewController.updateTableParticipant();
            if(participantViewController!=null)
                participantViewController.updateTableParticipant();
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
    public void setParticipantViewController(ParticipantViewController participantViewController) {
        this.participantViewController = participantViewController;
    }
    public void setCurrentParticipant(Participant currentParticipant) {
        this.currentParticipant = currentParticipant;
    }

    public void setValue(Participant participant) {
        currentParticipant=participant;
        txtFname.setText(participant.getFname());
        txtLname.setText(participant.getLname());
        txtPhoneNumber.setText(participant.getPhoneNumber());
        txtEmail.setText(participant.getEmail());
        btnAdd.setText("Update");
    }
    private void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong...");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }
}
