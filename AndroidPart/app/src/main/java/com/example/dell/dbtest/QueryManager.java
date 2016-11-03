package com.example.dell.dbtest;

import android.app.Service;
import android.os.Handler;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/**
 * Created by dell on 2016/10/31.
 */

public class QueryManager extends Service
{
    private static String SERVICE_NS = "http://tempuri.org/";
    private static String SERVICE_URL = "http://121.42.12.186:803/UserService.asmx";
    private static HttpTransportSE ht;
    private static SoapSerializationEnvelope envelope;
    private static QueryManager instance;
    private static Handler handler;


    /*private QueryManager()
    {
        ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {

            }
        };

    }*/

    public static QueryManager getQueryManager()
    {
        if(instance == null)
            instance = new QueryManager();
        return instance;
    }

    public ArrayList<String> query(String methodName, ArrayList<String> pair)
    {
        final String SOAP_ACTION = SERVICE_NS + methodName;
        final ArrayList<String> result = new ArrayList<>();
        if(pair.size() % 2 != 0)
        {
            result.add("Error");
            return result;
        }

        SoapObject soapObject = new SoapObject(SERVICE_NS,methodName);
        // add properties
        for(int i=0,size=pair.size()/2;i<size;i++)
        {
            soapObject.addProperty(pair.get(i),pair.get(i+1));
        }
        envelope.bodyOut = soapObject;
        new Thread() {
            @Override
            public void run()
            {
                try {
                    //调用WebService，调用对象的call()方法，并以SoapSerializationEnvelope作为参数调用远程Web Service
                    ht.call(SOAP_ACTION, envelope);
                    if (envelope.getResponse() != null) {
                        //获取服务器响应返回的SOAP消息，调用完成后，访问SoapSerializationEnvelope对象的bodyIn属性，该属性返回一个
                        //SoapObject对象，该对象就代表了Web Service的返回消息。解析该SoapObject对象，即可获取调用Web Service的返回值
                        SoapObject so = (SoapObject) envelope.bodyIn;
                        result.add(so.getPropertyAsString(0));
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

            }
        }.start();
        return result;
    }
}
