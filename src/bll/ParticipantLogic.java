package bll;

import be.Participant;
import dal.db.ParticipantDAO;

import java.util.ArrayList;

public class ParticipantLogic {
    ParticipantDAO databaseParticipant;

    public ParticipantLogic() throws Exception {
        this.databaseParticipant = new ParticipantDAO();
    }

    public ArrayList<Participant> getAllParticipants() throws Exception {
        return databaseParticipant.getAllParticipants();
    }
}
