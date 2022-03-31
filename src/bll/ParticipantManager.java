package bll;

import be.Events;
import be.Participant;
import bll.exception.ParticipantManagerException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.ParticipantDAO;

import java.sql.SQLException;
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

    public void deleteParticipant(Participant participant) throws ParticipantManagerException {
        try {
            participantDAO.deleteParticipant(participant);
        } catch (SQLException e) {
            throw new ParticipantManagerException("Error while removing participant",e);
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

    public void updateParticipant(Participant participant) throws ParticipantManagerException {
        try {
            participantDAO.updateParticipant(participant);
        } catch (Exception e) {
            throw new ParticipantManagerException("Error while updating a participant in the database",e);
        }
    }

    public void deleteParticipantFromEvent(Participant participant, Events event) throws ParticipantManagerException {
        try {
            participantDAO.deleteParticipantFromEvent(participant,event);
        } catch (SQLException e) {
            throw new ParticipantManagerException("Error while deleting a participation from an event",e);
        }
    }
}
