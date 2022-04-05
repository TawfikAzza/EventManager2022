package gui.Controller;

import be.Users;
import bll.MainManager;
import bll.utils.DisplayError;
import bll.utils.LoggedInUser;
import bll.utils.SceneSetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


public class MainController {
    public PasswordField password;
    public TextField username;
    public Label loginWrongLabel;
    public MainManager mainManager = new MainManager() ;
    private Logger logger;
    private FileHandler fileHandler;

    public MainController()
    {
        this.logger = Logger.getLogger("LoginInfo");
        try {
            this.fileHandler = new FileHandler("resources/Log/login.log", true);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openEventMgr() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/ECViews/RootLayoutEvent.fxml"));
        SceneSetter.setScene(password ,loader);
    }

    public void openAdminMgr() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminViews/AdminView.fxml"));
        SceneSetter.setScene(password ,loader);
    }

    public void submitLogin(ActionEvent actionEvent) throws Exception{
        Users users= mainManager.submitLogin(username.getText(), password.getText());
        if(users != null)
        {
            if (users.getRoleID() == 1) {
                openAdminMgr();
                logger.info("Admin: " + username.getText() + " logged in");

            }
            else if (users.getRoleID()== 2)
            {
                openEventMgr();
                logger.info("Event Coordinator: " + username.getText() + " logged in");

            }
        }
        else
        {
            loginWrongLabel.setVisible(true);
        }
    }
}
