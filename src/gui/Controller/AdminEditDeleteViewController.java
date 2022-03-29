package gui.Controller;

import be.Admin;
import bll.exception.AdminDAOException;
import bll.utils.DisplayError;
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

    public void handleBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminView.fxml"));
        SceneSetter.setScene(nameLabel, loader);
    }

    public void handleEditClick(ActionEvent actionEvent) throws IOException {
        EditAdminViewController controller = new EditAdminViewController(admin);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/EditAdminView.fxml"));
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminView.fxml"));
                    SceneSetter.setScene(nameLabel, loader);
                } catch (AdminDAOException | IOException e) {
                    DisplayError.displayError(e);
                }
            }
            return null;
        });

    }
}
