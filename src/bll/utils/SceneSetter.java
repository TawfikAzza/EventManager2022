package bll.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSetter {

    public static void setScene(Node node, FXMLLoader loader) throws IOException {

        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
