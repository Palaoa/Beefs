package com.example.dell.dbtest.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.dbtest.R;

import java.util.ArrayList;

/**
 * Created by dell on 2016/11/14.
 */

public class ActivityStoryPublish extends MyActivity
{
    private EditText edtSTime,edtETime,edtCity;
    private Button btn;

    @Override
    public void getResult(ArrayList<String> result)
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mystory_publish_layout);
        name = "ActivityStoryPublish";
        ActivityManager.getActivityManager().addActivity(this);

        edtCity = (EditText) findViewById(R.id.mystoryp_city_edit);
        edtSTime = (EditText) findViewById(R.id.mystoryp_stime_edit);
        edtETime = (EditText) findViewById(R.id.mystoryp_etime_edit);
        btn = (Button) findViewById(R.id.mystoryp_ok_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishStory();
            }
        });
    }

    private void publishStory()
    {

    }
}
