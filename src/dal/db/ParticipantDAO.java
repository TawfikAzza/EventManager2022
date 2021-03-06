package dal.db;
import be.Events;
import be.Participant;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;

public class ParticipantDAO {
    ConnectionManager cm;
    public ParticipantDAO() throws Exception {
        cm = new ConnectionManager();
    }
    public ArrayList<Participant> getAllParticipants() throws Exception
    {
        ArrayList<Participant> allParticipants = new ArrayList<>();

        try (Connection con = cm.getConnection()) {
            String sql ="SELECT * FROM Participant WHERE fname != ? AND lname != ? AND phoneNumber != ? AND email != ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "ANONYMOUS");
            pstmt.setString(2, "ANONYMOUS");
            pstmt.setString(3, "00000000");
            pstmt.setString(4, "ANONYMOUS");
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Participant participant = new Participant(rs.getInt("id"),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"));
                allParticipants.add(participant);
            }
        }

        return allParticipants;
    }
    public Participant addParticipant(Participant participant) throws Exception {
        Participant participantCreated=null;
        try (Connection con = cm.getConnection()) {
            //TODO: Before inserting a Participant in the db, there is a need to check if the participant doesn't
            // already exist in it. Issue is, which criteria should we take into account? mail, phone number?

            String sql = "INSERT INTO Participant VALUES(?,?,?,?)";

            PreparedStatement pstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, participant.getFname());
            pstmt.setString(2, participant.getLname());
            pstmt.setString(3, participant.getPhoneNumber());
            pstmt.setString(4, participant.getEmail());
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                participantCreated = new Participant(rs.getInt(1),
                        participant.getFname(),
                        participant.getLname(),
                        participant.getPhoneNumber(),
                        participant.getEmail());
            }
        }
        return participantCreated;
    }

    public void updateParticipant(Participant participant) throws Exception {
        try (Connection con = cm.getConnection()) {
            String sql = "UPDATE Participant SET fname = ?, lname = ?, phoneNumber = ?, email = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, participant.getFname());
            pstmt.setString(2, participant.getLname());
            pstmt.setString(3, participant.getPhoneNumber());
            pstmt.setString(4, participant.getEmail());
            pstmt.setInt(5, participant.getId());


            pstmt.execute();
        }
    }
    public void deleteParticipant(Participant participant) throws  SQLException {
        try (Connection con = cm.getConnection()) {
            String sql = "UPDATE Participant SET fname = ?, lname = ?, phoneNumber = ?, email = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, "ANONYMOUS");
            pstmt.setString(2, "ANONYMOUS");
            pstmt.setString(3, "00000000");
            pstmt.setString(4, "ANONYMOUS");
            pstmt.setInt(5, participant.getId());

            pstmt.execute();

        }
    }
    public void deleteParticipantFromEvent(Participant participant, Events event) throws SQLException {
        try (Connection con = cm.getConnection()) {
            String sqlDeleteFromTicket = "DELETE FROM TICKET WHERE id = " +
                    "(SELECT ticketID FROM EventParticipant WHERE idParticipant=? AND idEvent = ? AND ticketID = ?)";

            String sqlDeleteFromEventParticipant = "DELETE FROM EventParticipant WHERE idParticipant=? AND idEvent=? AND ticketID = ?";
            PreparedStatement pstmtDeleteTicket = con.prepareStatement(sqlDeleteFromTicket);
            pstmtDeleteTicket.setInt(1,participant.getId());
            pstmtDeleteTicket.setInt(2,event.getId());
            pstmtDeleteTicket.setInt(3,participant.getTicketID());
            PreparedStatement pstmtDeleteFromEventParticipant = con.prepareStatement(sqlDeleteFromEventParticipant);
            pstmtDeleteFromEventParticipant.setInt(1,participant.getId());
            pstmtDeleteFromEventParticipant.setInt(2,event.getId());
            pstmtDeleteFromEventParticipant.setInt(3,participant.getTicketID());
            pstmtDeleteTicket.execute();
            pstmtDeleteFromEventParticipant.execute();
        }

    }
    public ArrayList<String> participantsShowEventsbyId (int idParticipant) throws SQLServerException {
        ArrayList<String> eventsOfOneParticipant = new ArrayList<>();

        try (Connection con = cm.getConnection()) {
            String sql ="SELECT e.name FROM Events e " +
                    "INNER JOIN EventParticipant ep ON  ep.idEvent = e.id " +
                    "INNER JOIN Participant p ON p.id = ep.idParticipant " +
                    "WHERE p.id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, idParticipant);
            ResultSet rs = pstmt.executeQuery();


            while(rs.next())
            {
                String nameOfEvent = rs.getString("name");
                eventsOfOneParticipant.add(nameOfEvent);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventsOfOneParticipant;
    } // this method returns all the events which the particular participant has the ticket (parameter is participant??s id)




}
