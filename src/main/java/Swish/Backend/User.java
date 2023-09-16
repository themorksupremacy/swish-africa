package Swish.Backend;

import java.util.Date;

public class User {

    //private fields
    private String firstName;
    private String lastName;
    private java.sql.Date dob;
    private String emailAddress;
    private String pass;
    private int userID;
    private String userType;

    //default constructor
    public User() {
    }

    //parameterised constructor
    public User(String f, String l, java.sql.Date d, String e, String p, String ut) {
        firstName = f;
        lastName = l;
        dob = d;
        emailAddress = e;
        pass = p;
        userType = ut;
    }

    //generates a random number for resetting a user's password
    public static String resetPswrd() {
        int i = ((int) (Math.random() * 100000)) % 10000000; //random number generated
        String temp = String.valueOf(i);
        return temp; //new password returned
    }

    //getter
    public String getFirstName() {
        return firstName;
    }

    //setter
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //getter
    public String getLastName() {
        return lastName;
    }

    //setter
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //getter
    public java.sql.Date getDob() {
        return dob;
    }

    //setter
    public void setDob(java.sql.Date dob) {
        this.dob = dob;
    }

    //getter
    public String getEmailAddress() {
        return emailAddress;
    }

    //setter
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    //getter
    public String getPass() {
        return pass;
    }

    //setter
    public void setPassword(String password) {
        this.pass = password;
    }

    //getter
    public int getUserID() {
        return userID;
    }

    //setter
    public void setUserID(int userID) {
        this.userID = userID;
    }

    //getter
    public String getUserType() {
        return userType;
    }

    //setter
    public void setUserType(String userType) {
        this.userType = userType;
    }

    //toString method for user fields
    public String toString(){
        String temp = getUserID() + " | " + getFirstName() + " " + getLastName() + " | " + getDob() + " | "
                + getEmailAddress() + " | " + getUserType();
        return temp;
    }

}
