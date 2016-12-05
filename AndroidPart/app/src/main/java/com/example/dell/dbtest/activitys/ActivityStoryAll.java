package com.example.dell.dbtest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.dell.dbtest.Models.StoryModel;
import com.example.dell.dbtest.QueryManager;
import com.example.dell.dbtest.R;
import com.example.dell.dbtest.ResultParser;
import com.example.dell.dbtest.adapter.StoryAdapter;
import com.example.dell.dbtest.UserAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/11/5.
 */

public class ActivityStoryAll extends MyActivity
{

    private ListView storyLv;
    private MyActivity ma;
    @Override
    public void getResult(ArrayList<String> result)
    {
        String methodName = result.get(0);
        Intent intent = new Intent();
        try {
            switch (methodName) {
                case "queryUserStory":
                    String str = result.get(1);
                    List<StoryModel> list = ResultParser.parseStory(str);

                    if(list.size() != 0)
                    {
                        //ArrayList<Map<String,Object>> alist = getData(list);
                        storyLv.setAdapter(new StoryAdapter(ma,list));
                        //
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
        name = "ActivityStoryAll";
        setContentView(R.layout.mystory_all_layout);
        ActivityManager.getActivityManager().addActivity(this);
        ma = this;
        storyLv = (ListView)findViewById(R.id.mystorya_story_listview);
        startQuery();
        //storyLv.setAdapter(new StoryAdapter(this,list));

    }

    /*@Override
    protected void onResume()
    {
        super.onResume();
    }*/

    private void updateStory()
    {

    }

    private void startQuery()
    {
        String id = UserAccount.getInstance().getUser().user_id;
        QueryManager qm = new QueryManager(ma);
        qm.execute("queryUserStory","user_id",id);
        return;
    }
    /*
    public ArrayList<Map<String, Object>> getData(List<StoryModel> mList){
        ArrayList<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < mList.size(); i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("title", mList.get(i).title);
            map.put("content", mList.get(i).content);
            list.add(map);
        }
        return list;
    }
    */
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        ActivityManager.getActivityManager().popActivity();
    }
}
