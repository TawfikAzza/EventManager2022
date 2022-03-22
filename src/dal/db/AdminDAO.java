package dal.db;

import be.Admin;
import be.Coordinator;
import be.Users;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;
import dal.interfaces.IAdminDAO;
import org.apache.commons.math3.exception.NullArgumentException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements IAdminDAO {

    private ConnectionManager dbc;

    public AdminDAO() throws IOException {
        this.dbc = new ConnectionManager();
    }

    public Users getUser(String username, String password) throws SQLException {
        try (Connection connection = dbc.getConnection()) {
            String sql = "SELECT * FROM LoginUser WHERE loginName = ? AND password = ?";

            PreparedStatement ps = dbc.getConnection().prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ps.execute();
            ResultSet rs = ps.getResultSet();

            while(rs.next())
            {
                int userID = rs.getInt("userID");
                String loginName = rs.getString("loginName");
                String pass = rs.getString("password");
                int roleID = rs.getInt("roleID");
                String email = rs.getString("email");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");

                if(roleID == 1)
                {
                    return new Admin(userID, loginName, pass, roleID, email, fname, lname);
                }
                if(roleID == 2)
                {
                    return new Coordinator(userID, loginName, pass, roleID, email, fname, lname);
                }
            }

        }
        return null;
    }

    @Override
    public void addLoginUser(Users user) {
        String loginName = user.getLoginName();
        String password = user.getPassword();
        int roleID = user.getRoleID();
        String email = user.getMail();
        String fname = user.getFirstName();
        String lname = user.getLastName();

        try (Connection connection = dbc.getConnection()) {
            String sql = "INSERT INTO LoginUser(loginName, password, roleID, email, fname, lname) VALUES(?,?,?,?,?,?)";

            PreparedStatement ps = dbc.getConnection().prepareStatement(sql);

            ps.setString(1, loginName);
            ps.setString(2, password);
            ps.setInt(3, roleID);
            ps.setString(4, email);
            ps.setString(5, fname);
            ps.setString(6, lname);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void deleteUser(Users user) {
        int userID = user.getUserID();

        try (Connection connection = dbc.getConnection()) {
            String sql = "DELETE FROM LoginUser WHERE userID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, userID);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void editUser(Users user) {

        String loginName = user.getLoginName();
        String password = user.getPassword();;
        String email = user.getMail();
        int userID = user.getUserID();
        String fname = user.getFirstName();
        String lname = user.getLastName();

        try (Connection connection = dbc.getConnection()) {
            String sql = "UPDATE LoginUser SET loginName = ?, password = ?, email = ?, fname = ?, lname = ? WHERE userID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, loginName);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setString(4, fname);
            ps.setString(5, lname);
            ps.setInt(6, userID);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Coordinator> getAllCoordinators()
    {
        ArrayList<Coordinator> allCoordinators = new ArrayList<>();

        try (Connection connection = dbc.getConnection()) {
            String sql ="SELECT * FROM LoginUser WHERE roleID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, 2);

            statement.execute();

            ResultSet rs = statement.getResultSet();

            while(rs.next())
            {
                int userID = rs.getInt("userID");
                String loginName = rs.getString("loginName");
                String password = rs.getString("password");
                int roleID = rs.getInt("roleID");
                String email = rs.getString("email");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");

                Coordinator coordinator = new Coordinator(userID, loginName, password, roleID, email, fname, lname);
                allCoordinators.add(coordinator);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCoordinators;
    }

    public ArrayList<Admin> getAllAdmins()
    {
        ArrayList<Admin> allAdmins = new ArrayList<>();

        try (Connection connection = dbc.getConnection()) {
            String sql ="SELECT * FROM LoginUser WHERE roleID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, 1);

            statement.execute();

            ResultSet rs = statement.getResultSet();

            while(rs.next())
            {
                int userID = rs.getInt("userID");
                String loginName = rs.getString("loginName");
                String password = rs.getString("password");
                int roleID = rs.getInt("roleID");
                String email = rs.getString("email");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");

                Admin admin = new Admin(userID, loginName, password, roleID, email, fname, lname);
                allAdmins.add(admin);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAdmins;
    }

    public ArrayList<String> getAccountTypes(){
        ArrayList<String> accountTypes = new ArrayList<>();

        try (Connection connection = dbc.getConnection()) {
            String sql = "SELECT roleName FROM UserRoles";

            Statement statement = connection.createStatement();

            ResultSet rs =statement.executeQuery(sql);

            while(rs.next())
            {
                String accountType = rs.getString("roleName");
                accountTypes.add(accountType);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return accountTypes;
    }
    private int checkUserRole(Users user)
    {
        return user.getRoleID();
    }

}
