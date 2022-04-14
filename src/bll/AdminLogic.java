package bll;

import be.Admin;
import be.Coordinator;
import be.Users;
import bll.exception.AdminDAOException;
import bll.exception.AdminLogicException;
import bll.exception.LoginException;
import bll.utils.LogCreator;
import bll.utils.LoggedInUser;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.AdminDAO;
import dal.interfaces.IAdminDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminLogic {

    private IAdminDAO adminDAO;
    private LogCreator log;

    public AdminLogic() throws  AdminDAOException {
        try {
            this.adminDAO = new AdminDAO();
            this.log = new LogCreator("AdminOperations");
        } catch (IOException e) {
            throw new AdminDAOException("Failed to initialize Admin Logic class!",e);
        }
    }

    public void addLoginUser(Users user) throws AdminDAOException
    {
        try {
            adminDAO.addLoginUser(user);
            logMessage("added", user);

        }
        catch (SQLServerException e)
        {
            throw new AdminDAOException("Username already exists.", e);
        } catch (SQLException e) {
            throw new AdminDAOException("Cannot connect to database.", e);
        }
    }

    public void deleteUser(Users user) throws AdminDAOException {
        try {
            adminDAO.deleteUser(user);
            logMessage("deleted", user);
        }
        catch (SQLException e)
        {
            throw new AdminDAOException("Failed to connect to the database.", e);
        }
    }

    public void editUser(Users user) throws AdminDAOException {
        try {
            adminDAO.editUser(user);
            logMessage("edited", user);
        }
        catch (SQLException e)
        {
            throw new AdminDAOException("Failed to connect to the database.", e);
        }
    }

    public ArrayList<Coordinator> getAllCoordinators() throws AdminDAOException {
        try {
            return adminDAO.getAllCoordinators();
        } catch (SQLException e) {
            throw new AdminDAOException("Failed to connect to the database.", e);
        }
    }

    public ArrayList<Admin> getAllAdmins() throws AdminDAOException {
        try{
            return adminDAO.getAllAdmins();
        }
        catch(SQLException e)
        {
            throw new AdminDAOException("Failed to connect to the database.", e);
        }
    }

    public ArrayList<String> getAccountTypes() throws AdminDAOException {
        try{
            return adminDAO.getAccountTypes();
        }
        catch(SQLException e)
        {
            throw new AdminDAOException("Failed to connect to the database.", e);
        }
    }

    public Users getUser(String username, String password) throws AdminDAOException {
        try{
            return adminDAO.getUser(username, password);
        }
        catch(SQLException e)
        {
            throw new AdminDAOException("Failed to connect to the database.", e);
        }
    }

    public void logMessage(String action, Users user)
    {
        Users loggedIn = LoggedInUser.getInstance(null);
        log.getLogger().info("Admin: " + loggedIn.getLoginName() + " with the ID: "+ loggedIn.getUserID() + " " + action + " User: " + user.getLoginName() + " with the ID " +user.getUserID());
    }

}
