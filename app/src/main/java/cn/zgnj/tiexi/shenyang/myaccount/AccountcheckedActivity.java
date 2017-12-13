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
