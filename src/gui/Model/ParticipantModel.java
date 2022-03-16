package gui.Model;

import be.Participant;
import bll.ParticipantLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParticipantModel {

    private ParticipantLogic participantLogic;
    private ObservableList<Participant> participantObservableList;

    public ParticipantModel() throws Exception {
        participantLogic = new ParticipantLogic();
        participantObservableList = FXCollections.observableArrayList();
    }

    private static ParticipantModel single_instance = null;

    public static ParticipantModel getInstance() throws Exception {
        if (single_instance == null)
            single_instance = new ParticipantModel();

        return single_instance;
    }

    public ObservableList<Participant> getAllParticipants() throws Exception {
        participantObservableList.clear();
        participantObservableList.addAll(participantLogic.getAllParticipants());
        return participantObservableList;
    }

}
