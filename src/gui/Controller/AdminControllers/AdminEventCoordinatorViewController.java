package gui.Controller.AdminControllers;

import be.Coordinator;
import bll.exception.AdminDAOException;
import bll.utils.DisplayError;
import bll.utils.LoggedInUser;
import bll.utils.SceneSetter;
import gui.Model.AdminModel;
import gui.util.BundleHelper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

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

    private AdminModel adminModel;
    private final Coordinator eventCoordinator;

    public AdminEventCoordinatorViewController(Coordinator coordinator)
    {
        this.eventCoordinator = coordinator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.adminModel = new AdminModel();
        } catch (AdminDAOException e) {
            DisplayError.displayError(e);
        }
        nameLabel.setText(eventCoordinator.getFirstName() + " " + eventCoordinator.getLastName());
        emailLabel.setText(eventCoordinator.getMail());
    }

    public void handleEditClick(ActionEvent actionEvent) {
        EditEventCoordinatorViewController controller = new EditEventCoordinatorViewController(eventCoordinator);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/EditEventCoordinatorView.fxml"));
        if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
        else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
        loader.setController(controller);
        SceneSetter.setScene(nameLabel, loader);
    }

    public void handleDeleteClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Warning. Deleting a coordinator cannot be undone. Deleting a coordinator will remove him from the system completely.");
        alert.show();
        alert.setResultConverter(buttonType -> {
            if(buttonType==ButtonType.OK)
            {
                try {
                    adminModel.deleteUser(eventCoordinator);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminView.fxml"));
                    if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
                    else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
                    SceneSetter.setScene(nameLabel, loader);
                } catch (AdminDAOException e) {
                   DisplayError.displayError(e);
                }
            }
            return null;
        });

    }

    public void handleBack(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminView.fxml"));
        if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
        else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
        SceneSetter.setScene(nameLabel, loader);
    }

}
