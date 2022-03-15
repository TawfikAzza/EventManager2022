package be;

import gui.Model.AdminModel;

public abstract class Users {
    private int userID;
    private String firstName;
    private String lastName;
    private String mail;
    private String loginName;
    private String password;
    private int roleID;

    public Users(String loginName, String password, int roleID, String mail) {
        this.mail=mail;
        this.loginName = loginName;
        this.password = password;
        this.roleID = roleID;
    }

    public Users(int userID, String loginName, String password, int roleID, String mail) {
        this.mail=mail;
        this.loginName = loginName;
        this.password = password;
        this.roleID = roleID;
        this.userID = userID;
    }

    public Users(int userID, String loginName, String password, int roleID, String mail, String firstName, String lastName) {
        this.mail=mail;
        this.loginName = loginName;
        this.password = password;
        this.roleID = roleID;
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}
