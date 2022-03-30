package gui.Controller;

import bll.utils.DisplayError;
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
    void addEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/ECViews/NewEventView.fxml"));
            GridPane personOverview = (GridPane) loader.load();

            // Set person overview into the center of root layout.
            topPane.setCenter(personOverview);

            // Give the controller access to the main app.
            NewEventController controller = loader.getController();
            controller.setMainApp(this);
        }
        catch (IOException e) {
            DisplayError.displayError(e);}

    }

    @FXML
    void manageEvent(ActionEvent event){
        try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/ECViews/EventView.fxml"));
        GridPane eventOverview = (GridPane) loader.load();

        // Set person overview into the center of root layout.
        topPane.setCenter(eventOverview);

        // Give the controller access to the main app.
        EventsController controller = loader.getController();
        controller.setMainApp(this);
        }
        catch (IOException e) {
            DisplayError.displayError(e);}
    }
    public void setCenter(GridPane gridPane) {
        topPane.setCenter(gridPane);
    }
    @FXML
    void manageParticipants(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/ECViews/ParticipantView.fxml"));
            GridPane eventOverview = (GridPane) loader.load();

            // Set person overview into the center of root layout.
            topPane.setCenter(eventOverview);

            // Give the controller access to the main app.
            ParticipantViewController controller = loader.getController();
            controller.setMainApp(this);
        }
        catch (IOException e) {DisplayError.displayError(e);}
    }

    public void sellTicket(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/ECViews/SellTicketView.fxml"));
            GridPane eventOverview = (GridPane) loader.load();

            // Set person overview into the center of root layout.
            topPane.setCenter(eventOverview);

            // Give the controller access to the main app.
            SellTicketViewController controller = loader.getController();
            controller.setMainApp(this);
        }
        catch (IOException e) {DisplayError.displayError(e);}
    }
}
