package bll;

import be.Admin;
import be.Coordinator;
import be.Users;
import bll.exception.AdminLogicException;
import bll.exception.LoginException;
import dal.db.AdminDAO;
import dal.interfaces.IAdminDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminLogic {

    private IAdminDAO adminDAO;

    public AdminLogic() throws AdminLogicException {
        try {
            this.adminDAO = new AdminDAO();
        } catch (IOException e) {
            throw new AdminLogicException("Failed to initialize Admin Logic class!",e);
        }
    }

    public void addLoginUser(Users user)
    {
        adminDAO.addLoginUser(user);
    }

    public void deleteUser(Users user)
    {
        adminDAO.deleteUser(user);
    }

    public void editUser(Users user)
    {
        adminDAO.editUser(user);
    }

    public ArrayList<Coordinator> getAllCoordinators()
    {
        return adminDAO.getAllCoordinators();
    }

    public ArrayList<Admin> getAllAdmins()
    {
        return adminDAO.getAllAdmins();
    }

    public ArrayList<String> getAccountTypes(){
        return adminDAO.getAccountTypes();
    }

    public Users getUser(String username, String password) throws SQLException {
            return adminDAO.getUser(username, password);
    }

}
