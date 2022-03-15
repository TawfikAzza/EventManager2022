package bll;

import be.Coordinator;
import dal.db.AdminDAO;
import dal.interfaces.IAdminDAO;

import java.io.IOException;

public class AdminLogic {

    private IAdminDAO adminDAO;

    public AdminLogic() throws IOException {
        this.adminDAO = new AdminDAO();
    }

    public void addEventCoordinator(Coordinator coordinator)
    {
        adminDAO.addEventCoordinator(coordinator);
    }

}
