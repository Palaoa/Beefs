package com.example.dell.dbtest.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2016/11/2.
 */

public class UserModel implements Serializable {
    public String user_id;
    public String nickname;
    public String slogan;
    public String avatar;
    public Date birthday;
    public String occupation;
    public char gender;
    public String address;
    public int grade;
    public int coin;
    public int contribution;
    public String password;
    public UserSecurityModel usm;
    //private String

}
