package com.example.dell.dbtest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.dbtest.Models.UserModel;
import com.example.dell.dbtest.QueryManager;
import com.example.dell.dbtest.R;
import com.example.dell.dbtest.UserAccount;

import java.util.ArrayList;

/**
 * Created by dell on 2016/10/31.
 */

public class ActivityLogin extends MyActivity
{
    private Button btn;
    private EditText edtnick;
    private EditText edtpass;
    private TextView text;
    @Override
    public void getResult(ArrayList<String> result)
    {
        String methodName = result.get(0);
        Intent intent = new Intent();
        switch (methodName)
        {
            case "loginUser":
                String str = result.get(1);
                text.setText(str);
                //
                // Yao Gai
                UserAccount.getInstance().setUser(new UserModel());
                //  Fan Hui Main
                intent.putExtra("ShowToast",true);
                setResult(201,intent);
                this.finish();
                //
                return;
        }
        intent.putExtra("ShowToast",false);
        setResult(201,intent);
        this.finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        name = "ActivityLogin";
        setContentView(R.layout.login_layout);
        ActivityManager.getActivityManager().addActivity(this);
        edtnick = (EditText) findViewById(R.id.edit_nickname);
        edtpass = (EditText) findViewById(R.id.edit_password);
        btn = (Button) findViewById(R.id.login_confirm_btn);
        text = (TextView) findViewById(R.id.text_loginresult);
        final MyActivity ma = this;
        Intent intent = getIntent();
        String msg = intent.getStringExtra("ShowToast");
        if(msg != null)
        {
            Toast toast = new Toast(getApplicationContext());
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtnick.getText().toString();
                String pwd = edtpass.getText().toString();
                ArrayList<String> pair;
                if(name != ""&&pwd !="")
                {
                    pair = new ArrayList<>();
                    pair.add("loginUser");//method name
                    pair.add("nickname");
                    pair.add(name);
                    pair.add("password");
                    pair.add(pwd);
                    QueryManager qm = new QueryManager(ma);
                    qm.execute("loginUser","nickname",name,"password",pwd);
                    //result = QueryManager.getQueryManager().query("loginUser",pair);
                    return;
                }
            }
        });
    }
}
