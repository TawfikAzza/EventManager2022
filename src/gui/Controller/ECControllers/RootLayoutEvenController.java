package gui.Controller.ECControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class RootLayoutEvenController {
    @FXML
    private BorderPane topPane;

    @FXML
    private Button btnAdd;

    @FXML
    void addEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/ECViews/NewEventView.fxml"));
        GridPane personOverview = (GridPane) loader.load();

        // Set person overview into the center of root layout.
        topPane.setCenter(personOverview);

        // Give the controller access to the main app.
        NewEventController controller = loader.getController();
        controller.setMainApp(this);
    }

    @FXML
    void manageEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/ECViews/EventView.fxml"));
        GridPane eventOverview = (GridPane) loader.load();

        // Set person overview into the center of root layout.
        topPane.setCenter(eventOverview);

        // Give the controller access to the main app.
        EventsController controller = loader.getController();
        controller.setMainApp(this);
    }
    public void setCenter(GridPane gridPane) {
        topPane.setCenter(gridPane);
    }
    @FXML
    void manageParticipants(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/ECViews/ParticipantView.fxml"));
        GridPane eventOverview = (GridPane) loader.load();

        // Set person overview into the center of root layout.
        topPane.setCenter(eventOverview);

        // Give the controller access to the main app.
        ParticipantViewController controller = loader.getController();
        controller.setMainApp(this);
    }

    public void sellTicket(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/ECViews/SellTicketView.fxml"));
        GridPane eventOverview = (GridPane) loader.load();

        // Set person overview into the center of root layout.
        topPane.setCenter(eventOverview);

        // Give the controller access to the main app.
        SellTicketViewController controller = loader.getController();
        controller.setMainApp(this);
    }

    @FXML
    void scanTicket(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/ECViews/ScanTicketView.fxml"));
        GridPane eventOverview = (GridPane) loader.load();

        // Set person overview into the center of root layout.
        topPane.setCenter(eventOverview);
    }
}
