package gui.Controller;

import be.Users;
import bll.MainManager;
import bll.utils.SceneSetter;
import gui.util.BundleHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;


public class MainController {
    public PasswordField password;
    public TextField username;
    public Label loginWrongLabel;
    public MainManager mainManager = new MainManager() ;
    public BundleHelper bundle = new BundleHelper();
    Boolean hasEnglish = true;


    @FXML
    private Button btnLanguage;


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

            }
            else if (users.getRoleID()== 2)
            {
                openEventMgr();

            }
        }
        else
        {
            loginWrongLabel.setVisible(true);
        }
    }

    @FXML
    void toDanishEnglish(ActionEvent event) throws IOException {
        hasEnglish = hasEnglish == true ? false : true;
        System.out.println("This: " + hasEnglish);
        bundle.loadView(hasEnglish, "/gui/View/MainWindow.fxml", btnLanguage);
    }



}
