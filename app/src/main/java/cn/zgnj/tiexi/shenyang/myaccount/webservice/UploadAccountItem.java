package cn.zgnj.tiexi.shenyang.myaccount.webservice;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import junit.framework.Test;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBILL;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTLIST;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Toolkit;

/**
 * Created by CJJ on 2017/12/8.
 */

public class UploadAccountItem  implements Runnable
{
    public interface AfterServiceRunResultListener
    {
        public void RunAfterResult(String methodName,boolean isSuccess,Bundle bundle);
    }
    public AfterServiceRunResultListener SetOnAfterServiceRunResult;




    String SERVICE_NS ;
    String SOAP_ACTION;
    String SERVICE_URL;
    String methodName ;

    public void startService()
    {
        Thread thread=new Thread(this);
        thread.start();
    }

    public Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Bundle bundle =new Bundle() ;
            Bundle rebundle = msg.getData() ;
            String methodName = rebundle .getString("method") ;
            bundle .putString("returninfo",rebundle .getString("returninfo")) ;
            if(msg.what ==0)
            {
                if(SetOnAfterServiceRunResult !=null)
                {
                    SetOnAfterServiceRunResult.RunAfterResult(methodName, false, bundle);
                }
            }
            else if(msg .what ==1)
            {
                if(SetOnAfterServiceRunResult !=null)
                {
                    SetOnAfterServiceRunResult.RunAfterResult(methodName, true, bundle);
                }
            }
            else
            {
                if(SetOnAfterServiceRunResult !=null)
                {
                    SetOnAfterServiceRunResult.RunAfterResult(methodName, false, bundle);
                }
            }
        }
    };

    @Override
    public void run()
    {
        Message msg = new Message();
        Bundle bundle =new Bundle();
        try
        {
            if(this.methodName.equals("Test"))
            {
                if (verify4ConnectRight())
                {
                    msg.what = 1;
                    bundle .putString("returninfo","Success") ;
                }
                else
                {
                    msg.what = 0;
                    bundle .putString("returninfo","Fail") ;
                }
                bundle.putString("method","Test") ;
                msg.setData(bundle) ;
                mHandler.sendMessage(msg);
            }
        }
        catch (IOException e)
        {
            if(this.methodName.equals("Test"))
            {
                bundle.putString("method","Test") ;
                bundle .putString("returninfo",e.getMessage()) ;
            }
            msg.setData(bundle) ;
            mHandler.sendMessage(msg);
        }
        catch (XmlPullParserException e)
        {
            if(this.methodName.equals("Test"))
            {
                bundle.putString("method","Test") ;
                bundle .putString("returninfo",e.getMessage()) ;
            }
            msg.setData(bundle) ;
            mHandler.sendMessage(msg);
        }

    }


    public UploadAccountItem (String serviceUrl)
    {
        SERVICE_NS = "http://bayuquan.cn/";//命名空间
        //SOAP_ACTION = "http://bayuquan.cn/"+methodName;//用来定义消息请求的地址，也就是消息发送到哪个操作
       // SERVICE_URL = "http://172.16.40.189:9981/MyAccount/AccountManager.asmx";//URL地址，这里写发布的网站的本地地址
        this.SERVICE_URL  =serviceUrl;
    }



    public UploadAccountItem (String serverURL,String methodName)
    {
        SERVICE_NS = "http://bayuquan.cn/";//命名空间
        SOAP_ACTION = "http://bayuquan.cn/"+methodName;//用来定义消息请求的地址，也就是消息发送到哪个操作
        SERVICE_URL = serverURL ;//URL地址，这里写发布的网站的本地地址
        this.methodName =methodName ;
    }

    public void RunService(String methodName)
    {
        this.methodName =methodName ;
        SOAP_ACTION =SERVICE_NS + methodName ;
        Thread thread=new Thread(this);
        thread.start();
    }


    public boolean verify4ConnectRight() throws IOException, XmlPullParserException
    {

        //创建HttpTransportSE传输对象，该对象用于调用Web Service操作
        final HttpTransportSE ht = new HttpTransportSE(SERVICE_URL); ;
        ht.debug = true;
        //使用SOAP1.1协议创建Envelop对象。从名称上来看,SoapSerializationEnvelope代表一个SOAP消息封包；但ksoap2-android项目对
        //SoapSerializationEnvelope的处理比较特殊，它是HttpTransportSE调用Web Service时信息的载体--客户端需要传入的参数，需要通过
        //SoapSerializationEnvelope对象的bodyOut属性传给服务器；服务器响应生成的SOAP消息也通过该对象的bodyIn属性来获取。
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //实例化SoapObject对象，创建该对象时需要传入所要调用的Web Service的命名空间、Web Service方法名
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
        //对dotnet webservice协议的支持,如果dotnet的webservice
        envelope.dotNet = true;
        //调用SoapSerializationEnvelope的setOutputSoapObject()方法，或者直接对bodyOut属性赋值，将前两步创建的SoapObject对象设为
        //SoapSerializationEnvelope的付出SOAP消息体
        envelope.bodyOut = soapObject;
        //调用WebService，调用对象的call()方法，并以SoapSerializationEnvelope作为参数调用远程Web Service
        ht.call(SOAP_ACTION, envelope);
        if(envelope.getResponse() != null)
        {
            //获取服务器响应返回的SOAP消息，调用完成后，访问SoapSerializationEnvelope对象的bodyIn属性，该属性返回一个
            //SoapObject对象，该对象就代表了Web Service的返回消息。解析该SoapObject对象，即可获取调用Web Service的返回值
            SoapObject so = (SoapObject) envelope.bodyIn;
            //接下来就是从SoapObject对象中解析响应数据的过程了
            String result = so.getPropertyAsString(0);
            return result .equals("success") ;
        }
        else
        {
            return false ;
        }
    }


    public String  getSererName() throws IOException, XmlPullParserException
    {
        //创建HttpTransportSE传输对象，该对象用于调用Web Service操作
        final HttpTransportSE ht = new HttpTransportSE(SERVICE_URL); ;
        ht.debug = true;
        //使用SOAP1.1协议创建Envelop对象。从名称上来看,SoapSerializationEnvelope代表一个SOAP消息封包；但ksoap2-android项目对
        //SoapSerializationEnvelope的处理比较特殊，它是HttpTransportSE调用Web Service时信息的载体--客户端需要传入的参数，需要通过
        //SoapSerializationEnvelope对象的bodyOut属性传给服务器；服务器响应生成的SOAP消息也通过该对象的bodyIn属性来获取。
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //实例化SoapObject对象，创建该对象时需要传入所要调用的Web Service的命名空间、Web Service方法名
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
        //对dotnet webservice协议的支持,如果dotnet的webservice
        envelope.dotNet = true;
        //调用SoapSerializationEnvelope的setOutputSoapObject()方法，或者直接对bodyOut属性赋值，将前两步创建的SoapObject对象设为
        //SoapSerializationEnvelope的付出SOAP消息体
        envelope.bodyOut = soapObject;
        //调用WebService，调用对象的call()方法，并以SoapSerializationEnvelope作为参数调用远程Web Service
        ht.call(SOAP_ACTION, envelope);
        if(envelope.getResponse() != null)
        {
            //获取服务器响应返回的SOAP消息，调用完成后，访问SoapSerializationEnvelope对象的bodyIn属性，该属性返回一个
            //SoapObject对象，该对象就代表了Web Service的返回消息。解析该SoapObject对象，即可获取调用Web Service的返回值
            SoapObject so = (SoapObject) envelope.bodyIn;
            //接下来就是从SoapObject对象中解析响应数据的过程了
            String result = so.getPropertyAsString(0);
            return result;
        }
        else
        {
            return "" ;
        }
    }


    public String [] uploadBooks4AccountItem(long bookID) throws IOException, XmlPullParserException
    {
        List<String>uploadedaccount=new ArrayList<String>() ;
        ACCOUNTBOOK accountbook =ACCOUNTBOOK .getItSelf(bookID) ;
        //创建HttpTransportSE传输对象，该对象用于调用Web Service操作
        final HttpTransportSE ht = new HttpTransportSE(SERVICE_URL); ;
        ht.debug = true;
        //使用SOAP1.1协议创建Envelop对象。从名称上来看,SoapSerializationEnvelope代表一个SOAP消息封包；但ksoap2-android项目对
        //SoapSerializationEnvelope的处理比较特殊，它是HttpTransportSE调用Web Service时信息的载体--客户端需要传入的参数，需要通过
        //SoapSerializationEnvelope对象的bodyOut属性传给服务器；服务器响应生成的SOAP消息也通过该对象的bodyIn属性来获取。
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //对dotnet webservice协议的支持,如果dotnet的webservice
        envelope.dotNet = true;

        for(ACCOUNTLIST temp:ACCOUNTLIST .GetSomeNoUpload(String .valueOf(bookID)))
        {
            //实例化SoapObject对象，创建该对象时需要传入所要调用的Web Service的命名空间、Web Service方法名
            SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            String uuID =temp .getUUID();
            String bookname =accountbook .getNAME();
            String accountitme=temp.getSubjectName();
            String accountname=temp .getNAME();
            String accountremark=temp .getREMARK();
            String accountdate =sdf.format(temp .getACCOUNTTIME());
            String price =String.valueOf(temp .getPrice());
            String count =String.valueOf(temp .getCount()) ;
            String isout =temp .getIsOut()?"-1":"1";
            String opby=accountbook .getUserInfo() .getSIM_ISMI() ;
            String createby=accountbook .getUserInfo() .getUSERNAME() ;

            soapObject.addProperty("uuID", uuID);
            soapObject.addProperty("bookname",bookname );
            soapObject.addProperty("accountitme", accountitme);
            soapObject.addProperty("accountname", accountname );
            soapObject.addProperty("accountremark", accountremark);
            soapObject.addProperty("accountdate", accountdate);
            soapObject.addProperty("price", price);
            soapObject.addProperty("count", count );
            soapObject.addProperty("isout", isout);
            soapObject.addProperty("kinds", "1");
            soapObject.addProperty("opby", opby );
            soapObject.addProperty("createby", createby);
            soapObject.addProperty("remark", "手机端上传");

            //调用SoapSerializationEnvelope的setOutputSoapObject()方法，或者直接对bodyOut属性赋值，将前两步创建的SoapObject对象设为
            //SoapSerializationEnvelope的付出SOAP消息体
            envelope.bodyOut = soapObject;
            //调用WebService，调用对象的call()方法，并以SoapSerializationEnvelope作为参数调用远程Web Service
            ht.call(SOAP_ACTION, envelope);
            if (envelope.getResponse() != null)
            {
                //获取服务器响应返回的SOAP消息，调用完成后，访问SoapSerializationEnvelope对象的bodyIn属性，该属性返回一个
                //SoapObject对象，该对象就代表了Web Service的返回消息。解析该SoapObject对象，即可获取调用Web Service的返回值
                SoapObject so = (SoapObject) envelope.bodyIn;
                //接下来就是从SoapObject对象中解析响应数据的过程了
                //String result = so.getPropertyAsString(0);
                Object  result = so.getPropertyAsString(0) ;
                if(result .equals("success"))
                {
                    temp .setISUPLOAD(true) ;
                    //temp .save() ;
                    uploadedaccount .add(temp.getUUID()) ;
                }
            }
            else
            {

            }
        }
        String [] ccc= uploadedaccount.toArray(new String[]{});
        return ccc;
    }



    public  int  uploadBillPic(String [] uuIDlist) throws IOException, XmlPullParserException
    {
        int Index =0;

        //创建HttpTransportSE传输对象，该对象用于调用Web Service操作
        final HttpTransportSE ht = new HttpTransportSE(SERVICE_URL); ;
        ht.debug = true;
        //使用SOAP1.1协议创建Envelop对象。从名称上来看,SoapSerializationEnvelope代表一个SOAP消息封包；但ksoap2-android项目对
        //SoapSerializationEnvelope的处理比较特殊，它是HttpTransportSE调用Web Service时信息的载体--客户端需要传入的参数，需要通过
        //SoapSerializationEnvelope对象的bodyOut属性传给服务器；服务器响应生成的SOAP消息也通过该对象的bodyIn属性来获取。
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //对dotnet webservice协议的支持,如果dotnet的webservice
        envelope.dotNet = true;

        for(String uuID : uuIDlist )
        {
            ACCOUNTLIST temp =ACCOUNTLIST.getOne(uuID) ;
            for (ACCOUNTBILL accountbill: temp.getACCOUNTBILL())
            {
                //实例化SoapObject对象，创建该对象时需要传入所要调用的Web Service的命名空间、Web Service方法名
                SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
                String pic = Base64.encode(Toolkit.unGZip(accountbill.getPIC()));
                soapObject.addProperty("uuID", uuID);
                soapObject.addProperty("pic", pic);

                //调用SoapSerializationEnvelope的setOutputSoapObject()方法，或者直接对bodyOut属性赋值，将前两步创建的SoapObject对象设为
                //SoapSerializationEnvelope的付出SOAP消息体
                envelope.bodyOut = soapObject;
                //调用WebService，调用对象的call()方法，并以SoapSerializationEnvelope作为参数调用远程Web Service
                ht.call(SOAP_ACTION, envelope);
                if (envelope.getResponse() != null)
                {
                    //获取服务器响应返回的SOAP消息，调用完成后，访问SoapSerializationEnvelope对象的bodyIn属性，该属性返回一个
                    //SoapObject对象，该对象就代表了Web Service的返回消息。解析该SoapObject对象，即可获取调用Web Service的返回值
                    SoapObject so = (SoapObject) envelope.bodyIn;
                    //接下来就是从SoapObject对象中解析响应数据的过程了
                    //String result = so.getPropertyAsString(0);
                    Object result = so.getPropertyAsString(0);
                    if (result.equals("success"))
                    {
                        accountbill.setISUPLOAD(true);
                        //accountbill .save() ;
                        Index++;
                    }
                }
                else
                {

                }
            }
        }
        return Index ;
    }


}
