package dal.interfaces;

import be.Admin;
import be.Coordinator;
import be.Users;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IAdminDAO {

    void addLoginUser(Users user) throws SQLException;

    void deleteUser(Users user) throws SQLException;

    void editUser(Users user) throws SQLException;

    ArrayList<Coordinator> getAllCoordinators() throws SQLException;

    ArrayList<Admin> getAllAdmins() throws SQLException;

    ArrayList<String> getAccountTypes() throws SQLException;

    Users getUser(String username, String password) throws SQLException;

}
