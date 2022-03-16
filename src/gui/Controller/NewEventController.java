package gui.Controller;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewEventController {

    @FXML
    private JFXButton btnBack,btnCreate;
    @FXML
    private ComboBox<String> startComboHour,startComboMinute,endComboHour,endComboMinute;


    @FXML
    private TextArea txtDescription,txtItinerary;

    @FXML
    private TextField txtEndDate,txtLocation;

    @FXML
    private TextField txtName,txtStartDate;


    @FXML
    void createEvent(ActionEvent event) {

    }

    @FXML
    void goBack(ActionEvent event) {

    }

}

