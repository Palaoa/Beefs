package com.example.dell.dbtest.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.dell.dbtest.HouseAdapter;
import com.example.dell.dbtest.Models.HouseModel;
import com.example.dell.dbtest.Models.StoryModel;
import com.example.dell.dbtest.QueryManager;
import com.example.dell.dbtest.R;
import com.example.dell.dbtest.ResultParser;
import com.example.dell.dbtest.StoryAdapter;
import com.example.dell.dbtest.UserAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/11/15.
 */

public class ActivityMyHouseAll extends MyActivity
{
    private ListView houseLv;
    private MyActivity ma;
    @Override
    public void getResult(ArrayList<String> result)
    {
        String methodName = result.get(0);
        Intent intent = new Intent();
        try {
            switch (methodName) {
                case "queryUserHouse":
                    String str = result.get(1);
                    List<HouseModel> list = ResultParser.parseHouse(str);

                    if(list.size() != 0)
                    {
                        //ArrayList<Map<String,Object>> alist = getData(list);
                        houseLv.setAdapter(new HouseAdapter(ma,list));
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
        name = "ActivityMyHouseAll";
        setContentView(R.layout.myhouse_all_layout);
        ActivityManager.getActivityManager().addActivity(this);
        ma = this;
        houseLv = (ListView)findViewById(R.id.mystorya_story_listview);
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
        qm.execute("queryUserHouse","user_id",id);
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
