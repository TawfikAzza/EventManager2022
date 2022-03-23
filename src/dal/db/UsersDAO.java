package dal.db;

import be.Participant;
import be.Users;
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
    public Users compareLogins(String username, String password) throws Exception
    {
        Users users = null;

        try (Connection con = cm.getConnection()) {
            String sql ="SELECT [loginName],[password],[email],[roleID] FROM LoginUser WHERE [loginName] = ? AND [password] = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next())
            {

                users = new Users(
                    rs.getString("loginName"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getInt("roleID")
                );


            }
        }
        catch (Exception e)
        {
            users =null;
            e.printStackTrace();
        }
        return users;
    }
}
