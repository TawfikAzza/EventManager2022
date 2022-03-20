package bll;

import be.Admin;
import be.Coordinator;
import be.Users;
import bll.exception.AdminLogicException;
import dal.db.AdminDAO;
import dal.interfaces.IAdminDAO;

import java.io.IOException;
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

    public void deleteEventCoordinator(Coordinator coordinator)
    {
        adminDAO.deleteEventCoordinator(coordinator);
    }

    public void updateEventCoordinator(Coordinator coordinator)
    {
        adminDAO.updateEventCoordinator(coordinator);
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

}
