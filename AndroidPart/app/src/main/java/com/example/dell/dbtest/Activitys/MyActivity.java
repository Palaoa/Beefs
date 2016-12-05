package com.example.dell.dbtest.activitys;

import android.app.Activity;

import java.util.ArrayList;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.BindView;
import com.example.dell.dbtest.R;

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
