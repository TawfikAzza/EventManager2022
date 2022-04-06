package bll.utils;

import be.Users;

public class LoggedInUser {

    private static Users user;

    private LoggedInUser()
    {
    }

    public static Users getInstance(Users theUser)
    {
        if(user==null)
        {
            user = theUser;
        }
        return user;
    }
}
