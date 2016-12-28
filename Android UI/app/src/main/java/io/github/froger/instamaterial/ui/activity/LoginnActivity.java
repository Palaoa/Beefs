package io.github.froger.instamaterial.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.github.froger.instamaterial.QueryManager;
import io.github.froger.instamaterial.R;
import io.github.froger.instamaterial.UserAccount;
import io.github.froger.instamaterial.models.UserModel;

/**
 * Created by dell on 2016/12/14.
 */

public class LoginnActivity extends BaseActivity
{
    BaseActivity ba;
    Button okBtn, registBtn;
    EditText nickEdt, pwdEdt;

    public static void startLoginActivity(Activity startingActivity)
    {
        Intent intent = new Intent(startingActivity,LoginnActivity.class);
        startingActivity.startActivity(intent);
    }


    @Override
    public void getResult(ArrayList<String> result)
    {
        String methodName = result.get(0);
        Intent intent = new Intent();
        try {
            switch (methodName) {
                case "loginUser":
                    String str = result.get(1);
                    //text.setText(str);
                    List<UserModel> list = io.github.froger.instamaterial.ResultParce.parseUser(str);
                    UserModel um = null;
                    if(list.size() != 0)
                    {
                        um = list.get(0);
                        UserAccount.getInstance().setUser(um);
                        //  Fan Hui Main
                        intent.putExtra("ShowToast", true);
                        setResult(201, intent);
                        finish();
                        //
                        return;
                    }
                    return;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //intent.putExtra("ShowToast",false);
        //setResult(201,intent);
        //finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ba = this;
        okBtn = (Button) findViewById(R.id.login_btnok);
        nickEdt = (EditText) findViewById(R.id.login_edtnick);
        pwdEdt = (EditText) findViewById(R.id.login_edtpwd);
        registBtn = (Button) findViewById(R.id.login_btnregist);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nickEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();
                if(name != ""&&pwd != "")
                {
                    QueryManager qm = new QueryManager(ba);
                    qm.execute("loginUser","nickname",name,"password",pwd);
                    return;
                }
            }
        });
        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName(getApplicationContext(),"io.github.froger.instamaterial.ui.activity.RegisterActivity");
                startActivity(intent);
            }
        });

    }
}
