package com.example.dell.dbtest.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.dbtest.Models.UserModel;
import com.example.dell.dbtest.R;
import com.example.dell.dbtest.UserAccount;

import java.util.ArrayList;

/**
 * Created by dell on 2016/11/5.
 */

public class ActivityMyInfo extends MyActivity
{
    private TextView textID,textNick,textSlogan,textBirthday,textOccu, textGender,textAddress,
        textGrade,textCoin,textContri;
    private Button btnEdit,btnLogout;
    MyActivity ma;
    @Override
    public void getResult(ArrayList<String> result)
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        name = "ActivityMyInfo";
        setContentView(R.layout.myinfo_layout);
        ActivityManager.getActivityManager().addActivity(this);
        ma = this;
        // texts
        textID = (TextView)findViewById(R.id.myinfo_id_text);
        textAddress = (TextView)findViewById(R.id.myinfo_address_text);
        textBirthday = (TextView)findViewById(R.id.myinfo_birthday_text);
        textCoin = (TextView)findViewById(R.id.myinfo_coin_text);
        textContri = (TextView)findViewById(R.id.myinfo_contri_text);
        textGender = (TextView)findViewById(R.id.myinfo_gender_text);
        textGrade = (TextView)findViewById(R.id.myinfo_grade_text);
        textNick = (TextView)findViewById(R.id.myinfo_nickname_text);
        textOccu = (TextView)findViewById(R.id.myinfo_occu_text);
        textSlogan = (TextView)findViewById(R.id.myinfo_slogan_text);
        //
        btnEdit = (Button)findViewById(R.id.myinfo_edit_btn);
        btnLogout = (Button)findViewById(R.id.myinfo_logout_btn);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAccount.getInstance().logout();
                ma.finish();
            }
        });
    }

    @Override protected void onResume()
    {
        super.onResume();
        if(!UserAccount.getInstance().getState())  // wei deng lu
        {

        }
        else
        {
            UserModel um = UserAccount.getInstance().getUser();
            textID.setText("ID :"+um.user_id);
            textAddress.setText("Addr :"+um.address);
            textBirthday.setText("Birthday :"+um.birthday.toString());  // hai yao gai
            textCoin.setText("Coin :"+um.coin);
            textContri.setText("Contribution :"+um.contribution);
            textGender.setText("Gender :"+um.gender);
            textGrade.setText("Grade :"+um.grade);
            textNick.setText("Nickname :"+um.nickname);
            textOccu.setText("Occupation :"+um.occupation);
            textSlogan.setText("Slogan :"+um.slogan);
        }
    }
}
