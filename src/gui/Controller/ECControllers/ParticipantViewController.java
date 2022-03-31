package gui.Controller.ECControllers;

import be.Events;
import be.Participant;
import bll.exception.*;
import bll.utils.DisplayError;
import gui.Model.CoordinatorModel;
import gui.Model.EventModel;
import gui.Model.ParticipantModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ParticipantViewController implements Initializable {

    @FXML
    private Button btnSearch, btnGenerateFile,btnDeleteParticipant,btnDeleteParticicpantFromEvent;


    @FXML
    private TableColumn<Participant, String> columnFname,columnPhone,columnLname,columnFirstNamePE,columnLastNamePE,columnPhoneNumberPE;
    @FXML
    private TextField query;
    @FXML
    private TableView<Participant> tableParticipant,tableParticipantByEvent;

    @FXML
    private Label lblMail,lblName,lblPhoneNumber;
    @FXML
    private ListView<Events> lstEventParticipant;
    @FXML
    private TableColumn<Events, String> columnEventDate,columnEventName,columnEventParticipantNumber;

    @FXML
    private TableView<Events> tableEvent;

    private RootLayoutEvenController rootLayoutEvenController;
    private ParticipantModel participantModel;
    private ObservableList<Participant> allParticipants;
    private Participant currentParticipant;
    private CoordinatorModel coordinatorModel;

    private EventModel eventModel;  // FILEMANAGER

    public ParticipantViewController() throws EventDAOException, Exception, EventManagerException {
        try {
            coordinatorModel = new CoordinatorModel();
            participantModel = new ParticipantModel();
            eventModel = new EventModel(); // FILEMANAGER + exceptions
        } catch (Exception | EventManagerException | AdminDAOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allParticipants = FXCollections.observableArrayList();
        updateTableParticipant();
        updateEventTable();
        query.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchParticipant();
            }
        });
    }

    public void updateTableParticipant() {
        columnFname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        columnLname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        try {
            tableParticipant.getItems().clear();
            allParticipants= participantModel.getAllParticipants();
            tableParticipant.getItems().addAll(allParticipants);
        } catch (ParticipantManagerException e) {
            e.printStackTrace();
        }
    }
    public void updateTableParticipantByEvent(Events event) {
        columnFirstNamePE.setCellValueFactory(new PropertyValueFactory<>("fname"));
        columnLastNamePE.setCellValueFactory(new PropertyValueFactory<>("lname"));
        columnPhoneNumberPE.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableParticipantByEvent.getItems().clear();
        tableParticipantByEvent.getItems().addAll(event.getListParticipants());

    }
    public void updateEventTable() {
        columnEventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEventDate.setCellValueFactory(new PropertyValueFactory<>("strStartDate"));
        columnEventParticipantNumber.setCellValueFactory(new PropertyValueFactory<>("numberParticipants"));
        try {
            tableEvent.getItems().clear();
            tableEvent.getItems().addAll(coordinatorModel.getAllEvents());
        } catch (EventManagerException e) {
            displayError(e);
        }
    }
    @FXML
    private void searchParticipant() {
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
    @FXML
    private void displayEventParticipant(MouseEvent event) {
        if(tableEvent.getSelectionModel().getSelectedIndex()==-1)
            return;
        updateTableParticipantByEvent(tableEvent.getSelectionModel().getSelectedItem());
    }
   @FXML
    private void toGenerateExcelFile(ActionEvent event) throws IOException {       // FILEMANAGER
        if(tableEvent.getSelectionModel().getSelectedIndex()==-1)
            return;
        Workbook workbook = eventModel.exportExcelFile(tableEvent.getSelectionModel().getSelectedItem().getId());
       FileChooser fileChooser = new FileChooser();

       //Set extension filter to .xlsx files
       FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
       fileChooser.getExtensionFilters().add(extFilter);

       //Show save file dialog
       File file = fileChooser.showSaveDialog((Stage) btnGenerateFile.getScene().getWindow());
       if (file != null) {
           try (FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath())) {
               workbook.write(outputStream);
           }
           catch (IOException ex) {
               ex.printStackTrace();
           }
       }
    }

    @FXML
    private void editParticpant(ActionEvent event) {
        try {
            if (tableParticipant.getSelectionModel().getSelectedIndex() == -1)
                return;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ECViews/NewParticipantView.fxml"));
            Parent root = loader.load();
            NewParticipantViewController newParticipantViewController = loader.getController();
            newParticipantViewController.setParticipantViewController(this);
            newParticipantViewController.setValue(tableParticipant.getSelectionModel().getSelectedItem());
            newParticipantViewController.setOperationType("modification");
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            DisplayError.displayError(e);}
    }

    @FXML
    private void deleteParticipant(ActionEvent event) {

    }
    @FXML
    void deleteParticipantFromEvent(ActionEvent event) {
        String message = "";
        if(tableParticipantByEvent.getSelectionModel().getSelectedIndex()==-1){
            message += "Please select a participant to remove \n\n";
        }
        if(tableEvent.getSelectionModel().getSelectedIndex()==-1) {
            message += "Please select an Event \n";
        }
        if(!message.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing information to continue");
            alert.setHeaderText(message);
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choose wisely...");
        alert.setHeaderText("Are you sure you want to delete the Participant "+
                tableParticipantByEvent.getSelectionModel().getSelectedItem().getFname()+" "+
                tableParticipantByEvent.getSelectionModel().getSelectedItem().getLname()+" ?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            try {
                participantModel.deleteParticipantFromEvent(tableParticipantByEvent.getSelectionModel().getSelectedItem(),
                                                            tableEvent.getSelectionModel().getSelectedItem());
                updateEventTable();
                tableParticipantByEvent.getItems().remove(tableParticipantByEvent.getSelectionModel().getSelectedItem());
            } catch (ParticipantManagerException e) {
                displayError(e);
            }
        } else {
            System.out.println("button Cancel");
            //
        }
    }
}
