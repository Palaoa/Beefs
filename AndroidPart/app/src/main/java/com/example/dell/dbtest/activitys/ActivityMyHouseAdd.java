package com.example.dell.dbtest.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dell.dbtest.Models.HouseModel;
import com.example.dell.dbtest.R;

import java.util.ArrayList;

/**
 * Created by dell on 2016/11/15.
 */

public class ActivityMyHouseAdd extends MyActivity
{
    private Button btnOK;
    private EditText edtName, edtAddr, edtInfo, edtLimit;
    private Spinner spinCity;
    @Override
    public void getResult(ArrayList<String> result)
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = "ActivityMyHouseAll";
        setContentView(R.layout.myhouse_add_layout);
        ActivityManager.getActivityManager().addActivity(this);
        btnOK = (Button)findViewById(R.id.myhousead_ok_btn);
        edtName = (EditText)findViewById(R.id.myhousead_name_edt);
        edtAddr = (EditText)findViewById(R.id.myhousead_address_edt);
        edtInfo = (EditText)findViewById(R.id.myhousead_info_edt);
        edtLimit = (EditText)findViewById(R.id.myhousead_limit_edt);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName = edtName.getText().toString();
                String mAddr = edtAddr.getText().toString();
                String mLimit = edtLimit.getText().toString();
                String mInfo = edtInfo.getText().toString();
                String mCity = spinCity.getSelectedItem().toString();
                HouseModel hm;
                while(mName != "" && mAddr != "" && mLimit != "" && mInfo != "" && mCity != "")
                {
                    hm = new HouseModel();

                }
            }
        });
    }
}
