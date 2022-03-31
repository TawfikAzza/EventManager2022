package gui.Model;

import be.Events;
import be.Participant;
import bll.ParticipantManager;
import bll.exception.ParticipantManagerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParticipantModel {

    private ParticipantManager participantManager;
    private ObservableList<Participant> participantObservableList;
    private ObservableList<String> showEventsByPartId;

    public ParticipantModel() throws Exception {
        participantManager = new ParticipantManager();
        participantObservableList = FXCollections.observableArrayList();
    }

    private static ParticipantModel single_instance = null;

    public static ParticipantModel getInstance() throws Exception {
        if (single_instance == null)
            single_instance = new ParticipantModel();

        return single_instance;
    }

    public ObservableList<Participant> getAllParticipants() throws ParticipantManagerException {
        participantObservableList.setAll(participantManager.getAllParticipants());
        return participantObservableList;
    }

    public ObservableList<String> participantsShowEventsById(int idParticipant) throws ParticipantManagerException {
        showEventsByPartId = FXCollections.observableArrayList();
        showEventsByPartId.setAll(participantManager.participantsShowEventsbyId(idParticipant));
        return showEventsByPartId;
    }

    public Participant addParticipant(Participant participant) throws ParticipantManagerException {
        return participantManager.addParticipant(participant);

    }

    public void updateParticipant(Participant participant) throws ParticipantManagerException {
        participantManager.updateParticipant(participant);
    }

    public void deleteParticipantFromEvent(Participant participant, Events event) throws ParticipantManagerException {
        participantManager.deleteParticipantFromEvent(participant, event);
    }

    public void deleteParticipant(Participant selectedParticipant) throws ParticipantManagerException {
        participantManager.deleteParticipant(selectedParticipant);
    }
}
