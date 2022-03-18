package gui.Controller;

import be.Admin;
import bll.utils.CurrentAdmin;
import bll.utils.SceneSetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminEditDeleteViewController implements Initializable {
    public Label nameLabel;
    public Label emailLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Admin admin = CurrentAdmin.getInstance();
        this.nameLabel.setText(admin.getFirstName() + " " + admin.getLastName());
        this.emailLabel.setText(admin.getMail());
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminView.fxml"));
        SceneSetter.setScene(nameLabel, loader);
    }

    public void handleEditClick(ActionEvent actionEvent) {
    }

    public void handleDeleteClick(ActionEvent actionEvent) {
    }
}
