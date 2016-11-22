package com.example.dell.dbtest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dell.dbtest.Models.StoryModel;
import com.example.dell.dbtest.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by dell on 2016/11/14.
 */

public class ActivityStoryOne extends MyActivity
{
    private Button btn_edit,btn_publish;
    private TextView txt_title,txt_content;
    private StoryModel sm;
    @Override
    public void getResult(ArrayList<String> result)
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sm = (StoryModel)getIntent().getSerializableExtra("StoryModel");

        name = "ActivityStoryOne";
        ActivityManager.getActivityManager().addActivity(this);

        btn_edit = (Button)findViewById(R.id.mystoryo_edit_btn);
        btn_publish = (Button)findViewById(R.id.mystoryo_publish_btn);
        txt_title = (TextView)findViewById(R.id.mystoryo_title_text);
        txt_title.setText(sm.title);
        txt_content = (TextView)findViewById(R.id.mystoryo_content_text);
        txt_content.setText(sm.content);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("StoryModel",sm);
                intent.putExtras(bundle);
                intent.setClassName(getApplicationContext(),"com.example.dell.dbtest.activitys.ActivityStoryEdit");
                startActivity(intent);
            }
        });

        setContentView(R.layout.mystory_one_layout);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        reCheck();
    }

    private void reCheck()
    {

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        ActivityManager.getActivityManager().popActivity();
    }
}
