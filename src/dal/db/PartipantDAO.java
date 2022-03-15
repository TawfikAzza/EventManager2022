package dal.db;

import be.Events;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;

public class PartipantDAO {
    ConnectionManager cm;
    public PartipantDAO() throws Exception {
        cm = new ConnectionManager();
    }
    public Events addParticipant(Events event) throws Exception{
        Events eventCreated=null;
        try (Connection con = cm.getConnection()) {
            String sql = "INSERT INTO EVENTS VALUES (?,?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,event.getName());
            pstmt.setString(2,event.getLocation());
            pstmt.setString(3,event.getDescription());
            pstmt.setObject(4,event.getStartDate());
            if(event.getEndDate()==null) {
                pstmt.setNull(5, Types.NULL);
            } else {
                pstmt.setObject(5,event.getEndDate());
            }
            if(event.getItinerary()==null) {
                pstmt.setNull(6, Types.NULL);
            } else {
                pstmt.setString(6,event.getItinerary());
            }
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            while(rs.next())
            {
                eventCreated= new Events(
                        rs.getInt(1),
                        event.getName(),
                        event.getLocation()
                );
                eventCreated.setDescription(event.getDescription());
                eventCreated.setStartDate(event.getStartDate());
                if(event.getEndDate()!=null) {
                    eventCreated.setEndDate(event.getEndDate());
                }
                if(event.getItinerary()!=null) {
                    eventCreated.setItinerary(event.getItinerary());
                }
            }
        }

        return eventCreated;
    }
}
