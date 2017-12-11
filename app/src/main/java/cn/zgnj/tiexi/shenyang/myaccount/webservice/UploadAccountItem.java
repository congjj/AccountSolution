package cn.zgnj.tiexi.shenyang.myaccount.webservice;

import android.os.Message;

import junit.framework.Test;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;

/**
 * Created by CJJ on 2017/12/8.
 */

public class UploadAccountItem
{
    String SERVICE_NS ;
    String SOAP_ACTION;
    String SERVICE_URL;
    String methodName ;

    public UploadAccountItem (String methodName)
    {
        SERVICE_NS = "http://tempuri.org/";//命名空间
        SOAP_ACTION = "http://tempuri.org/"+methodName;//用来定义消息请求的地址，也就是消息发送到哪个操作
        SERVICE_URL = "http://172.16.40.189:9981/MyAccount/AccountManager.asmx";//URL地址，这里写发布的网站的本地地址
        this.methodName =methodName ;
    }

    public HttpTransportSE getHttpTransportSE()
    {
        HttpTransportSE ht =  new HttpTransportSE(SERVICE_URL);
        return ht;
    }

    public SoapSerializationEnvelope getSoapSerializationEnvelope()
    {
        SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //envelope .dotNet =true;
        return envelope ;
    }

    public SoapObject getSoapObject()
    {

        return new SoapObject(SERVICE_NS, methodName);
    }

    private void setConnect4WebService()
    {

        //创建HttpTransportSE传输对象，该对象用于调用Web Service操作
        final HttpTransportSE ht = this.getHttpTransportSE() ;
        ht.debug = true;
        //使用SOAP1.1协议创建Envelop对象。从名称上来看,SoapSerializationEnvelope代表一个SOAP消息封包；但ksoap2-android项目对
        //SoapSerializationEnvelope的处理比较特殊，它是HttpTransportSE调用Web Service时信息的载体--客户端需要传入的参数，需要通过
        //SoapSerializationEnvelope对象的bodyOut属性传给服务器；服务器响应生成的SOAP消息也通过该对象的bodyIn属性来获取。
        final SoapSerializationEnvelope envelope = getSoapSerializationEnvelope() ;
        //实例化SoapObject对象，创建该对象时需要传入所要调用的Web Service的命名空间、Web Service方法名
        SoapObject soapObject = getSoapObject() ;
        //对dotnet webservice协议的支持,如果dotnet的webservice
        envelope.dotNet = true;
        //调用SoapSerializationEnvelope的setOutputSoapObject()方法，或者直接对bodyOut属性赋值，将前两步创建的SoapObject对象设为
        //SoapSerializationEnvelope的付出SOAP消息体
        envelope.bodyOut = soapObject;

//        new Thread()
//        {
//            @Override
//            public void run()
//            {
//                try
//                {
//                    //调用WebService，调用对象的call()方法，并以SoapSerializationEnvelope作为参数调用远程Web Service
//                    ht.call(SOAP_ACTION, envelope);
//                    if(envelope.getResponse() != null){
//                        //获取服务器响应返回的SOAP消息，调用完成后，访问SoapSerializationEnvelope对象的bodyIn属性，该属性返回一个
//                        //SoapObject对象，该对象就代表了Web Service的返回消息。解析该SoapObject对象，即可获取调用Web Service的返回值
//                        SoapObject so = (SoapObject) envelope.bodyIn;
//                        //接下来就是从SoapObject对象中解析响应数据的过程了
//                        String result = so.getPropertyAsString(0);
//                        Message msg = new Message();
//                        msg.what = 1;
//                        //handler.sendMessage(msg);
//                    }
//                    else
//                    {
//                        Message msg=new Message();
//                        msg.what=0;
//                       //handler.sendMessage(msg);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (XmlPullParserException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

    }


}
