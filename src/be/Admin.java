package be;

public class Admin extends Users {


    public Admin(String loginName, String password, int roleID, String mail) {
        super(loginName, password, roleID, mail);
    }

    public Admin(int userID, String loginName, String password, int roleID, String mail, String firstName, String lastName) {
        super(userID, loginName, password, roleID, mail, firstName, lastName);
    }
}
