package com.example.dell.dbtest;

/**
 * Created by dell on 2016/10/31.
 */

public class UserAccount {
    private static UserAccount instance;
    private static String userID;
    private static int loginTime;

    public UserAccount getInstance()
    {
        if(instance == null)
            instance = new UserAccount();
        return  instance;
    }

    public void setUserID(String str)
    {
        if(userID == null)
        {
            userID = new String(str);
            return;
        }
        userID = str;
    }

    public String getUserID()
    {
        if(userID == null)
            return "";
        return userID;
    }
}
