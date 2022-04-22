package bll;

import be.Users;
import bll.utils.LoggedInUser;
import dal.db.UsersDAO;

public class MainManager {
    UsersDAO usersDAO;

    public MainManager()
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
            LoggedInUser.getInstance(users);
            return users;

        }
        else {
            return null;
        }


    }
}
