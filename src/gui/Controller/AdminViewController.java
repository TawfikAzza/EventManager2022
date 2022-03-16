package gui.Controller;

import be.Coordinator;
import bll.utils.CurrentEventCoordinator;
import dal.interfaces.IAdminDAO;
import gui.Model.AdminModel;
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
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {
    @FXML
    private TableColumn<Coordinator, String> tableColumnFirstName;
    @FXML
    private TableColumn<Coordinator, String> tableColumnLastName;
    @FXML
    private TableColumn<Event, String> tableColumnEventName;
    @FXML
    private TableColumn<Coordinator, String> tableColumnNumberOfEvents;
    @FXML
    private TableColumn<Event, String> tableColumnNumberOfCoordinators;
    @FXML
    private TableView<Event> eventTableView;
    @FXML
    private TableView<Coordinator> coordinatorTableView;
    @FXML
    private Button newCoordinatorBtn;

    private AdminModel adminModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.adminModel = new AdminModel();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ObservableList<Coordinator> obsList = FXCollections.observableArrayList();
        obsList.addAll(adminModel.getAllCoordinators());
        this.coordinatorTableView.setItems(obsList);
        this.initTables();
    }

    private void initTables() {
        //left side
        //this.tableColumnEventName.setCellValueFactory(new PropertyValueFactory<>("Event Name"));
        //this.tableColumnNumberOfCoordinators.setCellValueFactory(new PropertyValueFactory<>("Number of Events"));

        //right side
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
