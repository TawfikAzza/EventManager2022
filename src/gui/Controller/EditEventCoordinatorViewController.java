package gui.Controller;

import be.Coordinator;
import be.Users;
import bll.exception.AdminDAOException;
import bll.utils.DisplayError;
import bll.utils.SceneSetter;
import gui.Model.AdminModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    @FXML
    private Button backButton;
    @FXML
    private Button newEventCoordinatorButton;

    Coordinator eventCoordinator;

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

    public void backClick(ActionEvent actionEvent) throws IOException {
        AdminEventCoordinatorViewController controller = new AdminEventCoordinatorViewController(eventCoordinator);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminEventCoordinatorView.fxml"));
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminEventCoordinatorView.fxml"));
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
