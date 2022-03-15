package dal.db;

import be.Coordinator;
import dal.ConnectionManager;
import dal.interfaces.IAdminDAO;

import java.io.IOException;

public class AdminDAO implements IAdminDAO {

    private ConnectionManager dbc;

    public AdminDAO() throws IOException {
        this.dbc = new ConnectionManager();
    }

    @Override
    public void addEventCoordinator(Coordinator coordinator) {

    }
}
