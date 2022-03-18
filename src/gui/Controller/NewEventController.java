package gui.Controller;


import be.Events;
import be.Ticket;
import bll.exception.AdminLogicException;
import bll.exception.EventDAOException;
import bll.exception.EventManagerException;
import com.jfoenix.controls.JFXButton;
import gui.Model.CoordinatorModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewEventController implements Initializable {

    @FXML
    private JFXButton btnBack,btnCreate;
    @FXML
    private ComboBox<String> startComboHour,startComboMinute,endComboHour,endComboMinute;

    @FXML
    private DatePicker txtStartDate,txtEndDate;
    @FXML
    private TextArea txtDescription,txtItinerary,txtTicketDesciption;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtName,txtNameTicket;
    @FXML
    private ListView<Ticket> lstTickets;

    private CoordinatorModel coordinatorModel;
    private EventsController eventsController;
    private RootLayoutEvenController rootLayoutEvenController;
    private Events currentEvent;
    public NewEventController() throws EventManagerException, AdminLogicException {
            coordinatorModel = new CoordinatorModel();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillComboBox();
    }

    public void setEventsController(EventsController eventsController){
        this.eventsController = eventsController;
    }
    @FXML
    private void createEvent(ActionEvent event) throws EventManagerException {
        if(!checkFields())
            return;
        Events eventCreated = new Events(0,txtName.getText(),txtLocation.getText(),txtDescription.getText(),getStartDate(),getEndDate(),txtItinerary.getText());
        eventCreated = coordinatorModel.createEvent(eventCreated);
        eventsController.updateTableView();
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }
    @FXML
    private LocalDateTime getStartDate() {
        int startHour = Integer.parseInt(startComboHour.getValue());
        int startMinute = Integer.parseInt(startComboMinute.getValue());
        return  txtStartDate.getValue().atTime(startHour,startMinute);
    }
    @FXML
    private LocalDateTime getEndDate() {
        int endHour = Integer.parseInt(endComboHour.getValue());
        int endMinute = Integer.parseInt(endComboMinute.getValue());
        return txtEndDate.getValue().atTime(endHour,endMinute);
    }
    @FXML
    void goBack(ActionEvent event) {

    }
    public void setCurrentEvent(Events event){
        this.currentEvent = event;
    }

    private boolean checkFields() {
        return startComboHour.getValue() != null
                && startComboMinute.getValue() != null
                && endComboHour.getValue() != null
                && endComboMinute.getValue() != null
                && txtStartDate.getValue() != null
                && !txtDescription.getText().equals("")
                && !txtLocation.getText().equals("")
                && !txtName.getText().equals("");
    }

    private void fillComboBox(){
        List<String> arrayHours = new ArrayList<>();
        List<String> arrayMinutes = new ArrayList<>();
        arrayHours.add("00");
        arrayHours.add("01");
        arrayHours.add("02");
        arrayHours.add("03");
        arrayHours.add("04");
        arrayHours.add("05");
        arrayHours.add("06");
        arrayHours.add("07");
        arrayHours.add("08");
        arrayHours.add("09");
        arrayHours.add("10");
        arrayHours.add("11");
        arrayHours.add("12");
        arrayHours.add("13");
        arrayHours.add("14");
        arrayHours.add("15");
        arrayHours.add("16");
        arrayHours.add("17");
        arrayHours.add("18");
        arrayHours.add("19");
        arrayHours.add("20");
        arrayHours.add("21");
        arrayHours.add("22");
        arrayHours.add("23");
        arrayMinutes.add("00");
        arrayMinutes.add("15");
        arrayMinutes.add("30");
        arrayMinutes.add("45");
        startComboHour.getItems().clear();
        endComboHour.getItems().clear();
        startComboMinute.getItems().clear();
        endComboMinute.getItems().clear();

        startComboHour.getItems().addAll(arrayHours);
        endComboHour.getItems().addAll(arrayHours);
        startComboMinute.getItems().addAll(arrayMinutes);
        endComboMinute.getItems().addAll(arrayMinutes);
    }

    public void addTicket(ActionEvent actionEvent) {
        Ticket ticket = null;
        if(txtNameTicket.getText().equals(""))
            return;
        if(txtTicketDesciption.getText().equals(""))
            return;
        ticket = new Ticket(0,txtNameTicket.getText(),txtTicketDesciption.getText());
        for (Ticket ticket1:lstTickets.getItems()) {
            if(ticket.getType().equals(ticket1.getType()))
                return;
        }

        lstTickets.getItems().add(ticket);

    }

    public void removeTicket(ActionEvent actionEvent) {
        if(lstTickets.getSelectionModel().getSelectedIndex()==-1)
            return;
        lstTickets.getItems().remove(lstTickets.getSelectionModel().getSelectedIndex());

    }

    public void setMainApp(RootLayoutEvenController rootLayoutEvenController) {
        this.rootLayoutEvenController=rootLayoutEvenController;
    }
}

