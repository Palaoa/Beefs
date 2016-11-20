package com.example.dell.dbtest.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.dell.dbtest.R;
import com.example.dell.dbtest.UserAccount;

public class MainActivity extends MyActivity {

    private Button btnTest;
    private Button btnLogin;
    private Button btnMyInfo,btnWriteStory,btnMyStory,btnRegister;
    private MyActivity ma;
    //private View loginView;
    //private View myInfoView;
    private TextView showInfo;
    private String result;

    @Override
    public void getResult(ArrayList<String> result)
    {

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        boolean state = UserAccount.getInstance().getState();
        if(state)
        {
            showInfo.setText("Hello" + UserAccount.getInstance().getUser().user_id);

        }
        else
        {
            showInfo.setText("Welcome, please login");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        name = "MainActivity";
        ma = this;
        setContentView(R.layout.activity_main);
        ActivityManager.getActivityManager().addActivity(this);
        showInfo = (TextView)findViewById(R.id.main_show_text);
        btnTest = (Button)findViewById(R.id.main_test_btn);
        btnTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //just some tests put here
            }
        });
        //loginView = getLayoutInflater().inflate(R.layout.login_layout,null);
        btnLogin = (Button)findViewById(R.id.main_2login_btn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(getApplicationContext(),"com.example.dell.dbtest.activitys.ActivityLogin");
                startActivityForResult(intent,101);
                //.dbtest.Activitys.ActivityLogin
            }
        });
        //myInfoView = getLayoutInflater().inflate(R.layout.myinfo_layout,null);
        btnMyInfo = (Button)findViewById(R.id.main_2myinfo_btn);
        btnMyInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                boolean state = UserAccount.getInstance().getState();
                if(state)
                {
                    Intent intent = new Intent();
                    intent.setClassName(getApplicationContext(),"com.example.dell.dbtest.activitys." +
                            "ActivityMyInfo");
                    startActivity(intent);
                }
                else
                {

                    Intent intent = new Intent();
                    intent.setClassName(getApplicationContext(),"com.example.dell.dbtest.activitys." +
                            "ActivityLogin");
                    //intent.putExtra("ShowToast","Please login first");
                    startActivityForResult(intent,101);
                }
            }
        });
        //  Hai Mei Xie Wan
        btnWriteStory = (Button)findViewById(R.id.main_2write_btn);
        btnWriteStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean state = UserAccount.getInstance().getState();
                if(state)
                {
                    Intent intent = new Intent();
                    intent.setClassName(getApplicationContext(),"com.example.dell.dbtest.activitys.ActivityStoryWrite");
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent();
                    intent.setClassName(getApplicationContext(),"com.example.dell.dbtest.activitys." +
                            "ActivityLogin");
                    //intent.putExtra("ShowToast","Please login first");
                    startActivityForResult(intent,102);
                }

            }
        });
        btnMyStory = (Button)findViewById(R.id.main_2mystory_btn);

        btnRegister = (Button)findViewById(R.id.main_register_btn);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(getApplicationContext(),"com.example.dell.dbtest.activitys." +
                        "ActivityRegister");
                //intent.putExtra("ShowToast","Please login first");
                startActivityForResult(intent,103);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        /*
        !!!!!!!!!!!!!!!!!!!!!!!!
        !!!!!!!!!!!!!!!!!!!!!!!!
        */
        //101 info 102 write story 103 register

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == 201)  // deng lu
        {
            Boolean result_value = data.getBooleanExtra("ShowToast",false);
            if(result_value != null && result_value)
            {
                //Toast toast = new Toast(getApplicationContext());
                //toast.setText("Login success!");
                //toast.setDuration(Toast.LENGTH_SHORT);
                //toast.show();
            }
        }
    }

}
