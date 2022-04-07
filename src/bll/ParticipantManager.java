package bll;

import be.Events;
import be.Participant;
import be.Users;
import bll.exception.ParticipantManagerException;
import bll.utils.LogCreator;
import bll.utils.LoggedInUser;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.ParticipantDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ParticipantManager {
    ParticipantDAO participantDAO;
    LogCreator log;

    public ParticipantManager() throws ParticipantManagerException {
        try {
            this.participantDAO = new ParticipantDAO();
            this.log = new LogCreator("ParticipantOperations");
        } catch (Exception e) {
            throw new ParticipantManagerException("Error while creating the ParticipantManager",e);
        }
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
            logMessage("deleted", participant);
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
            logMessage("added", participant);
            return participantDAO.addParticipant(participant);
        } catch (Exception e) {
            throw new ParticipantManagerException("Error while creating a participant in the database",e);
        }
    }

    public void updateParticipant(Participant participant) throws ParticipantManagerException {
        try {
            participantDAO.updateParticipant(participant);
            logMessage("edited", participant);
        } catch (Exception e) {
            throw new ParticipantManagerException("Error while updating a participant in the database",e);
        }
    }

    public void deleteParticipantFromEvent(Participant participant, Events event) throws ParticipantManagerException {
        try {
            participantDAO.deleteParticipantFromEvent(participant,event);
            logMessage("removed from event", participant);
        } catch (SQLException e) {
            throw new ParticipantManagerException("Error while deleting a participation from an event",e);
        }
    }

    public void logMessage(String action, Participant participant)
    {
        Users loggedIn = LoggedInUser.getInstance(null);
        log.getLogger().info("Event Coordinator: " + loggedIn.getLoginName() + " with the ID: "+ loggedIn.getUserID() + " " + action + " Participant: " + participant.getEmail() + " with the ID " +participant.getId());
    }
}
