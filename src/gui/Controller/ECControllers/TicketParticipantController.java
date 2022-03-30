package gui.Controller.ECControllers;

import be.Events;
import be.Participant;
import be.Ticket;
import be.TicketType;
import bll.exception.ParticipantManagerException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javax.print.attribute.standard.OrientationRequested;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TicketParticipantController implements Initializable {

    @FXML
    private ImageView imageQRCode;
    @FXML
    private TextFlow lblDescription;
    @FXML
    private Label lblLocation,lblName,lblTicketType, lblStartDate , lblEndDate;

    @FXML
    private TextFlow lblEventName;
    private Participant participant;
    private Events event;
    private Ticket ticketSold;
    private TicketType ticketTypeSold;
    private AnchorPane anchorPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public void setEvent(Events event) {
        this.event = event;
    }
    public void setTicket(Ticket ticketSold) {
        this.ticketSold=ticketSold;
    }

    public void setTicketType(TicketType ticketTypeSold) {
        this.ticketTypeSold=ticketTypeSold;
    }

    public void setValues() {
        //This is where you set the parameters for the look and feel.
        //In particular the textFlow which necessits a setting up of
        //font etc through its Text object.
        try {
            imageQRCode.setImage(generateQRCode(ticketSold.getTicketNumber()));

            Text textDescription = new Text(event.getDescription());
            lblDescription.getChildren().add(textDescription);
            Text text = new Text(event.getName());
            text.setStyle("-fx-font-size: 40px;-fx-font-weight: bold");
            lblEventName.getChildren().add(text);
            lblLocation.setText(event.getLocation());
            lblTicketType.setText(ticketTypeSold.getType());
            lblName.setText(participant.getFname()+" "+participant.getLname());
            lblStartDate.setText(event.getStrStartDate());
            lblEndDate.setText(event.getStrEndDate());
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    public Image generateQRCode(String numTicket) throws WriterException {
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrWriter.encode(numTicket, BarcodeFormat.QR_CODE,300,300);

        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return SwingFXUtils.toFXImage(qrImage,null);
    }

    public void printTicket(MouseEvent event) {

        PrinterJob job = PrinterJob.createPrinterJob();
        if(job != null){
            Stage stagePrint = (Stage) lblTicketType.getScene().getWindow();
            PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, 0, 0, 0, 0);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Make a choice");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Print");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Save");

            alert.setTitle("Choose wisely...");
            alert.setHeaderText("Print/Save Ticket");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                job.showPrintDialog(stagePrint);
            } else {
                job.printPage(pageLayout,anchorPane);
            }
            job.endJob();
        }
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane=anchorPane;
    }
}
