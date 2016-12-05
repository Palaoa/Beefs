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

import com.example.dell.dbtest.Models.HouseModel;
import com.example.dell.dbtest.R;

import java.util.List;

/**
 * Created by dell on 2016/12/1.
 */

public class HouseAdapter extends BaseAdapter
{

    List<HouseModel> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public HouseAdapter(Context context,List<HouseModel> data){
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
        HouseAdapter.MyComponent component;
        if(convertView == null)
        {
            component = new HouseAdapter.MyComponent();
            convertView = layoutInflater.inflate(R.layout.storylist,null);
            component.okBtn = (Button)convertView.findViewById(R.id.houselist_view_btn);
            component.titleTxt = (TextView)convertView.findViewById(R.id.houselist_title_txt);
            convertView.setTag(component);
        }
        else
            component = (HouseAdapter.MyComponent) convertView.getTag();
        component.titleTxt.setText(data.get(position).address);
        component.okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * ！！！！！！还要加listener！！！！！！！
                * */
                Intent intent = new Intent();
                intent.setClassName(context,"com.example.dell.dbtest.activitys.ActivityHouseOne");
                Bundle bundle = new Bundle();
                bundle.putSerializable("HouseModel",data.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

}
