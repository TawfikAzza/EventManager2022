package dal.db;

import be.Coordinator;
import be.Events;
import be.Participant;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class PartipantDAO {
    ConnectionManager cm;
    public PartipantDAO() throws Exception {
        cm = new ConnectionManager();
    }
    public ArrayList<Participant> getAllParticipants() throws Exception
    {
        ArrayList<Participant> allParticipants = new ArrayList<>();

        try (Connection con = cm.getConnection()) {
            String sql ="SELECT * FROM Participant";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs =pstmt.executeQuery(sql);

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
    public Participant addParticipant(Participant participant) throws Exception {
        Participant participantCreated=null;
        try (Connection con = cm.getConnection()) {
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
            String sql = "UPDATE Participant SET fname = ?, lname = ?, phoneNumber = ? email = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, participant.getFname());
            pstmt.setString(2, participant.getLname());
            pstmt.setString(3, participant.getPhoneNumber());
            pstmt.setString(4, participant.getEmail());
            pstmt.setInt(5, participant.getId());


            pstmt.execute();
        }
    }
    public void deleteParticipant(Participant participant) throws Exception{
        try (Connection con = cm.getConnection()) {
            String sql = "DELETE FROM Participant WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, participant.getId());
            pstmt.execute();

        }
    }

}
