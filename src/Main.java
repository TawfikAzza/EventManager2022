import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Font roboto =
            Font.loadFont(getClass()
                .getResourceAsStream("/fonts/Roboto-Regular.ttf"), 12);

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/MainWindow.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/MainWindow.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        Application.launch();
    }
}
