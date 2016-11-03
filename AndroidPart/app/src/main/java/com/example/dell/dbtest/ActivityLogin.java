package com.example.dell.dbtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.dbtest.ActivityManager;

import java.util.ArrayList;

/**
 * Created by dell on 2016/10/31.
 */

public class ActivityLogin extends Activity
{
    private Button btn;
    private EditText edtnick;
    private EditText edtpass;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ActivityManager.getActivityManager().addActivity(this);
        edtnick = (EditText) findViewById(R.id.edit_nickname);
        edtpass = (EditText) findViewById(R.id.edit_password);
        btn = (Button) findViewById(R.id.login_confirm_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> result;
                String name = edtnick.getText().toString();
                String pwd = edtpass.getText().toString();
                ArrayList<String> pair = null;
                if(name != ""&&pwd !="")
                {
                    pair = new ArrayList<String>();
                    pair.add("nickname");
                    pair.add(name);
                    pair.add("password");
                    pair.add(pwd);
                    result = QueryManager.getQueryManager().query("loginUser",pair);
                    return;
                }
            }
        });
    }
}
