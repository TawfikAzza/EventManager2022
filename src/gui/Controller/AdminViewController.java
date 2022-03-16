package gui.Controller;

import be.Coordinator;
import be.Events;
import bll.exception.EventDAOException;
import bll.utils.CurrentEventCoordinator;
import dal.interfaces.IAdminDAO;
import gui.Model.AdminModel;
import gui.Model.EventModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {
    public TableColumn<Events, LocalDateTime> tableColumnEventDate;
    @FXML
    private TableColumn<Coordinator, String> tableColumnFirstName;
    @FXML
    private TableColumn<Coordinator, String> tableColumnLastName;
    @FXML
    private TableColumn<Events, String> tableColumnEventName;
    @FXML
    private TableColumn<Coordinator, String> tableColumnNumberOfEvents;
    @FXML
    private TableColumn<Events, String> tableColumnNumberOfCoordinators;
    @FXML
    private TableView<Events> eventTableView;
    @FXML
    private TableView<Coordinator> coordinatorTableView;
    @FXML
    private Button newCoordinatorBtn;

    private AdminModel adminModel;
    private EventModel eventmodel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.adminModel = new AdminModel();
            this.eventmodel = new EventModel();

        ObservableList<Coordinator> obsListCoordinator = FXCollections.observableArrayList();
        obsListCoordinator.addAll(adminModel.getAllCoordinators());
        this.coordinatorTableView.setItems(obsListCoordinator);
        ObservableList<Events> obsListEvent = FXCollections.observableArrayList();
        obsListEvent.addAll(eventmodel.getAllEvents());
        this.eventTableView.setItems(obsListEvent);
        this.initTables();
        } catch (Exception | EventDAOException e) {
            e.printStackTrace();
        }
    }

    private void initTables() {
        this.tableColumnEventName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.tableColumnEventDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        //this.tableColumnNumberOfCoordinators.setCellValueFactory(new PropertyValueFactory<>("Number of Events"));

        this.tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        //this.tableColumnNumberOfEvents.setCellValueFactory(new PropertyValueFactory<>());
    }

    public void handleNewClick(ActionEvent actionEvent) throws IOException {
        setScene("/gui/View/AddEventCoordinatorView.fxml");
    }

    public void handleCoordinatorClick(MouseEvent mouseEvent) throws IOException {
        Coordinator coordinator = coordinatorTableView.getSelectionModel().getSelectedItem();
        if(coordinator!=null)
        {
            if(mouseEvent.getClickCount()==2) {
                CurrentEventCoordinator.setInstance(coordinator);
                setScene("/gui/View/AdminEventCoordinatorView.fxml");
            }
        }
    }

    private void setScene(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) newCoordinatorBtn.getScene().getWindow();
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
