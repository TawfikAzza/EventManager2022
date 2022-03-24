package gui.Controller;

import be.Users;
import bll.MainManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;


public class MainController {
    public PasswordField password;
    public TextField username;
    public Label loginWrongLabel;
    public MainManager mainManager = new MainManager() ;
    public void openEventMgr() throws IOException {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/EventView.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/RootLayoutEvent.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
        closeWindow();
    }

    public void openAdminMgr() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/AdminView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        closeWindow();

    }
    public void closeWindow() throws IOException {
        Stage window = (Stage) this.password.getScene().getWindow();
        window.close();
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
}
