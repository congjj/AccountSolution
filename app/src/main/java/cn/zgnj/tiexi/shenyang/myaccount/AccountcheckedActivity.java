package cn.zgnj.tiexi.shenyang.myaccount;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cjj.tiexi.shenyang.library.qrcode.activity.CaptureActivity;
import cjj.tiexi.shenyang.library.qrcode.activity.CodeUtils;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;

import cn.zgnj.tiexi.shenyang.myaccount.webservice.*;

public class AccountcheckedActivity extends AppCompatActivity
{
    public static final int REQUEST_CODE = 111;

    String result;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    Toast.makeText(AccountcheckedActivity.this, "连接服务器失败", Toast.LENGTH_LONG).show();
                case 1:
                    Toast.makeText(AccountcheckedActivity.this, result, Toast.LENGTH_LONG).show();
                default:
                    break;
            }
        }
    };

    private ACCOUNTBOOK mACCOUNTBOOK ;
    private void Load(Intent intent, Bundle savedInstanceState)
    {
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("sendBookID");
        mACCOUNTBOOK = ACCOUNTBOOK.getItSelf(bundle.getLong("book_ID"));

    }


    private void CheckAccountItem(View v)
    {
        Intent intent = new Intent(AccountcheckedActivity.this, CaptureActivity.class);
        startActivityForResult(intent,REQUEST_CODE);





//        UploadAccountItem webser=new UploadAccountItem("Test") ;
//        //创建HttpTransportSE传输对象，该对象用于调用Web Service操作
//        final HttpTransportSE ht = webser .getHttpTransportSE();
//        ht.debug = true;
//
//        final SoapSerializationEnvelope envelope =webser .getSoapSerializationEnvelope() ;
//        //实例化SoapObject对象，创建该对象时需要传入所要调用的Web Service的命名空间、Web Service方法名
//        SoapObject soapObject = webser .getSoapObject() ;
//        //对dotnet webservice协议的支持,如果dotnet的webservice
//        envelope.dotNet = true;
//        //调用SoapSerializationEnvelope的setOutputSoapObject()方法，或者直接对bodyOut属性赋值，将前两步创建的SoapObject对象设为
//        //SoapSerializationEnvelope的付出SOAP消息体
//        envelope.bodyOut = soapObject;
//        final String SOAP_ACTION = "http://tempuri.org/Test";
//
//        new Thread(){
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
//                        result = so.getPropertyAsString(0);
//                        Message msg = new Message();
//                        msg.what = 1;
//                        handler.sendMessage(msg);
//                    }
//                    else
//                    {
//                        Message msg=new Message();
//                        msg.what=0;
//                        handler.sendMessage(msg);
//                    }
//                }
//                catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//                catch (XmlPullParserException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE)
        {
            //处理扫描结果（在界面上显示）
            if (null != data)
            {
                Bundle bundle = data.getExtras();
                if (bundle == null)
                {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE)
                        == CodeUtils.RESULT_SUCCESS)
                {
                    String result1 =
                            bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(AccountcheckedActivity.this,
                            result1 , Toast.LENGTH_LONG).show();
//                    //用默认浏览器打开扫描得到的地址
//                    Intent intent = new Intent();
//                    intent.setAction("android.intent.action.VIEW");
//                    Uri content_url = Uri.parse(result.toString());
//                    intent.setData(content_url);
//                    startActivity(intent);
                }
                else if (bundle.getInt(CodeUtils.RESULT_TYPE)
                        == CodeUtils.RESULT_FAILED)
                {
                    Toast.makeText(AccountcheckedActivity.this,
                            "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


















    Button mButtonItemCheck;
    ImageView mImageViewItemShow;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountchecked);
        this.InitializeComponent(savedInstanceState);
    }

    private void InitializeComponent(Bundle savedInstanceState)
    {
        //载入控件
        this.LoadView();

        //载入时发生
        Load(getIntent(), savedInstanceState);
        //控件事件
        SetListener();
    }

    private void LoadView()
    {
        mImageViewItemShow =(ImageView )findViewById(R.id .ivItemShow) ;
        mButtonItemCheck =(Button)findViewById(R.id.btnItemCheckOp) ;
    }

    private void SetListener()
    {

        this.mButtonItemCheck .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckAccountItem(v);
            }
        }) ;
    }




}
