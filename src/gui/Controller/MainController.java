package gui.Controller;

import be.Users;
import bll.exception.AdminDAOException;
import bll.exception.AdminLogicException;
import bll.utils.DisplayError;
import gui.Model.AdminModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Label errorLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    private AdminModel adminModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.adminModel = new AdminModel();
        } catch (AdminDAOException e) {
            DisplayError.displayError(e);
        }
    }

    public void openEventMgr() throws IOException {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/EventView.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/RootLayoutEvent.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root,800,600);
        stage.setScene(scene);

        stage.show();
    }

    public void openAdminMgr() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }


    public void SubmitLogin(ActionEvent actionEvent) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        Users user = null;
        try {
            user = adminModel.getUser(username, password);
            if (user.getRoleID() == 1) {
                openAdminMgr();
            }
            if (user.getRoleID() == 2) {
                openEventMgr();
            }
        } catch (AdminDAOException  e) {
            DisplayError.displayError(e);
        }
        catch (NullPointerException e) {
            errorLabel.setText("Invalid username or password");
        }
    }
}
