package dal.interfaces;

import be.Admin;
import be.Coordinator;
import be.Users;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IAdminDAO {

    void addLoginUser(Users user);

    void deleteEventCoordinator(Coordinator coordinator);

    void updateEventCoordinator(Coordinator coordinator);

    ArrayList<Coordinator> getAllCoordinators();

    ArrayList<Admin> getAllAdmins();

    ArrayList<String> getAccountTypes();

    void deleteAdmin(Admin admin);

    void editAdmin(Admin admin);
}
