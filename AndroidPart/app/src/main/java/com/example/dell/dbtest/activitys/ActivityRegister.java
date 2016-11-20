package com.example.dell.dbtest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dell.dbtest.Models.UserModel;
import com.example.dell.dbtest.QueryManager;
import com.example.dell.dbtest.R;
import com.example.dell.dbtest.ResultParser;
import com.example.dell.dbtest.UserAccount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2016/11/20.
 */


/*
  nick gender birthday phone pwd*2
    */
public class ActivityRegister extends MyActivity
{
    private Button btnOK;
    private Spinner genderSpin,monthSpin;
    private EditText nickEdt,yearEdt,dayEdt,phoneEdt,pwdEdt;
    private MyActivity ma;
    private UserModel um;
    @Override
    public void getResult(ArrayList<String> result)
    {
        String methodName = result.get(0);
        Intent intent = new Intent();
        try {
            switch (methodName) {
                case "insertUser":
                    String str = result.get(1);
                    //text.setText(str);
                    String id = ResultParser.parseID(str);
                    if(id != "false")
                    {
                        um.user_id = id;
                    }
                    else
                    {
                        um = null;
                        return;
                    }
                    UserAccount.getInstance().setUser(um);
                    //  Fan Hui Main
                    intent.putExtra("ShowToast", true);
                    setResult(301, intent);
                    finish();
                    //
                    return;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        name = "ActivityRegister";
        setContentView(R.layout.register_layout);
        ActivityManager.getActivityManager().addActivity(this);
        ma = this;
        genderSpin = (Spinner)findViewById(R.id.register_gender_spin);
        monthSpin = (Spinner)findViewById(R.id.register_month_spin);
        nickEdt = (EditText)findViewById(R.id.register_nick_edt);
        yearEdt = (EditText)findViewById(R.id.register_year_edt);
        dayEdt = (EditText)findViewById(R.id.register_day_edt);
        phoneEdt = (EditText)findViewById(R.id.register_phone_edt);
        pwdEdt = (EditText)findViewById(R.id.register_pwd_edt);
        btnOK = (Button) findViewById(R.id.register_ok_btn);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick = nickEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();
                String gender = genderSpin.getSelectedItem().toString();
                String birthday = monthSpin.getSelectedItem().toString() +"-"+ dayEdt.getText().toString() +"-"+ yearEdt.getText().toString();
                String phone = phoneEdt.getText().toString();
                um = new UserModel();
                if(nick.length()!=0&&pwd.length()!=0&&gender.length()!=0&&birthday.length()!=0&&phone.length()!=0)
                {
                    if(gender=="Male")
                    {
                        gender = "M";
                    }
                    else
                    {
                        gender = "F";
                    }
                    um.password = pwd;
                    um.gender = gender.charAt(0);
                    um.nickname = nick;
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
                    try {
                        um.birthday = sdf.parse(birthday);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        um = null;
                        return;
                    }
                    QueryManager qm = new QueryManager(ma);
                    qm.execute("insertUser","password",pwd,"nickname",nick,"gender",gender,"birthday",birthday,"phone",phone);
                    return;
                }
            }
        });
    }

}
