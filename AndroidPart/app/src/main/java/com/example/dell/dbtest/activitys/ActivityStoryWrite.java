package com.example.dell.dbtest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.dbtest.Models.UserModel;
import com.example.dell.dbtest.QueryManager;
import com.example.dell.dbtest.R;
import com.example.dell.dbtest.ResultParser;
import com.example.dell.dbtest.UserAccount;

import java.util.ArrayList;

/**
 * Created by dell on 2016/11/5.
 */

public class ActivityStoryWrite extends MyActivity
{
    private MyActivity ma;
    private Button btn;
    private EditText titleEdt,contentEdt;
    @Override
    public void getResult(ArrayList<String> result)
    {
        // 301 cheng gong
        String methodName = result.get(0);
        Intent intent = new Intent();
        try{
            switch (methodName)
            {
                case "insertStory":
                    String str = result.get(1);
                    Boolean bool = ResultParser.parseBool(str);
                    if(bool)
                    {
                        // Cheng Gong
                        intent.putExtra("ShowToast", true);
                        setResult(301, intent);
                        finish();
                    }
                    // Bu Cheng Gong
                    Toast toast = new Toast(getApplicationContext());
                    toast.setText("Failed!");
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
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
        name = "ActivityStoryWrite";
        ma = this;
        setContentView(R.layout.mystory_write_layout);
        ActivityManager.getActivityManager().addActivity(this);
        btn = (Button)findViewById(R.id.mystoryw_OK_btn);
        titleEdt = (EditText)findViewById(R.id.mystoryw_title_edt);
        contentEdt = (EditText)findViewById(R.id.mystoryw_content_edt);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnClick();
            }
        });
    }

    private void onBtnClick()
    {
        String title,content;
        UserModel user = UserAccount.getInstance().getUser();
        title = titleEdt.getText().toString();
        if(title == null || title == "")
        {
            Toast toast = new Toast(getApplicationContext());
            toast.setText("Please write a title!");
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        content = contentEdt.getText().toString();
        if(content == null || content.length() < 8)
        {
            Toast toast = new Toast(getApplicationContext());
            toast.setText("Please write over 8 character");
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        QueryManager qm = new QueryManager(ma);
        qm.execute("insertStory","user_id",user.user_id,"password",user.password,"title",title,
                "content",content);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        ActivityManager.getActivityManager().popActivity();
    }
}
