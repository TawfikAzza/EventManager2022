package gui.Controller.AdminControllers;

import be.Coordinator;
import be.Users;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class EditEventCoordinatorViewController implements Initializable {
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

    private Coordinator eventCoordinator;

    public EditEventCoordinatorViewController(Coordinator eventCoordinator)
    {
        this.eventCoordinator = eventCoordinator;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.adminModel = new AdminModel();
        } catch ( AdminDAOException e) {
            DisplayError.displayError(e);
        }
        setFields(eventCoordinator);
    }

    public void backClick(ActionEvent actionEvent) {
        AdminEventCoordinatorViewController controller = new AdminEventCoordinatorViewController(eventCoordinator);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminEventCoordinatorView.fxml"));
        if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
        else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
        loader.setController(controller);
        SceneSetter.setScene(firstNameTextField, loader);
    }

    public void editEventCoordinatorClick(ActionEvent actionEvent) {
        String loginName = loginNameTextField.getText();
        String password = passwordField.getText();
        String email = emailTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        int ID = eventCoordinator.getUserID();

        Coordinator coordinator = new Coordinator(ID, loginName, password,2, email,firstName, lastName);
        if(confirmPassword()) {
            try {
                adminModel.editUser(coordinator);
                AdminEventCoordinatorViewController controller = new AdminEventCoordinatorViewController(eventCoordinator);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminEventCoordinatorView.fxml"));
                if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
                else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
                loader.setController(controller);
                SceneSetter.setScene(firstNameTextField, loader);
            } catch (AdminDAOException e) {
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
