package dal.db;

import be.Participant;
import be.Users;
import bll.utils.DisplayError;
import dal.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UsersDAO {
    ConnectionManager cm;
    public UsersDAO() throws Exception {
        cm = new ConnectionManager();
    }
    public Users compareLogins(String username, String password)
    {
        Users users = null;

        try (Connection con = cm.getConnection()) {
            String sql ="SELECT [loginName],[password],[email],[roleID],[userID] FROM LoginUser WHERE [loginName] = ? AND [password] = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {
                int userID = rs.getInt("userID");
                String loginName = rs.getString("loginName");
                String email = rs.getString("email");
                int roleID = rs.getInt("roleID");

                users = new Users(userID, loginName, email, roleID);
            }
        }
        catch (Exception e)
        {
            users =null;
            DisplayError.displayMessage("Cannot connect to the database");
        }
        return users;
    }
}
