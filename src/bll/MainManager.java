package bll;

import be.Users;
import bll.utils.LogCreator;
import bll.utils.LoggedInUser;
import dal.db.UsersDAO;

public class MainManager {
    UsersDAO usersDAO;
    LogCreator log;

    public MainManager()
    {
        try {
            this.log = new LogCreator("Login");
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
            log.getLogger().info("User with login name: " + users.getLoginName() + " and the ID: " + users.getUserID() + " logged in.");
            return users;

        }
        else {
            return null;
        }


    }
}
