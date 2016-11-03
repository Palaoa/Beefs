package com.example.dell.dbtest;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.dell.dbtest.QueryManager;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import com.example.dell.dbtest.ActivityManager;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private View loginView;
    private String result;

    private String xml = "<ArrayOfString xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://tempuri.org/\">\n" +
            "<string>0001</string>\n" +
            "<string>Test1</string>\n" +
            "<string>Hello</string>\n" +
            "<string/>\n" +
            "<string>1996/1/1 0:00:00</string>\n" +
            "<string>test</string>\n" +
            "<string>M</string>\n" +
            "<string>ShangHai</string>\n" +
            "<string>0</string>\n" +
            "<string>0</string>\n" +
            "<string>0</string>\n" +
            "<string>0001</string>\n" +
            "</ArrayOfString>";


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    Toast.makeText(MainActivity.this, "连接服务器失败", Toast.LENGTH_LONG).show();
                case 1:
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.getActivityManager().addActivity(this);
        btn1 = (Button)findViewById(R.id.main_test_btn);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                ArrayList<String> pair = new ArrayList<String>();
                QueryManager.getQueryManager().query("selectAllUserInfo",pair);

            }
        });
        loginView = getLayoutInflater().inflate(R.layout.login_layout,null);
        btn2 = (Button)findViewById(R.id.main_2login_btn);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(getApplicationContext(),"com.example.dell.dbtest.ActivityLogin");
                startActivity(intent);
            }
        });
    }

    /*private void BtnClick() {

        final  String SERVICE_NS = "http://tempuri.org/";//命名空间
        final  String SERVICE_URL = "http://121.42.12.186:803/UserService.asmx";//URL地址，这里写发布的网站的本地地址
        String methodName = "selectAllUserInfo";
        final  String SOAP_ACTION = SERVICE_NS+methodName;

        final HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
        ht.debug = true;
        //使用SOAP1.1协议创建Envelop对象。从名称上来看,SoapSerializationEnvelope代表一个SOAP消息封包；但ksoap2-android项目对
        //SoapSerializationEnvelope的处理比较特殊，它是HttpTransportSE调用Web Service时信息的载体--客户端需要传入的参数，需要通过
        //SoapSerializationEnvelope对象的bodyOut属性传给服务器；服务器响应生成的SOAP消息也通过该对象的bodyIn属性来获取。
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //实例化SoapObject对象，创建该对象时需要传入所要调用的Web Service的命名空间、Web Service方法名
        SoapObject soapObject = new SoapObject(SERVICE_NS,methodName);
        //soapObject.addProperty("theRegionCode","上海");
        envelope.dotNet = true;
        //调用SoapSerializationEnvelope的setOutputSoapObject()方法，或者直接对bodyOut属性赋值，将前两步创建的SoapObject对象设为
        //SoapSerializationEnvelope的付出SOAP消息体
        envelope.bodyOut = soapObject;

        new Thread(){
            @Override
            public void run() {
                try {
                    //调用WebService，调用对象的call()方法，并以SoapSerializationEnvelope作为参数调用远程Web Service
                    ht.call(SOAP_ACTION, envelope);
                    if(envelope.getResponse() != null){
                        //获取服务器响应返回的SOAP消息，调用完成后，访问SoapSerializationEnvelope对象的bodyIn属性，该属性返回一个
                        //SoapObject对象，该对象就代表了Web Service的返回消息。解析该SoapObject对象，即可获取调用Web Service的返回值
                        SoapObject so = (SoapObject) envelope.bodyIn;

                        result = so.getPropertyAsString(0);
                        Message msg = Message.obtain();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                    else{
                        Message msg=new Message();
                        msg.what=0;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }*/

}
