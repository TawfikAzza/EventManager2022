package gui.Controller.AdminControllers;

import be.Admin;
import be.Coordinator;
import bll.exception.AdminDAOException;
import bll.utils.DisplayError;
import bll.utils.LoggedInUser;
import bll.utils.SceneSetter;
import gui.Model.AdminModel;
import gui.util.BundleHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.adminModel = new AdminModel();
            this.accountTypeChoiceBox.setItems(adminModel.getAccountTypes());
        } catch (AdminDAOException e) {
            DisplayError.displayError(e);
        }
    }

    public AddEventCoordinatorViewController()
    {
    }

    public void backClick(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminView.fxml"));
        if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
        else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
        SceneSetter.setScene(accountTypeChoiceBox, loader);
    }

    public void addNewEventCoordinatorClick(ActionEvent actionEvent) {
        try {
            String loginName = loginNameTextField.getText();
            String password = passwordField.getText();
            String email = emailTextField.getText();
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String role = accountTypeChoiceBox.getValue();

            if (confirmPassword()) {
                if (role.equals("Admin")) {
                    Admin admin = new Admin(loginName, password, 1, email, firstName, lastName);
                    adminModel.addLoginUser(admin);
                }
                if (role.equals("Event Coordinator")) {
                    Coordinator coordinator = new Coordinator(loginName, password, 2, email, firstName, lastName);
                    adminModel.addLoginUser(coordinator);
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminView.fxml"));
                if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
                else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
                SceneSetter.setScene(accountTypeChoiceBox, loader);
            } else {
                System.out.println("Passwords do not match");
            }
        } catch (AdminDAOException e) {
            DisplayError.displayError(e);
        }

    }

    private boolean confirmPassword()
    {
        String password = passwordField.getText();
        String confirmation = confirmPasswordField.getText();

        return password.equals(confirmation);
    }
}
