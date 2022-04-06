package gui.Controller.AdminControllers;

import be.Admin;
import bll.exception.AdminDAOException;
import bll.utils.DisplayError;
import bll.utils.LoggedInUser;
import bll.utils.SceneSetter;
import gui.Model.AdminModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class AdminEditDeleteViewController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;

    private AdminModel adminModel;
    private Admin admin;

    public AdminEditDeleteViewController(Admin admin)
    {
        this.admin = admin;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.nameLabel.setText(admin.getFirstName() + " " + admin.getLastName());
        this.emailLabel.setText(admin.getMail());
        try {
            this.adminModel = new AdminModel();
        } catch (AdminDAOException e) {
            DisplayError.displayError(e);
        }

    }

    public void handleBack(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminView.fxml"));
        SceneSetter.setScene(nameLabel, loader);
    }

    public void handleEditClick(ActionEvent actionEvent) {
        EditAdminViewController controller = new EditAdminViewController(admin);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/EditAdminView.fxml"));
        loader.setController(controller);
        SceneSetter.setScene(nameLabel, loader);
    }

    public void handleDeleteClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Warning. Deleting an admin cannot be undone. Deleting an admin will remove them from the system completely.");
        alert.show();
        alert.setResultConverter(buttonType -> {
            if(buttonType== ButtonType.OK)
            {
                try {
                    adminModel.deleteUser(admin);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminView.fxml"));
                    SceneSetter.setScene(nameLabel, loader);
                } catch (AdminDAOException e) {
                    DisplayError.displayError(e);
                }
            }
            return null;
        });

    }
}
