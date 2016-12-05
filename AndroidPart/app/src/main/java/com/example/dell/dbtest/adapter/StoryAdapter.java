package com.example.dell.dbtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.dell.dbtest.Models.StoryModel;
import com.example.dell.dbtest.R;

import java.util.List;

/**
 * Created by dell on 2016/11/20.
 */

public class StoryAdapter extends BaseAdapter {

    //private List<Map<String, Object>> data;
    List<StoryModel> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public StoryAdapter(Context context,List<StoryModel> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }
    public final class MyComponent
    {
        public TextView titleTxt;
        public Button okBtn;
    }
    @Override
    public int getCount()
    {
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        MyComponent component;
        if(convertView == null)
        {
            component = new MyComponent();
            convertView = layoutInflater.inflate(R.layout.storylist,null);
            component.okBtn = (Button)convertView.findViewById(R.id.storylist_view_btn);
            component.titleTxt = (TextView)convertView.findViewById(R.id.storylist_title_txt);
            convertView.setTag(component);
        }
        else
            component = (MyComponent) convertView.getTag();
        component.titleTxt.setText(data.get(position).title);
        component.okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * ！！！！！！还要加listener！！！！！！！
                * */
                Intent intent = new Intent();
                intent.setClassName(context,"com.example.dell.dbtest.activitys.ActivityStoryOne");
                Bundle bundle = new Bundle();
                bundle.putSerializable("StoryModel",data.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
