package dal.interfaces;

import be.Admin;
import be.Coordinator;
import be.Users;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IAdminDAO {

    void addLoginUser(Users user);

    void deleteUser(Users user);

    void editUser(Users user);

    ArrayList<Coordinator> getAllCoordinators();

    ArrayList<Admin> getAllAdmins();

    ArrayList<String> getAccountTypes();

}
