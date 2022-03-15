package gui.Model;

import be.Coordinator;
import bll.AdminLogic;

import java.io.IOException;

public class AdminModel {

    private AdminLogic logic;

    public AdminModel() throws IOException {
        this.logic = new AdminLogic();
    }

    public void addEventCoordinator(Coordinator coordinator)
    {
        logic.addEventCoordinator(coordinator);
    }

}
