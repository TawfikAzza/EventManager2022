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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void updateEventTicket(Events event) throws Exception {
        HashMap<Integer,Integer> mapTicket = new HashMap<>();
        HashMap<Integer,Ticket> ticketListReceived = new HashMap<>();
        HashMap<Integer,Ticket> ticketListDB = new HashMap<>();
        for (Ticket ticket:event.getTicketAvailable()) {
            ticketListReceived.put(ticket.getId(),ticket);
            mapTicket.put(ticket.getId(),0);
        }
        for (Map.Entry entry:mapTicket.entrySet()) {
            int index = (Integer)entry.getKey();
            System.out.println("ID : "+index);
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
                ticketListDB.put(ticketID,new Ticket(ticketID,typeName,benefit));
                if(mapTicket.get(ticketID)!=null)
                    mapTicket.put(ticketID,mapTicket.get(ticketID)+1);
            }
            for(Map.Entry entry:ticketListDB.entrySet()) {
                int index = (Integer)entry.getKey();
                Ticket ticket = (Ticket) entry.getValue();
                if(ticketListReceived.get(index)==null) {
                    pstmtDelete.setInt(1,ticket.getId());
                    pstmtDelete.execute();
                }
            }
            for (Ticket ticket: event.getTicketAvailable()) {
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
}
