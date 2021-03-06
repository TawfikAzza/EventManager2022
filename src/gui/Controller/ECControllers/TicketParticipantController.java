package gui.Controller.ECControllers;

import be.Events;
import be.Participant;
import be.Ticket;
import be.TicketType;
import bll.utils.DisplayError;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void printTicket() {

        PrinterJob job = PrinterJob.createPrinterJob();
        if(job != null){
           // Stage stagePrint = (Stage) lblTicketType.getScene().getWindow();
            //PageLayout pageLayout = job.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, 0, 0, 0, 0);


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Make a choice");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Print");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Save");

            alert.setTitle("Choose wisely...");
            alert.setHeaderText("Print/Save Ticket");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                captureAndSaveDisplay();
                openOutlook();
                //job.showPrintDialog(stagePrint);
            } else {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save PNG File");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG File", "*.png"));
                File selectedFile = fileChooser.showSaveDialog(lblTicketType.getScene().getWindow());
                if (selectedFile != null) {
                    WritableImage writableImage = new WritableImage((int)anchorPane.getWidth() + 20,
                            (int)anchorPane.getHeight() + 20);
                    anchorPane.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    //Write the snapshot to the chosen file
                    try {
                        ImageIO.write(renderedImage, "png", selectedFile);
                    } catch (IOException e) {
                        DisplayError.displayError(e);
                    }

                    //document.add()
                    //writer.writeString();
                    //DOCUMENT WRITING CODE BEGINS
                }
                //captureAndSaveDisplay();
               // job.printPage(pageLayout,anchorPane);
            }
            job.endJob();
        }
    }
    public void captureAndSaveDisplay(){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        //Prompt user to select a file
        //File file = fileChooser.showSaveDialog(null);
        String fileName = "resources/TempTickets/"+ticketSold.getTicketNumber()+".png";
        File file = new File(fileName);
        if(file != null){
            try {
                //Pad the capture area
                WritableImage writableImage = new WritableImage((int)anchorPane.getWidth() + 20,
                        (int)anchorPane.getHeight() + 20);
                anchorPane.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);

            } catch (IOException ex) { ex.printStackTrace(); }
        }
    }
    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane=anchorPane;
    }

    public String getOutlook() {
        try {
            Process p = Runtime.getRuntime()
                    .exec(new String[]{"cmd.exe", "/c", "assoc", ".pst"});
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String extensionType = input.readLine();
            input.close();
            // extract type
            if (extensionType == null) {
                outlookNotFoundMessage("File type PST not associated with Outlook.");
            } else {
                String[] fileType = extensionType.split("=");

                p = Runtime.getRuntime().exec(
                        new String[]{"cmd.exe", "/c", "ftype", fileType[1]});
                input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String fileAssociation = input.readLine();
                // extract path
                Pattern pattern = Pattern.compile("\".*?\"");
                Matcher m = pattern.matcher(fileAssociation);
                if (m.find()) {
                    String outlookPath = m.group(0);
                    System.out.println(outlookPath);
                    return outlookPath;
                } else {
                    outlookNotFoundMessage("Error parsing PST file association");
                }
            }

        } catch (Exception err) {
            err.printStackTrace();
            outlookNotFoundMessage(err.getMessage());
        }
        return null;
    }

    private static void outlookNotFoundMessage(String errorMessage) {
        System.out.println("Could not find Outlook: \n" + errorMessage);
    }

    public void openOutlook()
    {
        String outlook = getOutlook();
        Runtime rt = Runtime.getRuntime();
        System.out.println(outlook);
        try {
            String attachment = "resources/TempTickets/"+ticketSold.getTicketNumber()+".png";
            String subject = "Ticket%20Email"; //%20 is used in place of a space
            String email = "cchesberg@gmail.com"; //participant.getEmail();
            String emailSubjectCombined = email+"?subject="+subject;
            File file = new File(attachment);
            rt.exec(new String[]{"cmd.exe","/c", outlook, "/m", emailSubjectCombined, "/a", file.getAbsolutePath()});
        } catch (IOException e) {
            // TODO Auto-generated catch block
            DisplayError.displayError(e);
        }
    }
}



