package gui.Controller.ECControllers;

import bll.utils.WebCamService;
import bll.utils.WebCamView;
import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    private ComboBox<Webcam> comboCam;

    private WebCamService service ;
    private Webcam cam;


    public ScanTicketViewController() {

        // note this is in init as it **must not** be called on the FX Application Thread:
        ObservableList<Webcam> listCam = FXCollections.observableArrayList();
        cam = Webcam.getWebcams().get(0);

        service = new WebCamService(cam);
        //setComboCam();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startStop.textProperty().bind(Bindings.
                when(service.runningProperty()).
                then("Stop").
                otherwise("Start"));

        startStop.setOnAction(e -> {
            if (service.isRunning()) {
                service.cancel();
            } else {
                service.restart();
            }
        });
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
        cam.close();
        cam = comboCam.getSelectionModel().getSelectedItem();
        service = new WebCamService(cam);
        WebCamView view = new WebCamView(service);
        cameraPane.getChildren().add(view.getView());
        System.out.println("Camera : "+cam.getName());
        scanQRCode(txtCode);
    }
    private void scanQRCode(TextField test) {

        Thread thread = new Thread(){
            public void run() {
                Result result = null;
                BufferedImage image = null;
                do {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }



                    if (cam.isOpen()) {

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

                    if (result != null) {
                        txtCode.setText(result.getText());
                    }

                } while (result==null);
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
}
