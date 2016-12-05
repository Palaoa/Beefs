package io.github.froger.instamaterial;

import io.github.froger.instamaterial.models.UserModel;

import java.util.Date;

/**
 * Created by dell on 2016/10/31.
 */


/*
* 用来处理用户的登陆信息处理判断 暂时存储
* */
public class UserAccount {
    private static UserAccount instance;
    private static UserModel currentUser;
    private static boolean state;  // 0 - no 1 - yes
    private static Date loginTime;

    static public UserAccount getInstance()
    {
        if(instance == null)
        {
            instance = new UserAccount();
            state = false;
        }
        return  instance;
    }
    // Login
    public void setUser(UserModel user)
    {
        currentUser = user;
        state = true;
        loginTime = new Date();
        return;
    }

    public UserModel getUser()
    {
        return currentUser;
    }

    public boolean getState()
    {
        return state;
    }

    public void logout()
    {
        currentUser = null;
        state = false;
        return;
    }

    public Date getLoginTime()
    {
        return loginTime;
    }
}
