package dal.interfaces;

import be.Coordinator;
import be.Users;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IAdminDAO {

    void addLoginUser(Users user);

    void deleteEventCoordinator(Coordinator coordinator);

    void updateEventCoordinator(Coordinator coordinator);

    ArrayList<Coordinator> getAllCoordinators();

    ArrayList<String> getAccountTypes();

}
