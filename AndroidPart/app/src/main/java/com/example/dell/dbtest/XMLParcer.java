package com.example.dell.dbtest;

/**
 * Created by dell on 2016/11/2.
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.example.dell.dbtest.Models.UserModel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParcer
{
    // Hen Sha Bi De Fang Fa!!!
    public static ArrayList<UserModel> parseUser(InputStream inStream)throws Exception
    {
        int count = 1;
        //  int max = 12;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");

        ArrayList<UserModel> result = new ArrayList<>();
        UserModel model = new UserModel();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inStream);
        Element root = document.getDocumentElement();
        NodeList nodes = root.getElementsByTagName("Table");
        for(int i = 0 ;i < nodes.getLength();i++)
        {
            Element element = (Element)nodes.item(i);
            NodeList nl = element.getChildNodes();
            for (int j = 0 ;j < nl.getLength() ;j++)
            {
                Node childNode = nl.item(j);
                if(childNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element childElement = (Element)childNode;
                    switch(count)
                    {
                        case 1:  // user_id
                            model.user_id = childElement.getFirstChild().getNodeValue();
                            count++;
                            break;
                        case 2:  // nickname
                            model.nickname = childElement.getFirstChild().getNodeValue();
                            count++;
                            break;
                        case 3:  // slogan
                            model.slogan = childElement.getFirstChild().getNodeValue();
                            count++;
                            break;
                        case 4:  // avatar
                            model.avatar = childElement.getFirstChild().getNodeValue();
                            count++;
                            break;
                        case 5:  // birthday
                            model.birthday = new java.util.Date();
                            model.birthday = sdf.parse(childElement.getFirstChild().getNodeValue());
                            count++;
                            break;
                        case 6:  // occupation
                            model.occupation = childElement.getFirstChild().getNodeValue();
                            count++;
                            break;
                        case 7:  // gender
                            model.gender = childElement.getFirstChild().getNodeValue().charAt(0);
                            count++;
                            break;
                        case 8:  // address
                            model.address = childElement.getFirstChild().getNodeValue();
                            count++;
                            break;
                        case 9:  // grade
                            model.grade = Integer.parseInt(childElement.getFirstChild().getNodeValue());
                            count++;
                            break;
                        case 10:  // coin
                            model.coin = Integer.parseInt(childElement.getFirstChild().getNodeValue());
                            count++;
                            break;
                        case 11:  // contribution
                            model.contribution = Integer.parseInt(childElement.getFirstChild().getNodeValue());
                            count++;
                            break;
                        case 12:  // password
                            model.password = childElement.getFirstChild().getNodeValue();
                            count = 1;
                            result.add(model);
                            break;
                    }
                }
            }
        }
        return result;
    }
}






/*public static ArrayList<UserModel> parseUser(String string)throws Exception
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
                            /*case 1:  // user_id
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
                                model.occupation = xpp.getText();
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

    }*/