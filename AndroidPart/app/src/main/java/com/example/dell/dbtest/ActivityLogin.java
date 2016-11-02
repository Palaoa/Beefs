package com.example.dell.dbtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import com.example.dell.dbtest.ActivityManager;
/**
 * Created by dell on 2016/10/31.
 */

public class ActivityLogin extends Activity
{
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ActivityManager.getActivityManager().addActivity(this);

        btn = (Button) findViewById(R.id.login_confirm_btn);

    }
}
