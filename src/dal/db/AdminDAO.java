package dal.db;

import be.Admin;
import be.Coordinator;
import be.Users;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ConnectionManager;
import dal.interfaces.IAdminDAO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements IAdminDAO {

    private ConnectionManager dbc;

    public AdminDAO() throws IOException {
        this.dbc = new ConnectionManager();
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
    public void deleteEventCoordinator(Coordinator coordinator) {
        int userID = coordinator.getUserID();

        try (Connection connection = dbc.getConnection()) {
            String sql = "DELETE FROM LoginUser WHERE userID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, userID);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateEventCoordinator(Coordinator coordinator) {

        String loginName = coordinator.getLoginName();
        String password = coordinator.getPassword();;
        String email = coordinator.getMail();
        int userID = coordinator.getUserID();
        String fname = coordinator.getFirstName();
        String lname = coordinator.getLastName();

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

    @Override
    public void deleteAdmin(Admin admin) {
        int userID = admin.getUserID();

        try (Connection connection = dbc.getConnection()) {
            String sql = "DELETE FROM LoginUser WHERE userID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, userID);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void editAdmin(Admin admin) {

        String loginName = admin.getLoginName();
        String password = admin.getPassword();;
        String email = admin.getMail();
        int userID = admin.getUserID();
        String fname = admin.getFirstName();
        String lname = admin.getLastName();

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

    private int checkUserRole(Users user)
    {
        return user.getRoleID();
    }

}
