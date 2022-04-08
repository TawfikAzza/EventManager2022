package gui.Controller.ECControllers;

import be.TicketType;
import bll.EventManager;
import bll.exception.EventDAOException;
import bll.exception.EventManagerException;
import bll.utils.DisplayError;
import bll.utils.WebCamService;
import bll.utils.WebCamView;
import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import gui.Model.EventModel;
import gui.util.FontsAwesomeHelper;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class ScanTicketViewController implements Initializable {

    @FXML
    private AnchorPane cameraPane;

    @FXML
    private Button startStop;

    @FXML
    private TextField txtCode;

    @FXML
    private Label lblCode,lblTicketType,lblBenefit,lblStatus;
    @FXML
    private ComboBox<Webcam> comboCam;

    private boolean flagRunningCam=true;
    private WebCamService service ;
    private Webcam cam;
    private EventModel eventModel;
    private Stage primaryStage;



    public ScanTicketViewController() {
        try {
            eventModel = new EventModel();
        } catch (EventDAOException | EventManagerException | Exception e) {
            DisplayError.displayError(e);
        }
        // note this is in init as it **must not** be called on the FX Application Thread:
        ObservableList<Webcam> listCam = FXCollections.observableArrayList();



        cam = Webcam.getWebcams().get(0);

        service = new WebCamService(cam);
        //TODO: CHange this FXML to be accessed on a standalone page (not part of the RootLayout Menu, this way the closing and opening of the camera
        // will be given more control, currently when the camera is on and the user click on another button from the menu, the user exits the scan
        // page without closing the camera, which makes the camera and scanning thread buggy.... one way to resolve this would be to
        // open the scanning page on top of the app page and catch the event closing to close the currently running camera to close it.

        //setComboCam();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        startStop.textProperty().bind(Bindings.
                when(service.runningProperty()).
                then("Stop").
                otherwise("Start"));

        startStop.setOnAction(e -> {
            if(comboCam.getSelectionModel().getSelectedIndex()==-1)
                return;
            if (service.isRunning()) {
                service.cancel();
                flagRunningCam=false;
            } else {
                service.restart();
            }
        });
       // ((Stage)lblBenefit.getScene().getWindow()).setOnCloseRequest(e-> {

        setComboCam();
        WebCamView view = new WebCamView(service);
        cameraPane.getChildren().add(view.getView());
        scanQRCode(txtCode);

    }
    private void setComboCam() {
        for (Webcam cam:Webcam.getWebcams()) {
            comboCam.getItems().add(cam);
        }
    }
    @FXML
    void changeCam() {
        setClosingWindow();
        cam = comboCam.getSelectionModel().getSelectedItem();
        service = new WebCamService(cam);
        WebCamView view = new WebCamView(service);
        cameraPane.getChildren().clear();
        cameraPane.getChildren().add(view.getView());
        System.out.println("Camera : "+cam.getName());
        scanQRCode(txtCode);
    }
    private void setClosingWindow() {
        primaryStage.setOnCloseRequest(e-> {
            System.out.println("Closing");
            if(cam.isOpen())
                cam.close();
            if (service.isRunning()) {
                service.cancel();
            }
        });
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    private void scanQRCode(TextField test) {

        Thread thread = new Thread(){
            boolean scanned=false;
            boolean isValidTicket=true;
            public void run() {
                Result result = null;

                BufferedImage image = null;

                do {
                    try {
                        Thread.sleep(100);
                        if(scanned) {
                            System.out.println("scanned now sleeping");
                            Thread.sleep(3000);
                            scanned=false;
                        }
                    } catch (InterruptedException e) {
                        DisplayError.displayError(e);
                    }

                    if (cam.isOpen() && flagRunningCam) {
                        if ((image = cam.getImage()) == null) {
                            continue;
                        }
                        LuminanceSource source = new BufferedImageLuminanceSource(image);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                        try {
                            result = new MultiFormatReader().decode(bitmap);
                        } catch (NotFoundException e) {
                            // fall thru, it means there is no QR code in image
                        }
                    }

                    if (result != null && flagRunningCam) {
                        scanned=true;
                        try {
                            String ticketNumber = result.getText();
                            isValidTicket = eventModel.validTicketScan(ticketNumber);

                            if(isValidTicket) {
                                scanned=true;
                                System.out.println("scanned valid");
                                Platform.runLater(() -> {
                                    lblStatus.setText(FontsAwesomeHelper.getFontAwesomeIconForButtons("check").getText());
                                    lblCode.setText(ticketNumber);
                                    TicketType ticketType = null;
                                    try {
                                        ticketType = eventModel.getTicketTypeFromTicket(ticketNumber);
                                    } catch (EventManagerException e) {
                                        DisplayError.displayError(e);
                                    }
                                    if(ticketType!=null) {
                                        lblTicketType.setText(ticketType.getType());
                                        lblBenefit.setText(ticketType.getBenefit());
                                    }
                                    lblStatus.setText("Valid");
                                });
                            }
                            if(!isValidTicket) {
                                System.out.println("scanned non valid");
                                scanned=true;
                                Platform.runLater(() -> {
                                    lblStatus.setText(FontsAwesomeHelper.getFontAwesomeIconForButtons("ban").getText());
                                    lblCode.setText(ticketNumber);
                                    TicketType ticketType = null;
                                    try {
                                        ticketType = eventModel.getTicketTypeFromTicket(ticketNumber);
                                    } catch (EventManagerException e) {
                                        DisplayError.displayError(e);
                                    }
                                    if (ticketType != null) {
                                        lblTicketType.setText(ticketType.getType());
                                        lblBenefit.setText(ticketType.getBenefit());
                                    }
                                    lblStatus.setText("Not Valid");
                                });
                            }

                        } catch (EventManagerException e) {
                            DisplayError.displayError(e);
                        }

                        result=null;
                    }

                } while (flagRunningCam);

            }
        };
        thread.setDaemon(true);
        thread.start();
    }
}
