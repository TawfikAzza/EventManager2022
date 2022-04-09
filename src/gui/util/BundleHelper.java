package gui.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class BundleHelper {

    public void loadView (boolean isEnglish, String pathView, Button btnName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(pathView));

        if (isEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
        else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));

        Parent root = loader.load();
        Stage stage = (Stage) btnName.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
