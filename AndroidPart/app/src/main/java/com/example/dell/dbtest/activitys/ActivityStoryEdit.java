package com.example.dell.dbtest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.dbtest.Models.StoryModel;
import com.example.dell.dbtest.Models.UserModel;
import com.example.dell.dbtest.QueryManager;
import com.example.dell.dbtest.R;
import com.example.dell.dbtest.ResultParser;
import com.example.dell.dbtest.UserAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/11/14.
 */

public class ActivityStoryEdit extends MyActivity
{
    private EditText edtTitle,edtContent;
    private Button btnOK;
    private StoryModel sm;
    private MyActivity ma;

    @Override
    public void getResult(ArrayList<String> result)
    {
        String methodName = result.get(0);
        Intent intent = new Intent();
        try {
            switch (methodName) {
                case "updateStory":
                    String str = result.get(1);
                    //text.setText(str);
                    boolean bool = ResultParser.parseBool(str);
                    if(bool)
                    {
                        finish();
                        return;
                    }
                    return;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        name = "ActivityStoryEdit";
        sm = (StoryModel)getIntent().getExtras().getSerializable("StoryModel");
        ma = this;
        edtTitle = (EditText)findViewById(R.id.mystorye_title_et);
        edtContent = (EditText)findViewById(R.id.mystorye_content_et);
        btnOK = (Button)findViewById(R.id.mystorye_ok_btn);
        edtTitle.setText(sm.title);
        edtContent.setText(sm.content);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mTitle = edtTitle.getText().toString();
                String mContent = edtContent.getText().toString();
                QueryManager qm = new QueryManager(ma);
                UserModel um = UserAccount.getInstance().getUser();
                sm.title = mTitle;
                sm.content = mContent;
                qm.execute("updateStory","user_id",um.user_id,"password",um.password,
                        "story_id",sm.story_id,"title",mTitle,"content",mContent);
            }
        });

        setContentView(R.layout.mystory_edit_layout);
        ActivityManager.getActivityManager().addActivity(this);

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        ActivityManager.getActivityManager().popActivity();
    }
}
