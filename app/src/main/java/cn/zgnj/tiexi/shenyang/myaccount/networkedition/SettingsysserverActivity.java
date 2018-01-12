package cn.zgnj.tiexi.shenyang.myaccount.networkedition;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.serialization.SoapObject;

import cjj.tiexi.shenyang.library.messageutility.DialogResult;
import cjj.tiexi.shenyang.library.messageutility.MessageDialog;
import cjj.tiexi.shenyang.library.qrcode.activity.CaptureActivity;
import cjj.tiexi.shenyang.library.qrcode.activity.CodeUtils;
import cjj.tiexi.shenyang.library.webservice.WebServiceHelper;
import cjj.tiexi.shenyang.library.xloading.xloading;
import cn.zgnj.tiexi.shenyang.myaccount.R;
import cn.zgnj.tiexi.shenyang.myaccount.model.SYSCONFIG;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Toolkit;


public class SettingsysserverActivity extends AppCompatActivity
{

    private void Load(Intent intent, Bundle savedInstanceState)
    {
    }

    private void setServer_Click (View v)
    {
        Dialog dialog = null;
        try
        {
            String na = Toolkit .SERVERNAMESPACE ;
            String me = "Test";
            final String weburl =mEditTextServerUrl .getText().toString() ;
            dialog = xloading.showWaitDialog(this,"验证服务器中……",false ,false );
            final WebServiceHelper serviceHelper =WebServiceHelper .getInstance(weburl ,na ) ;
            serviceHelper .RunService(me,null);
            serviceHelper .SetOnAfterRunService =new WebServiceHelper.AfterRunServicListener()
            {
                @Override
                public void RunService_After(String s, SoapObject soapObject)
                {
                    if(soapObject ==null)
                    {
                        new MessageDialog(SettingsysserverActivity.this).Show("连接错误","服务器连接失败！！", DialogResult.DialogIcon.Error) ;
                        return;
                    }
                    else
                    {
                        if( SYSCONFIG.addWebUrlConfig(weburl))
                        {
                            mEditTextServerUrl .setEnabled(false) ;
                            try
                            {
                                SoapObject aa = serviceHelper .callSoapObject("GetServerName",null) ;
                                mTextViewServerName .setText("服务器名："+aa.getProperty(0).toString()) ;
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            };
        }
        catch (Exception e)
        {
            new MessageDialog(this).Show("连接错误"+e.getMessage(),"服务器连接失败！！", DialogResult.DialogIcon.Error) ;
        }
        finally
        {
            xloading .closeDialog(dialog) ;
        }
    }

    private void scanQRCode_After(String qrcode)
    {
        if(!qrcode .equals("") )
        {
            mEditTextServerUrl.setText(qrcode);
        }
    }








    // region description  初始化

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsysserver);
        this.InitializeComponent(savedInstanceState);
    }

    Button mButtonScanCode;
    Button mButtonSetServer;
    TextView mTextViewServerName;
    EditText mEditTextServerUrl;

    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        this.LoadView();
        //载入时发生
        Load(getIntent(), savedInstanceState);
    }

    private void LoadView()
    {
        mButtonScanCode =(Button)findViewById(R.id.btnScanQRcode) ;
        mButtonSetServer =(Button)findViewById(R.id.btnServerSet) ;
        mButtonSetServer .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setServer_Click(v);
            }
        }) ;
        mButtonScanCode .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SettingsysserverActivity.this, CaptureActivity.class);
                startActivityForResult(intent,1000);
            }
        }) ;
        mTextViewServerName =(TextView)findViewById(R.id.txtServerName) ;
        mEditTextServerUrl =(EditText )findViewById(R.id.etxtServerUrl) ;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == 1000)
        {
            //处理扫描结果（在界面上显示）
            if (null != data)
            {
                Bundle bundle = data.getExtras();
                if (bundle == null)
                {
                    scanQRCode_After("");
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS)
                {
                    scanQRCode_After( bundle.getString(CodeUtils.RESULT_STRING));
                }
                else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED)
                {
                    scanQRCode_After("");
                }
            }
            else
            {
                scanQRCode_After("");
            }
        }
    }


//endregion


}
