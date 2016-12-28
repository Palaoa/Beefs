package io.github.froger.instamaterial.ui.activity;

/**
 * Created by dell on 2016/12/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import io.github.froger.instamaterial.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


import io.github.froger.instamaterial.ResultParce;

/**
 * Created by dell on 2016/11/20.
 */


/*
  nick gender birthday phone pwd*2
    */
public class RegisterActivity extends BaseActivity
{
    private Button btnOK;
    private Spinner genderSpin,monthSpin;
    private EditText nickEdt,yearEdt,dayEdt,phoneEdt,pwdEdt;
    private BaseActivity ma;
    private io.github.froger.instamaterial.models.UserModel um;
    @Override
    public void getResult(ArrayList<String> result)
    {
        String methodName = result.get(0);
        //Intent intent = new Intent();
        try {
            switch (methodName) {
                case "insertUser":
                    String str = result.get(1);
                    //text.setText(str);
                    String id = ResultParce.parseID(str);
                    if(id != "false")
                    {
                        um.user_id = id;
                    }
                    else
                    {
                        um = null;
                        return;
                    }
                    io.github.froger.instamaterial.UserAccount.getInstance().setUser(um);
                    //  Fan Hui Main
                    //intent.putExtra("ShowToast", true);
                    //setResult(301, intent);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.setAction(MainActivity.ACTION_SHOW_LOADING_ITEM);
                    startActivity(intent);
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
        setContentView(R.layout.activity_regist);
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
                um = new io.github.froger.instamaterial.models.UserModel();
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
                    io.github.froger.instamaterial.QueryManager qm = new io.github.froger.instamaterial.QueryManager(ma);
                    qm.execute("insertUser","password",pwd,"nickname",nick,"gender",gender,"birthday",birthday,"phone",phone);
                    return;
                }
            }
        });
    }

}
