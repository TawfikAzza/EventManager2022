import be.Coordinator;
import gui.Model.AdminModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AdminModel adminModel = new AdminModel();
        Coordinator coordinator = (new Coordinator("NotBob", "password", 2, "bob@gmail.com"));
        coordinator.setUserID(2);
        //adminModel.addEventCoordinator(new Coordinator("bob", "password", 2, "bob@gmail.com"));
        //adminModel.deleteEventCoordinator(coordinator);
        adminModel.updateEventCoordinator(coordinator);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/View/MainWindow.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch();
    }
}
