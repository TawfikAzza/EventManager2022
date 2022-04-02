package dal.db;

import be.Events;
import be.Ticket;
import be.TicketType;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;
import org.apache.poi.ss.formula.functions.T;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class TicketDAO {
    ConnectionManager cm;
    public TicketDAO() throws Exception {
        cm = new ConnectionManager();
    }
    public Ticket getTicket(int idTicket) throws SQLException {
        Ticket ticketSearched = null;
        try (Connection con = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM Ticket WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sqlSelect);
            pstmt.setInt(1,idTicket);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                ticketSearched = new Ticket(rs.getInt("id"),
                                            rs.getString("ticketNumber"),
                                            rs.getInt("ticketTypeID"),
                                            rs.getBoolean("valid"));
            }
        }
        return ticketSearched;
    }

    public TicketType getTicketType(int idTicketType) throws SQLException {
        TicketType ticketType = null;
        try (Connection con = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM TicketType WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sqlSelect);
            pstmt.setInt(1,idTicketType);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                ticketType = new TicketType(rs.getInt("id"),
                                            rs.getString("typeName"),
                                            rs.getString("benefit"));
            }
        }
        return ticketType;
    }
    public void addEventTicket(Events event) throws Exception {

        try (Connection con = cm.getConnection()) {
            String sqlInsert = "INSERT INTO TicketType VALUES (?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sqlInsert);
            for (TicketType ticket:event.getTicketAvailable()) {
                pstmt.setString(1,ticket.getType());
                pstmt.setString(2,ticket.getBenefit());
                pstmt.setInt(3,event.getId());
                pstmt.addBatch();
            }
           pstmt.executeBatch();
        }
    }
    public void updateEventTicketType(Events event) throws Exception {
        HashMap<Integer,Integer> mapTicket = new HashMap<>();
        HashMap<Integer, TicketType> ticketListReceived = new HashMap<>();
        HashMap<Integer, TicketType> ticketListDB = new HashMap<>();
        for (TicketType ticket:event.getTicketAvailable()) {
            ticketListReceived.put(ticket.getId(),ticket);
            mapTicket.put(ticket.getId(),0);
        }


        try (Connection con = cm.getConnection()) {
            String sqlSelect = "SELECT * FROM TicketType WHERE eventID=?";
            String sqlUpdate = "UPDATE TicketType set typeName=? , benefit = ? WHERE id = ?";
            String sqlInsert = "INSERT INTO TicketType VALUES (?,?,?)";
            String sqlDelete = "DELETE FROM TicketType WHERE id =? ";


            PreparedStatement pstmt = con.prepareStatement(sqlSelect);
            PreparedStatement pstmtUpdate = con.prepareStatement(sqlUpdate);
            PreparedStatement pstmtInsert = con.prepareStatement(sqlInsert);
            PreparedStatement pstmtDelete = con.prepareStatement(sqlDelete);


            pstmt.setInt(1,event.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int ticketID = rs.getInt("id");
                String typeName = rs.getString("typeName");
                String benefit = rs.getString("benefit");
                ticketListDB.put(ticketID,new TicketType(ticketID,typeName,benefit));
                if(mapTicket.get(ticketID)!=null)
                    mapTicket.put(ticketID,mapTicket.get(ticketID)+1);
            }
            for(Map.Entry entry:ticketListDB.entrySet()) {
                int index = (Integer)entry.getKey();
                TicketType ticket = (TicketType) entry.getValue();
                if(ticketListReceived.get(index)==null) {
                    pstmtDelete.setInt(1,ticket.getId());
                    pstmtDelete.execute();
                }
            }
            for (TicketType ticket: event.getTicketAvailable()) {
                if(mapTicket.get(ticket.getId())!=0) {
                    pstmtUpdate.setString(1,ticket.getType());
                    pstmtUpdate.setString(2,ticket.getBenefit());
                    pstmtUpdate.setInt(3,ticket.getId());
                    pstmtUpdate.execute();
                }
                if(mapTicket.get(ticket.getId())==0) {
                    pstmtInsert.setString(1,ticket.getType());
                    pstmtInsert.setString(2,ticket.getBenefit());
                    pstmtInsert.setInt(3,event.getId());
                    pstmtInsert.execute();
                }
            }

        }

    }

    public Ticket addTicketSold(Ticket ticket) throws SQLException {
        Ticket ticketCreated = null;
        try (Connection con = cm.getConnection()) {

            String sqlInsert = "INSERT INTO Ticket VALUES (?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,ticket.getTicketNumber());
            pstmt.setInt(2,ticket.getTicketTypeID());
            pstmt.setBoolean(3,true);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                ticketCreated = new Ticket(rs.getInt(1), ticket.getTicketNumber(),ticket.getTicketTypeID());
            }
        }
        System.out.println("In TicketDAO: addTicketSold");
        return ticketCreated;
    }
    public TicketType getTicketTypeFromTicket(String ticketNumber) throws SQLException {
        TicketType ticketTypeSearched = null;
        try (Connection con = cm.getConnection()) {

            String sql = "SELECT TicketType.id as id, TicketType.typeName as typeName, TicketType.benefit as benefit " +
                         " FROM TicketType INNER JOIN Ticket ON TicketType.id = Ticket.ticketTypeID " +
                         " WHERE Ticket.ticketNumber = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,ticketNumber);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                ticketTypeSearched = new TicketType(rs.getInt("id"),
                                                    rs.getString("typeName"),
                                                    rs.getString("benefit"));
            }
        }
        return ticketTypeSearched;
    }
    public boolean validTicketScan(String ticketNumber) throws SQLException {
        boolean isTicketValid = false;
        try (Connection con = cm.getConnection()) {
            isTicketValid = false;
            String sqlCheck = "SELECT valid FROM Ticket WHERE ticketNumber = ?";
            String sqlUpdate = "UPDATE Ticket set valid = 0 WHERE ticketNumber = ?";
            PreparedStatement pstmtCheck = con.prepareStatement(sqlCheck);
            PreparedStatement pstmtUpdate = con.prepareStatement(sqlUpdate);
            pstmtCheck.setString(1,ticketNumber);
            pstmtUpdate.setString(1,ticketNumber);
            ResultSet rsCheck = pstmtCheck.executeQuery();
            while(rsCheck.next()) {
                isTicketValid = rsCheck.getBoolean("valid");
            }
            if(isTicketValid) {
                pstmtUpdate.execute();
            }
        }
        return isTicketValid;
    }
}
