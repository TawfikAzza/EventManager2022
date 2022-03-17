package be;

public class Coordinator extends Users{

    public Coordinator(String loginName, String password, int roleID, String mail, String firstName, String lastName) {
        super(loginName, password, roleID, mail, firstName, lastName);
    }

    public Coordinator(int userID, String loginName, String password, int roleID, String mail, String firstName, String lastName) {
        super(userID, loginName, password, roleID, mail, firstName, lastName);
    }
}
