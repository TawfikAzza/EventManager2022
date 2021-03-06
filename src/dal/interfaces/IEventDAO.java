package dal.interfaces;

import be.Events;
import be.Participant;
import be.Ticket;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.List;

public interface IEventDAO {
    Events addEvent(Events event) throws Exception;

    void removeEvent(Events event) throws Exception;

    void updateEvent(Events event) throws Exception;

    List<Events> getAllEvents() throws Exception;

    Events getEvent(int id) throws Exception;

    List<Events> getParticipantEvent(Participant participant) throws SQLServerException;

    List<Events> getAllEventsWithTicketType() throws Exception;

    void addParticipantToEvent(Events eventChosen, Participant participant, Ticket ticketSold) throws SQLServerException, SQLException;
}
