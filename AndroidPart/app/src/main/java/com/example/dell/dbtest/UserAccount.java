package com.example.dell.dbtest;

/**
 * Created by dell on 2016/10/31.
 */

public class UserAccount {
    private static UserAccount instance;
    private static UserModel currentUser;
    private static int loginTime;

    public UserAccount getInstance()
    {
        if(instance == null)
            instance = new UserAccount();
        return  instance;
    }

    public void setUser(UserModel user)
    {
        if(currentUser == null)
        {
            currentUser = new UserModel();

        }
        currentUser.user_id = user.user_id;
        currentUser.nickname = user.nickname;
        currentUser.password = user.password;
        return;
    }

    public UserModel getUser()
    {
        return currentUser;
    }
}
