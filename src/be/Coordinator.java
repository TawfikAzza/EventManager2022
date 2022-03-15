package be;

public class Coordinator extends Users{

    public Coordinator(String loginName, String password, int roleID, String mail) {
        super(loginName, password, roleID, mail);
    }

    public Coordinator(int userID, String loginName, String password, int roleID, String mail) {
        super(userID, loginName, password, roleID, mail);
    }
}
