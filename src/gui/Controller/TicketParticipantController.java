package gui.Controller;

import be.Events;
import be.Participant;
import be.Ticket;
import be.TicketType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;

public class TicketParticipantController {

    @FXML
    private ImageView imageQRCode;

    @FXML
    private TextFlow lblDescription;

    @FXML
    private Label lblEventName,lblLocation,lblName,lblTicketType;


    private TicketType ticketType;
    private Participant participant;
    private Events event;


}
