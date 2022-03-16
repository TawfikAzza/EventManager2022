package bll;

import be.Participant;
import bll.exception.EventDAOException;
import bll.exception.EventManagerDAOException;
import dal.db.PartipantDAO;

import java.util.ArrayList;

public class ParticipantLogic {
    PartipantDAO databaseParticipant;

    public ParticipantLogic() throws Exception {
        this.databaseParticipant = new PartipantDAO();
    }

    public ArrayList<Participant> getAllParticipants() throws Exception {
        return databaseParticipant.getAllParticipants();
    }
}
