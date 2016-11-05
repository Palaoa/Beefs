package com.example.dell.dbtest.activitys;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by dell on 2016/11/5.
 */

public abstract class MyActivity extends Activity
{
    protected String name;

    public abstract void getResult(ArrayList<String> result);

    public String getName()
    {
        return name;
    }

}
