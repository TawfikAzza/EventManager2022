package gui.Controller.ECControllers;

import bll.utils.DisplayError;
import gui.util.BundleHelper;
import gui.util.FontsAwesomeHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.math3.analysis.function.Add;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class RootLayoutEvenController {

    @FXML
    private BorderPane topPane;

    @FXML
    private Label AddEventIcon;

    @FXML
    private Label ManageEventIcon;

    @FXML
    private Label ParticipantIcon;

    @FXML
    private Label SellIcon;

    @FXML
    private Label ScanIcon;

    @FXML
    private Button btnAdd;

    @FXML
    public void initialize()
    {
        AddEventIcon.setText(FontsAwesomeHelper.getFontAwesomeIconForButtons("add").getText());
        ManageEventIcon.setText(FontsAwesomeHelper.getFontAwesomeIconForButtons("manage").getText());
        ParticipantIcon.setText(FontsAwesomeHelper.getFontAwesomeIconForButtons("participants").getText());
        SellIcon.setText(FontsAwesomeHelper.getFontAwesomeIconForButtons("sell").getText());
        ScanIcon.setText(FontsAwesomeHelper.getFontAwesomeIconForButtons("scan").getText());

    }

    @FXML
    void addEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/ECViews/NewEventView.fxml"));
            if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
            else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
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
        if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
        else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
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
            if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
            else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
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
            if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
            else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
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
       /* FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/View/ECViews/ScanTicketView.fxml"));
        GridPane eventOverview = (GridPane) loader.load();

        // Set person overview into the center of root layout.
        topPane.setCenter(eventOverview);*/
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/View/ECViews/ScanTicketView.fxml"));
            if (BundleHelper.hasEnglish) loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("DA")));
            else loader.setResources(ResourceBundle.getBundle("bundle/bundle", new Locale("EN")));
            Parent root = loader.load();
            ScanTicketViewController scanTicketViewController = loader.getController();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            scanTicketViewController.setPrimaryStage(stage);
            stage.show();
        }
        catch (IOException e) {
            DisplayError.displayError(e);
        }
    }

}
