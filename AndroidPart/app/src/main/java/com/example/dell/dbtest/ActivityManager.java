package com.example.dell.dbtest;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by dell on 2016/10/31.
 */

public class ActivityManager {

    private static Stack<Activity> activityStack;
    private static  ActivityManager instance;
    private ActivityManager(){}

    public static ActivityManager getActivityManager()
    {
        if(instance == null)
        {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity)
    {
        if(activityStack == null)
        {
            activityStack = new Stack<Activity>();
        }
        if(activity == null)
            return;
        activityStack.push(activity);
    }

    public Activity currentActivity()
    {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public void finishActivity(Activity activity)
    {
        if(activity != null)
        {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public void finishAllActivity()
    {
        for(Iterator<Activity> iterator = activityStack.iterator(); iterator.hasNext();)
        {
            Activity a = iterator.next();
            if(a != null)
                a.finish();
        }
        activityStack.clear();
    }
}
