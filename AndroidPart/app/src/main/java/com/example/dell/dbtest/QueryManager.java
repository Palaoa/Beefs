package com.example.dell.dbtest;

import android.os.AsyncTask;

import com.example.dell.dbtest.activitys.MyActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dell on 2016/10/31.
 */

public class QueryManager extends AsyncTask<String, Integer, ArrayList<String>>
{
    private  String SERVICE_NS = "http://tempuri.org/";
    private  String SERVICE_URL = "http://121.42.12.186:803/UserService.asmx";
    private  HttpTransportSE ht;
    private  SoapSerializationEnvelope envelope;
    private MyActivity myActivity;
    public QueryManager(MyActivity activity)
    {
        super();
        ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        myActivity = activity;
    }

    @Override
    protected ArrayList<String> doInBackground(String... pair)
    {
        String methodName = pair[0];
        final String SOAP_ACTION = SERVICE_NS + methodName;
        final ArrayList<String> result = new ArrayList<>();
        if(pair.length % 2 != 1)
        {
            result.add("Error");
            return result;
        }
        // method name
        result.add(pair[0]);
        SoapObject soapObject = new SoapObject(SERVICE_NS,methodName);
        for(int i=1,size=pair.length;i<size;i+=2)
        {
            soapObject.addProperty(pair[i],pair[i+1]);
        }
        envelope.bodyOut = soapObject;
        try {
            //调用WebService，调用对象的call()方法，并以SoapSerializationEnvelope作为参数调用远程Web Service
            ht.call(SOAP_ACTION, envelope);
            if (envelope.getResponse() != null) {
                //获取服务器响应返回的SOAP消息，调用完成后，访问SoapSerializationEnvelope对象的bodyIn属性，该属性返回一个
                //SoapObject对象，该对象就代表了Web Service的返回消息。解析该SoapObject对象，即可获取调用Web Service的返回值
                SoapObject so = (SoapObject) envelope.bodyIn;
                result.add(so.getPropertyAsString(0));
                return result;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result)
    {
        myActivity.getResult(result);
    }


    /*public ArrayList<String> query(String methodName, ArrayList<String> pair)
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
    }*/
}
