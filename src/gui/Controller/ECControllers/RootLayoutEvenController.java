package gui.Controller.ECControllers;

import bll.utils.DisplayError;
import gui.util.FontsAwesomeHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.apache.commons.math3.analysis.function.Add;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RootLayoutEvenController {

    @FXML
    private BorderPane topPane;

    @FXML
    private Label AddEventIcon;

    @FXML
    private Button btnAdd;

    @FXML
    public void initialize()
    {
        AddEventIcon.setText(FontsAwesomeHelper.getFontAwesomeIconForButtons("add").getText());
    }

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
            e.printStackTrace();
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

    @FXML
    void scanTicket(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/ECViews/ScanTicketView.fxml"));
        GridPane eventOverview = (GridPane) loader.load();

        // Set person overview into the center of root layout.
        topPane.setCenter(eventOverview);
    }
}
