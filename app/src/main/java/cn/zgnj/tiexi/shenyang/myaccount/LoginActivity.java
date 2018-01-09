package cn.zgnj.tiexi.shenyang.myaccount;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cjj.tiexi.shenyang.library.messageutility.DialogResult;
import cjj.tiexi.shenyang.library.messageutility.MessageDialog;
import cjj.tiexi.shenyang.library.security.MD5;
import cjj.tiexi.shenyang.library.webservice.WebServiceHelper;
import cn.zgnj.tiexi.shenyang.myaccount.model.SYSCONFIG;
import cn.zgnj.tiexi.shenyang.myaccount.utility.*;

import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;

public class LoginActivity extends AppCompatActivity
{

    private TelephonyManager _TelephInfo;
    private USERINFO userinfo;

    private void Load(Bundle savedInstanceState)
    {
//        //无权限是设置权限
//        if (new PermissionsChecker(this).lacksPermissions(Manifest.permission.READ_PHONE_STATE))
//        {
//            //设置权限
//            Permissionhelper.startActivityForResult(this, READ_PHONE_REQUEST_CODE, Manifest.permission.READ_PHONE_STATE);
//        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            new MessageDialog(this) .Show("错误","获取本机识别码失败！") ;
            finish() ;
        }
        _TelephInfo = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String simcode = _TelephInfo.getSubscriberId();
        mtxvTelNO.setText(simcode);
        userinfo = USERINFO.getOne(simcode);
        localLogin();
        mRadioButtonLocal.setChecked(true);
    }

    private void webLogin()
    {
        this.mEditTextUserName.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        mTextViewName.setText(R.string.password);
        this.mEditTextUserName.setEnabled(true);
        this.mEditTextUserName.setText("");
    }

    private void localLogin()
    {
        this.mEditTextUserName.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
        mTextViewName.setText(R.string.name);
        if (userinfo == null)
        {
            this.mEditTextUserName.setText("");
        } else
        {
            this.mEditTextUserName.setText(userinfo.getUSERNAME());
            this.mEditTextUserName.setEnabled(false);
        }
    }

    //用户设置权限后的操作
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == READ_PHONE_REQUEST_CODE && resultCode == Permissionhelper.PERMISSIONS_DENIED)
        {
            finish();
        }
        if (resultCode == Permissionhelper.PERMISSIONS_DENIED)
        {
            finish();
        }

    }

    private void Login(View view)
    {
        try
        {
//            //无权限是设置权限
//            if (new PermissionsChecker(this).lacksPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE))
//            {
//                //设置权限
//                Permissionhelper.startActivityForResult(this, READ_PHONE_REQUEST_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            }

            if (mRadioButtonWeb.isChecked())
            {
                boolean a = SYSCONFIG .addWebUrlConfig("http://172.16.40.189:9981/MyAccount/AccountManager.asmx") ;
                String weburl = SYSCONFIG .getWEBURL() ;
                if(weburl .trim() .length() ==0)
                {

                }
                        //"http://172.16.40.189:9981/MyAccount/AccountManager.asmx";
                String na = "http://bayuquan.cn/";
                String me = "Test";
                WebServiceHelper serviceHelper =WebServiceHelper .getInstance(weburl ,na ) ;
                SoapObject soapObject =serviceHelper .callSoapObject(me,null) ;

                new MessageDialog(this) .Show("错误","获取本机识别码失败或访问本机存储失败！", DialogResult.DialogIcon .Error ) ;
            }
            else
            {
                String username = this.mEditTextUserName.getText().toString();
                if (username.trim().length() == 0)
                {
                    Toast.makeText(this, "请填写姓名！", Toast.LENGTH_LONG).show();
                    return;
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                {
                    new MessageDialog(this) .Show("错误","获取本机识别码失败或访问本机存储失败！", DialogResult.DialogIcon .Error ) ;
                    finish() ;
                }
                IModelHelper a = new USERINFO("", username, _TelephInfo.getLine1Number(), _TelephInfo.getSubscriberId(),
                        "", df.format(new Date()), df.format(new Date()), "", "", "");
                long returnid = a._Insert();
                Toast.makeText(this, "登录成功-欢迎试用", Toast.LENGTH_LONG).show();

                Intent i = new Intent(this, DoaccountActivity.class);
                i.putExtra("sendUserID", returnid);
                startActivity(i);
            }
        }
        catch (Exception ex)
        {
            try
            {
                Toast.makeText(this, ex.getMessage() , Toast.LENGTH_LONG).show();
                throw ex;
            }
            catch (NoSuchAlgorithmException e)
            {
                Toast.makeText(this, e.getMessage() , Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (Exception e)
            {
                Toast.makeText(this, e.getMessage() , Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }




    //region description 初始化
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //-------让 webservice 强制在 Main UI 主线程中运行！！！不建议使用
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //====================================================================================
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.InitializeComponent(savedInstanceState);
    }

    private final  int READ_PHONE_REQUEST_CODE=1000;
    private Button mbtnLogin;
    private TextView mtxvTelNO;
    private EditText mEditTextUserName;
    private RadioButton mRadioButtonLocal;
    private RadioButton mRadioButtonWeb;
    private TextView mTextViewName;

    private void LoadView()
    {
        //
        //mbtnLogin
        //
        this.mbtnLogin = (Button) findViewById(R.id.btnLogin);
        //
        //ntxvTelNO
        //
        mtxvTelNO = (TextView) findViewById(R.id.txvTelNO);
        mEditTextUserName =(EditText )findViewById(R.id.etxtUserName) ;

        mRadioButtonLocal =(RadioButton) findViewById(R.id.radioButton2) ;
        mRadioButtonWeb =(RadioButton)  findViewById(R.id.radioButton) ;
        mTextViewName =(TextView) findViewById(R.id.textView11) ;
        ConstraintLayout myLayout = (ConstraintLayout) findViewById(R.id.backConstraintLayout);
        myLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.mintcream));
    }

    private void InitializeComponent(Bundle savedInstanceState)
    {
        //载入View
        this.LoadView();
        //载入电话或ID
        Load(savedInstanceState);
        //登录按钮单击
        this.mbtnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Login(view);

            }
        });

        this.mRadioButtonLocal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                mRadioButtonWeb .setChecked(!isChecked ) ;
                if(isChecked )
                {
                    localLogin();
                }
            }
        }) ;

        this.mRadioButtonWeb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
               mRadioButtonLocal .setChecked(!isChecked) ;
               if(isChecked)
               {
                   webLogin();
               }
            }
        }) ;
    }


    //endregion


}
