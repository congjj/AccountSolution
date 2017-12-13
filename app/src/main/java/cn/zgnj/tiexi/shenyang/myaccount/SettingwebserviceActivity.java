package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import cjj.tiexi.shenyang.library.qrcode.activity.CaptureActivity;
import cjj.tiexi.shenyang.library.qrcode.activity.CodeUtils;
import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;
import cn.zgnj.tiexi.shenyang.myaccount.webservice.UploadAccountItem;

public class SettingwebserviceActivity extends AppCompatActivity
{

    public static final int REQUEST_CODE = 111;
    private long userID;


    //载入是发生
    private void Load(Intent intent, Bundle savedInstanceState)
    {
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("scanQRcode");
        userID =bundle .getLong("user_ID") ;
    }

    private void btnScanQRcode_Click(View v)
    {
        Intent intent = new Intent(this, CaptureActivity.class);
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
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS)
                {
                    final String serUrl = bundle.getString(CodeUtils.RESULT_STRING);
                    final UploadAccountItem webser=new UploadAccountItem(serUrl ,"GetServerName");
                    new Thread()
                    {
                        @Override
                        public void run()
                        {
                            Message msg = new Message();

                            try
                            {
                                String sername = webser .getSererName() ;
                                if(!sername .equals(""))
                                {
                                    msg.what = 1;
                                    Bundle bundle = new Bundle();
                                    bundle .putString("servername",sername) ;
                                    bundle .putLong("userid",userID) ;
                                    bundle .putString("serUrl",serUrl );
                                    msg.setData(bundle) ;

                                    handler.sendMessage(msg);
                                }
                                else
                                {
                                    msg.what = 0;
                                    handler.sendMessage(msg);
                                }
                            }
                            catch (IOException e)
                            {
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                            catch (XmlPullParserException e)
                            {
                                msg.what = 0;
                                handler.sendMessage(msg);
                            }
                        }
                    }.start();


//                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//                    //用默认浏览器打开扫描得到的地址
//                    Intent intent = new Intent();
//                    intent.setAction("android.intent.action.VIEW");
//                    Uri content_url = Uri.parse(result.toString());
//                    intent.setData(content_url);
//                    startActivity(intent);
                }
                else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED)
                {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what ==0)
            {
                Toast.makeText(SettingwebserviceActivity.this, "连接服务器失败", Toast.LENGTH_LONG).show();
            }
            else if(msg.what ==1)
            {
                Bundle bundle =msg .getData() ;
                String servername= bundle.getString("servername") ;
                String sererurl=bundle .getString("serUrl") ;
                long userid=bundle .getLong("userid") ;
                USERINFO userinfo =USERINFO .getOne(userid) ;
                userinfo .setSERVERNAME(servername ) ;
                userinfo .setSERVERURL(sererurl) ;
                userinfo.save() ;
                mTextViewConnInfo .setText("恭喜你已经成功连接到：") ;
                mTextViewShowServerName .setText("【"+servername+"】") ;
            }
            else
            {}
        }
    };




























    // region description 初始化

    Button mButtonScanQRcode;
    TextView mTextViewShowServerName;
    TextView mTextViewConnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingwebservice);
        this.InitializeComponent(savedInstanceState);
    }

    private void InitializeComponent(Bundle savedInstanceState)
    {
        //载入控件
        this.LoadView();
        //载入时发生
        Load(getIntent(), savedInstanceState);
    }

    private void LoadView()
    {
        mButtonScanQRcode =(Button)findViewById(R .id .btnScanQRcode) ;
        this.mButtonScanQRcode .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnScanQRcode_Click(v);
            }
        }) ;
        mTextViewShowServerName =(TextView )findViewById(R.id .txtShowServerName ) ;
        mTextViewConnInfo =(TextView )findViewById(R.id .txtConnInfo) ;
    }



    //endregion


}
