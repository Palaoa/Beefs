package com.example.dell.dbtest.activitys;

import android.os.Bundle;

import com.example.dell.dbtest.R;

import java.util.ArrayList;

/**
 * Created by dell on 2016/11/5.
 */

public class ActivityMyStory extends MyActivity
{
    @Override
    public void getResult(ArrayList<String> result)
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        name = "ActivityMyStory";
        setContentView(R.layout.mystory_layout);
        ActivityManager.getActivityManager().addActivity(this);

    }

}
