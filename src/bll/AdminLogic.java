package bll;

import be.Coordinator;
import dal.db.AdminDAO;
import dal.interfaces.IAdminDAO;

import java.io.IOException;
import java.util.ArrayList;

public class AdminLogic {

    private IAdminDAO adminDAO;

    public AdminLogic() throws IOException {
        this.adminDAO = new AdminDAO();
    }

    public void addEventCoordinator(Coordinator coordinator)
    {
        adminDAO.addEventCoordinator(coordinator);
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

}
