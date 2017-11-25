package com.questionqate.Pojo;

/**
 * Created by anarose on 11/1/17.
 */

public class Student {

    public String userName;
    public String userID;
    public String userEmail;
    public String userMobile;
    public String userpassword;
    public String fireBaseUserId;

    public Student() {
    }

    public Student(String userName, String userID, String userEmail, String userMobile, String userpassword, String firebaseUserId) {
        this.userName = userName;
        this.userID = userID;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.userpassword = userpassword;
        this.fireBaseUserId = firebaseUserId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public String getFireBaseUserId() {
        return fireBaseUserId;
    }
}
