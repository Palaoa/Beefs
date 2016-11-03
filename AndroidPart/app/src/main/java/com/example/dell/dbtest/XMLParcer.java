package com.example.dell.dbtest;

/**
 * Created by dell on 2016/11/2.
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class XMLParcer
{
    public ArrayList<UserModel> parseUser(String string)throws Exception
    {
        ArrayList<UserModel> result = null;
        UserModel model = new UserModel();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");

        ByteArrayInputStream is = new ByteArrayInputStream(string.getBytes());
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(is,"UTF-8");
        int eventType = xpp.getEventType();
        int count = 1;
        //int max = 12;
        while(eventType != XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {
                case XmlPullParser.START_DOCUMENT:
                    result = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if(xpp.getName().equals("string"))
                    {
                        switch (count)
                        {
                            case 1:  // user_id
                                eventType = xpp.next();
                                model.user_id = xpp.getText();
                                count++;
                                break;
                            case 2:  // nickname
                                eventType = xpp.next();
                                model.nickname = xpp.getText();
                                count++;
                                break;
                            case 3:  // slogan
                                eventType = xpp.next();
                                model.slogan = xpp.getText();
                                count++;
                                break;
                            case 4:  // avatar
                                eventType = xpp.next();
                                model.avatar = xpp.getText();
                                count++;
                                break;
                            case 5:  // birthday
                                eventType = xpp.next();
                                model.birthday = new java.util.Date();
                                model.birthday = sdf.parse(xpp.getText());
                                count++;
                                break;
                            case 6:  // occupation
                                eventType = xpp.next();
                                model.slogan = xpp.getText();
                                count++;
                                break;
                            case 7:  // gender
                                eventType = xpp.next();
                                model.gender = xpp.getText().charAt(0);
                                count++;
                                break;
                            case 8:  // address
                                eventType = xpp.next();
                                model.address = xpp.getText();
                                count++;
                                break;
                            case 9:  // grade
                                eventType = xpp.next();
                                model.grade = Integer.parseInt(xpp.getText());
                                count++;
                                break;
                            case 10:  // coin
                                eventType = xpp.next();
                                model.coin = Integer.parseInt(xpp.getText());
                                count++;
                                break;
                            case 11:  // contribution
                                eventType = xpp.next();
                                model.contribution = Integer.parseInt(xpp.getText());
                                count++;
                                break;
                            case 12:  // password
                                eventType = xpp.next();
                                model.password = xpp.getText();
                                count = 0;
                                result.add(model);
                                break;
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = xpp.next();
        }
        return result;

    }
}
