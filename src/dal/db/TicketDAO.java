package dal.db;

import be.Events;
import be.Participant;
import be.Ticket;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    ConnectionManager cm;
    public TicketDAO() throws Exception {
        cm = new ConnectionManager();
    }
    public void addEventTicket(Events event) throws Exception {
        try (Connection con = cm.getConnection()) {
            String sqlInsert = "INSERT INTO TicketType VALUES (?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sqlInsert);
            for (Ticket ticket:event.getTicketAvailable()) {
                pstmt.setString(1,ticket.getType());
                pstmt.setString(2,ticket.getBenefit());
                pstmt.setInt(3,event.getId());
                pstmt.addBatch();
            }
           pstmt.executeBatch();
        }
    }
    public ArrayList<Participant> getAllParticipants() throws Exception
    {
        ArrayList<Participant> allParticipants = new ArrayList<>();

        try (Connection con = cm.getConnection()) {
            String sql ="SELECT * FROM Participant";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                Participant participant = new Participant(rs.getInt("id"),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"));
                allParticipants.add(participant);
            }
        }

        return allParticipants;
    }
}
