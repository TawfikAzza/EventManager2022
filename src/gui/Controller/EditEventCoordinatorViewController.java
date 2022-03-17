package gui.Controller;

import be.Coordinator;
import bll.exception.AdminLogicException;
import bll.utils.CurrentEventCoordinator;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.adminModel = new AdminModel();
        } catch (AdminLogicException e) {
            e.printStackTrace();
        }
        setFields(CurrentEventCoordinator.getInstance());
    }

    public void backClick(ActionEvent actionEvent) throws IOException {
        switchScene();
    }

    public void editEventCoordinatorClick(ActionEvent actionEvent) throws IOException {
        String loginName = loginNameTextField.getText();
        String password = passwordField.getText();
        String email = emailTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        int ID = CurrentEventCoordinator.getInstance().getUserID();

        Coordinator coordinator = new Coordinator(ID, loginName, password,2, email,firstName, lastName);
        if(confirmPassword()) {
            adminModel.updateEventCoordinator(coordinator);
            CurrentEventCoordinator.setInstance(coordinator);
            switchScene();
        }
        else {
            System.out.println("Passwords do not match");
        }
    }

    public void setFields(Coordinator coordinator)
    {
        String loginName = coordinator.getLoginName();
        String email = coordinator.getMail();
        String firstName = coordinator.getFirstName();
        String lastName = coordinator.getLastName();
        String password = coordinator.getPassword();

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

    private void switchScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminEventCoordinatorView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
