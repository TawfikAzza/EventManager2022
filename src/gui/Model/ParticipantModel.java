package gui.Model;

import be.Participant;
import bll.ParticipantManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ParticipantModel {

    private ParticipantManager participantLogic;
    private ObservableList<Participant> participantObservableList;
    private ObservableList<String> showEventsByPartId;

    public ParticipantModel() throws Exception {
        participantLogic = new ParticipantManager();
        participantObservableList = FXCollections.observableArrayList();
    }

    private static ParticipantModel single_instance = null;

    public static ParticipantModel getInstance() throws Exception {
        if (single_instance == null)
            single_instance = new ParticipantModel();

        return single_instance;
    }

    public ObservableList<Participant> getAllParticipants() throws Exception {
        participantObservableList.setAll(participantLogic.getAllParticipants());
        return participantObservableList;
    }

    public ObservableList<String> participantsShowEventsbyId (int idParticipant) throws SQLServerException {
        showEventsByPartId = FXCollections.observableArrayList();
        showEventsByPartId.setAll(participantLogic.participantsShowEventsbyId(idParticipant));
        return showEventsByPartId;
    }

}
