package bll;

import be.Participant;
import bll.exception.ParticipantManagerException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.ParticipantDAO;

import java.util.ArrayList;

public class ParticipantManager {
    ParticipantDAO participantDAO;

    public ParticipantManager() throws Exception {
        this.participantDAO = new ParticipantDAO();
    }

    public ArrayList<Participant> getAllParticipants() throws ParticipantManagerException {
        try {
            return participantDAO.getAllParticipants();
        } catch (Exception e) {
            throw new ParticipantManagerException("Error while getting the list of participants",e);
        }
    }
    public ArrayList<String> participantsShowEventsbyId (int idParticipant) throws ParticipantManagerException {
        try {
            return participantDAO.participantsShowEventsbyId(idParticipant);
        } catch (SQLServerException e) {
            throw new ParticipantManagerException("Error while getting the events of the participant",e);
        }
    }

    public Participant addParticipant(Participant participant) throws ParticipantManagerException {
        try {
            return participantDAO.addParticipant(participant);
        } catch (Exception e) {
            throw new ParticipantManagerException("Error while creating a participant in the database",e);
        }
    }
}
