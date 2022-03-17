package gui.Controller;

import be.Coordinator;
import bll.utils.CurrentEventCoordinator;
import gui.Model.AdminModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminEventCoordinatorViewController implements Initializable {
    @FXML
    private TableView<Event> eventTableView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableColumn<Event, String> tableColumnEventName;
    @FXML
    private TableColumn tableColumnDate;

    AdminModel adminModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.adminModel = new AdminModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Coordinator coordinator = CurrentEventCoordinator.getInstance();
        nameLabel.setText(coordinator.getFirstName() + " " + coordinator.getLastName());
        emailLabel.setText(coordinator.getMail());
    }

    public void handleEditClick(ActionEvent actionEvent) throws IOException {
        setScene("/gui/View/EditEventCoordinatorView.fxml");
    }

    public void handleDeleteClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Warning. Deleting a coordinator cannot be undone. Deleting a coordinator will remove him from the system completely.");
        alert.show();
        alert.setResultConverter(buttonType -> {
            if(buttonType==ButtonType.OK)
            {
                adminModel.deleteEventCoordinator(CurrentEventCoordinator.getInstance());
                CurrentEventCoordinator.setInstance(null);
                try {
                    setScene("/gui/View/AdminView.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        });

    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        setScene("/gui/View/AdminView.fxml");
    }

    private void setScene(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) editButton.getScene().getWindow();
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
