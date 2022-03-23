package bll;

import be.Users;
import dal.db.UsersDAO;

public class MainManager {
    UsersDAO usersDAO;

    {
        try {
            usersDAO = new UsersDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Users submitLogin(String username, String password) throws Exception
    {
        Users users= usersDAO.compareLogins(username, password);
        if (users != null){
            return users;

        }
        else {
            return null;
        }


    }
}
