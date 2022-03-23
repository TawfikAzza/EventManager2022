package gui.Controller;

import be.Admin;
import be.Coordinator;
import be.Users;
import bll.exception.AdminDAOException;
import bll.exception.AdminLogicException;
import bll.utils.CurrentAdmin;
import bll.utils.CurrentEventCoordinator;
import bll.utils.DisplayError;
import bll.utils.SceneSetter;
import gui.Model.AdminModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditAdminViewController implements Initializable {
    private AdminModel adminModel;

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
        } catch (AdminDAOException e) {
            DisplayError.displayError(e);
        }
        setFields(CurrentAdmin.getInstance());
    }

    public void backClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminEditDeleteView.fxml"));
        SceneSetter.setScene(firstNameTextField, loader);
    }

    public void editAdmin(ActionEvent actionEvent) {
        String loginName = loginNameTextField.getText();
        String password = passwordField.getText();
        String email = emailTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        int ID = CurrentAdmin.getInstance().getUserID();

        Admin admin = new Admin(ID, loginName, password,2, email,firstName, lastName);
        if(confirmPassword()) {
            try {
                adminModel.editUser(admin);
                CurrentAdmin.setInstance(admin);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminEditDeleteView.fxml"));
                SceneSetter.setScene(firstNameTextField, loader);
            } catch (AdminDAOException | IOException e) {
                DisplayError.displayError(e);
            }
        }
        else {
            System.out.println("Passwords do not match");
        }
    }

    public void setFields(Users user)
    {
        String loginName = user.getLoginName();
        String email = user.getMail();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String password = user.getPassword();

        loginNameTextField.setText(loginName);
        emailTextField.setText(email);
        firstNameTextField.setText(firstName);
        lastNameTextField.setText(lastName);
        passwordField.setText(password);
        confirmPasswordField.setText(password);

    }

    private boolean confirmPassword()
    {
        String password = passwordField.getText();
        String confirmation = confirmPasswordField.getText();

        return password.equals(confirmation);
    }
}
