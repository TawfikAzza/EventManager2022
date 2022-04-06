package gui.Controller.AdminControllers;

import be.Admin;
import be.Users;
import bll.exception.AdminDAOException;
import bll.utils.DisplayError;
import bll.utils.LoggedInUser;
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
import java.util.logging.FileHandler;
import java.util.logging.Logger;

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

    private Admin admin;
    private Logger logger;
    private FileHandler fileHandler;


    public EditAdminViewController(Admin admin)
    {
        this.admin = admin;
        this.logger = Logger.getLogger("AdminOperations");
        try {
            this.fileHandler = new FileHandler("resources/Log/adminOperations.log", true);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.adminModel = new AdminModel();
        } catch (AdminDAOException e) {
            DisplayError.displayError(e);
        }
        setFields(admin);
    }

    public void backClick(ActionEvent actionEvent) {
        AdminEditDeleteViewController controller = new AdminEditDeleteViewController(admin);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminEditDeleteView.fxml"));
        loader.setController(controller);
        SceneSetter.setScene(firstNameTextField, loader);
    }

    public void editAdmin(ActionEvent actionEvent) {
        String loginName = loginNameTextField.getText();
        String password = passwordField.getText();
        String email = emailTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        int ID = admin.getUserID();

        Admin admin = new Admin(ID, loginName, password,2, email,firstName, lastName);
        if(confirmPassword()) {
            try {
                adminModel.editUser(admin);
                logger.info("Admin: " + LoggedInUser.getInstance(null).getUserID() + " edited Admin with the ID: " + admin.getUserID());
                AdminEditDeleteViewController controller = new AdminEditDeleteViewController(admin);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminEditDeleteView.fxml"));
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
