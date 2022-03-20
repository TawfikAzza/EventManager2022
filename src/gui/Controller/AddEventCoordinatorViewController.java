package gui.Controller;

import be.Admin;
import be.Coordinator;
import bll.exception.AdminLogicException;
import bll.utils.SceneSetter;
import gui.Model.AdminModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddEventCoordinatorViewController implements Initializable {
    private AdminModel adminModel;

    @FXML
    private ChoiceBox<String> accountTypeChoiceBox;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button backButton;
    @FXML
    private Button newEventCoordinatorButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.adminModel = new AdminModel();
            this.accountTypeChoiceBox.setItems(adminModel.getAccountTypes());
        } catch (AdminLogicException e) {
            e.printStackTrace();
        }
    }

    public void backClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminView.fxml"));
        SceneSetter.setScene(accountTypeChoiceBox, loader);
    }

    public void addNewEventCoordinatorClick(ActionEvent actionEvent) throws IOException {
        String loginName = loginNameTextField.getText();
        String password = passwordField.getText();
        String email = emailTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String role = accountTypeChoiceBox.getValue();

        if(confirmPassword()) {
            if (role.equals("Admin")) {
                Admin admin = new Admin(loginName, password, 1, email, firstName, lastName);
                adminModel.addLoginUser(admin);
            }
            if(role.equals("Event Coordinator"))
            {
                Coordinator coordinator = new Coordinator(loginName, password, 2, email, firstName, lastName);
                adminModel.addLoginUser(coordinator);
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminView.fxml"));
            SceneSetter.setScene(accountTypeChoiceBox, loader);
        }
        else {
            System.out.println("Passwords do not match");
        }
    }

    private boolean confirmPassword()
    {
        String password = passwordField.getText();
        String confirmation = confirmPasswordField.getText();

        return password.equals(confirmation);
    }
}
