package gui.Model;

import be.Coordinator;
import bll.AdminLogic;
import bll.exception.AdminLogicException;

import java.io.IOException;
import java.util.ArrayList;

public class AdminModel {

    private AdminLogic logic;

    public AdminModel() throws AdminLogicException {
        this.logic = new AdminLogic();
    }

    public void addEventCoordinator(Coordinator coordinator)
    {
        logic.addEventCoordinator(coordinator);
    }

    public void deleteEventCoordinator(Coordinator coordinator)
    {
        logic.deleteEventCoordinator(coordinator);
    }
    public void updateEventCoordinator(Coordinator coordinator)
    {
        logic.updateEventCoordinator(coordinator);
    }

    public ArrayList<Coordinator> getAllCoordinators()
    {
        return logic.getAllCoordinators();
    }

}
