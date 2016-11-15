package com.example.dell.dbtest.activitys;

import android.os.Bundle;

import com.example.dell.dbtest.R;

import java.util.ArrayList;

/**
 * Created by dell on 2016/11/14.
 */

public class ActivityStoryEdit extends MyActivity
{
    @Override
    public void getResult(ArrayList<String> result)
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        name = "ActivityStoryEdit";
        setContentView(R.layout.mystory_edit_layout);
        ActivityManager.getActivityManager().addActivity(this);

    }
}
