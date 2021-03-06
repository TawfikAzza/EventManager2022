package gui.Controller.ECControllers;


import be.Events;
import be.TicketType;
import bll.exception.AdminDAOException;
import bll.exception.EventManagerException;
import bll.exception.ParticipantManagerException;
import bll.utils.DateUtil;

import bll.utils.DisplayError;
import gui.Model.CoordinatorModel;
import gui.util.BundleHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class NewEventController implements Initializable {

    @FXML
    private Button btnBack,btnCreate;
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
    private ListView<TicketType> lstTickets;
    @FXML
    private GridPane gridPaneNewEvent;

    private CoordinatorModel coordinatorModel;
    private EventsController eventsController;
    private RootLayoutEvenController rootLayoutEvenController;
    private Events currentEvent;
    private String operationType="creation";
    public NewEventController() throws EventManagerException, AdminDAOException, ParticipantManagerException {
            coordinatorModel = new CoordinatorModel();
    }

    public void setOperationType(String operationType) {
        this.operationType=operationType;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillComboBox();
        lstTickets.setEditable(true);
        lstTickets.setCellFactory(lv -> new TicketListCell());
    }

    public void setEventsController(EventsController eventsController){
        this.eventsController = eventsController;
    }
    @FXML
    private void createEvent(ActionEvent event) {
        try {
        if(!checkFields())
            return;
            if(operationType.equals("creation")) {
            Events eventCreated = new Events(0,txtName.getText(),txtLocation.getText(),txtDescription.getText(),getStartDate(),getEndDate(),txtItinerary.getText());
            if(lstTickets.getItems().size()>0) {
                for (TicketType ticket : lstTickets.getItems())
                    if(ticket.getType()!=null)
                        eventCreated.getTicketAvailable().add(ticket);
            }

                    eventCreated = coordinatorModel.createEvent(eventCreated);
                    goBack();

            }
            if(operationType.equals("modification")) {
                currentEvent.getTicketAvailable().clear();
                for(TicketType ticket:lstTickets.getItems())
                    if(ticket.getType()!=null)
                        currentEvent.getTicketAvailable().add(ticket);
                System.out.println();
                currentEvent.setName(txtName.getText());
                currentEvent.setStartDate(getStartDate());
                currentEvent.setEndDate(getEndDate());
                currentEvent.setDescription(txtDescription.getText());
                currentEvent.setItinerary(txtItinerary.getText());
                currentEvent.setLocation(txtLocation.getText());
                coordinatorModel.updateEvent(currentEvent);
                goBack();
            }
        } catch (EventManagerException e) {
            DisplayError.displayError(e);
        }
    }

    @FXML
    private LocalDateTime getStartDate() {
        int startHour = Integer.parseInt(startComboHour.getValue());
        int startMinute = Integer.parseInt(startComboMinute.getValue());
        return  txtStartDate.getValue().atTime(startHour,startMinute);
    }
    @FXML
    private LocalDateTime getEndDate() {
        if(endComboHour.getValue()==null || endComboMinute.getValue()==null)
            return null;
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
        String message = "";
        if(txtName.getText().equals(""))
            message += "\n Provide a valid value for the event's name ";
        if(txtLocation.getText().equals(""))
            message += "\n Provide a valid value for the event's location ";
        if(txtStartDate.getValue() == null)
            message += "\n Select a valid value for the starting date of the event ";
        if(startComboHour.getValue() == null)
            message += "\n Select a valid value for the starting hour";
        if(startComboMinute.getValue()==null)
            message += "\n Select a valid value for the starting minute ";
        if(txtDescription.getText().equals(""))
            message += "\n Provide a valid value for the event's description ";
        if(lstTickets.getItems().size()==0)
            message += "\n Provide a valid value for the event's ticket type (currently empty) ";
       /* return startComboHour.getValue() != null
                && startComboMinute.getValue() != null
                && txtStartDate.getValue() != null
                && !txtDescription.getText().equals("")
                && !txtLocation.getText().equals("")
                && !txtName.getText().equals("")
                && !(lstTickets.getItems().size()==0);*/
        if(message.equals(""))
            return true;
        else {
            DisplayError.displayMessage(message);
            return false;
        }
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
        TicketType ticket = null;
        if(txtNameTicket.getText().equals(""))
            return;
        if(txtTicketDesciption.getText().equals(""))
            return;
        ticket = new TicketType(0,txtNameTicket.getText(),txtTicketDesciption.getText());
        for (TicketType ticket1:lstTickets.getItems()) {
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
    private void goBack() {
        try {
            FXMLLoader loaderPage = new FXMLLoader();
            loaderPage.setLocation(getClass().getResource("/gui/View/ECViews/EventView.fxml"));
            if (BundleHelper.hasEnglish) loaderPage.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
            else loaderPage.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
            GridPane eventOverview = (GridPane) loaderPage.load();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ECViews/RootLayoutEvent.fxml"));
            if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
            else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
            Parent root = loader.load();

            RootLayoutEvenController rootLayoutEvenController = loader.getController();
            rootLayoutEvenController.setCenter(eventOverview);
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) btnBack.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {DisplayError.displayError(e);}
    }

    public void setValue(Events event) {

        txtName.setText(event.getName());
        for (int i = 0; i < startComboHour.getItems().size(); i++) {
            if(event.getStrStartDate().substring(11,13).equals(startComboHour.getItems().get(i))) {
                startComboHour.getSelectionModel().select(i);
                break;
            }
        }
        for (int i = 0; i < startComboMinute.getItems().size(); i++) {
            if(event.getStrStartDate().substring(14,16).equals(startComboMinute.getItems().get(i))) {
                startComboMinute.getSelectionModel().select(i);
                break;
            }
        }
        txtStartDate.setValue(DateUtil.parseDate(event.getStrStartDate().substring(0,10)));
        if(!(event.getEndDate() ==null)) {
            for (int i = 0; i < endComboHour.getItems().size(); i++) {
                if(event.getStrEndDate().substring(11,13).equals(endComboHour.getItems().get(i))) {
                    endComboHour.getSelectionModel().select(i);
                    break;
                }
            }
            for (int i = 0; i < endComboMinute.getItems().size(); i++) {
                if(event.getStrEndDate().substring(14,16).equals(endComboMinute.getItems().get(i))) {
                    endComboMinute.getSelectionModel().select(i);
                    break;
                }
            }
            txtEndDate.setValue(DateUtil.parseDate(event.getStrStartDate().substring(0,10)));
        }
        if(!event.getItinerary().equals("")) {
            txtItinerary.setText(event.getItinerary());
        }

        txtDescription.setText(event.getDescription());
        txtLocation.setText(event.getLocation());
        for(TicketType ticket:event.getTicketAvailable()){
            if(ticket.getType()!=null)
                lstTickets.getItems().add(ticket);
        }
        btnCreate.setText("Update Event");
        currentEvent=event;
    }

    public class TicketListCell extends ListCell<TicketType> {
        private final TextField textField = new TextField();

        public TicketListCell() {
            textField.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });
            textField.setOnAction(e -> {
                getItem().setType(textField.getText());
                setText(textField.getText());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            });
            setGraphic(textField);
        }

        @Override
        protected void updateItem(TicketType ticket, boolean empty) {
            super.updateItem(ticket, empty);
            if (isEditing()) {
                textField.setText(ticket.getType());
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setContentDisplay(ContentDisplay.TEXT_ONLY);
                if (empty) {
                    setText(null);
                } else {
                    setText(ticket.getType());
                }
            }
        }

        @Override
        public void startEdit() {
            super.startEdit();
            textField.setText(getItem().getType());
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.requestFocus();
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem().getType());
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    }
}

