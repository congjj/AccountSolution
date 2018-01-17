package cn.zgnj.tiexi.shenyang.myaccount;

import android.Manifest;
import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import org.ksoap2.serialization.SoapObject;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import cjj.tiexi.shenyang.library.messageutility.DialogResult;
import cjj.tiexi.shenyang.library.messageutility.MessageDialog;
import cjj.tiexi.shenyang.library.security.MD5;
import cjj.tiexi.shenyang.library.webservice.WebServiceHelper;
import cjj.tiexi.shenyang.library.xloading.xloading;
import cn.zgnj.tiexi.shenyang.myaccount.model.SYSCONFIG;
import cn.zgnj.tiexi.shenyang.myaccount.networkedition.RegisterActivity;
import cn.zgnj.tiexi.shenyang.myaccount.networkedition.SettingsysserverActivity;
import cn.zgnj.tiexi.shenyang.myaccount.utility.*;

import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;

public class LoginActivity extends AppCompatActivity
{

    WebServiceHelper serviceHelper;

    private TelephonyManager _TelephInfo;
    private USERINFO userinfo;

    private void Load(Bundle savedInstanceState)
    {
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

        String na = Toolkit .SERVERNAMESPACE ;
        String me = "Test";
        String weburl = SYSCONFIG.getWEBURL() ;
        Dialog dialog = null;
        try
        {
            dialog = xloading.showWaitDialog(this,"连接网络版服务器……",false ,false );
            serviceHelper =WebServiceHelper .getInstance(weburl ,na ) ;
            serviceHelper.RunService(me,null) ;
            serviceHelper .SetOnAfterRunService =new WebServiceHelper.AfterRunServicListener()
            {
                @Override
                public void RunService_After(String s, SoapObject soapObject)
                {
                    if(soapObject ==null)
                    {
                        setWebServiceUrl() ;
                    }
                    else
                    {
                        try
                        {
                            SoapObject aa=serviceHelper .callSoapObject("GetServerName",null) ;
                            mLinearLayoutServerName.setVisibility(View.VISIBLE) ;
                            mTextViewServerName .setText(aa.getProperty(0).toString()) ;
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }
        catch (Exception ex)
        {
            setWebServiceUrl() ;
        }
        finally
        {
            xloading .closeDialog(dialog) ;
        }
    }


    private void localLogin()
    {
        this.mEditTextUserName.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
        this.mLinearLayoutServerName .setVisibility(View.GONE ) ;
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



    private void Login(View view)
    {
        try
        {
            if (mRadioButtonWeb.isChecked())
            {
               // boolean a = SYSCONFIG .addWebUrlConfig("http://172.16.40.189:9981/MyAccount/AccountManager.asmx") ;
                String na = Toolkit .SERVERNAMESPACE ;
                String me = "LoginSystem_Mobile";
                String weburl = SYSCONFIG .getWEBURL() ;
                final Dialog dialog ;
                if( mEditTextUserName.getText() .toString() .length() ==0)
                {
                    new MessageDialog(this) .Show("错误","请输入登录的密码！", DialogResult.DialogIcon .Error);
                    return ;
                }
                try
                {
                    dialog = xloading.showWaitDialog(this,"系统登录中……",false ,false );
                    WebServiceHelper serviceHelper =WebServiceHelper .getInstance(weburl ,na ) ;
                    String code = mtxvTelNO .getText() .toString() ;
                    String password = mEditTextUserName.getText() .toString() ;
                    Map <String ,Object > map = new HashMap<String,  Object >() ;
                    map.put("code",code) ;
                    map.put("password",MD5 .getMD5(password));
                    map .put("flog",2) ;
                    serviceHelper.RunService(me,map) ;
                    serviceHelper .SetOnAfterRunService =new WebServiceHelper.AfterRunServicListener()
                    {
                        @Override
                        public void RunService_After(String s, SoapObject soapObject)
                        {
                            xloading .closeDialog(dialog) ;
                            if(soapObject ==null)
                            {
                                setWebServiceUrl();
                            }
                            else
                            {
                                String result = soapObject.getPropertyAsString(0);
                                if (result.equals("无此用户名"))
                                {
                                    MessageDialog messageDialog =new MessageDialog(LoginActivity.this);
                                    messageDialog.Show("用户ID无效", "是否注册？”是“注册，”否“忽略！", DialogResult.DialogButton.YESNO, DialogResult.DialogIcon.Ask);
                                    messageDialog .SetOnAfterPressButtonListener =new MessageDialog.DialogButtonPressInterface()
                                    {
                                        @Override
                                        public void ButtonPress_After(DialogResult.Result result)
                                        {
                                            if(DialogResult.Result.YES ==result)
                                            {
                                                Intent i=new Intent(LoginActivity.this  ,RegisterActivity.class );
                                                Bundle bundle = new Bundle() ;
                                                bundle .putString("userid",mtxvTelNO .getText() .toString()) ;
                                                i.putExtra("RegisterActivity",bundle);
                                                startActivityForResult(i,2000) ;
                                            }
                                        }
                                    };
                                }
                                else
                                {
                                    if(result .equals("密码错误！"))
                                    {
                                        Toast.makeText(LoginActivity .this, result, Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {

                                    }
                                }
                            }
                        }
                    };

                }
                catch (Exception ex)
                {
                    setWebServiceUrl() ;
                }

                        //"http://172.16.40.189:9981/MyAccount/AccountManager.asmx";
//                new MessageDialog(this) .Show("错误","获取本机识别码失败或访问本机存储失败！", DialogResult.DialogIcon .Error ) ;
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
            catch (Exception e)
            {
                Toast.makeText(this, e.getMessage() , Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }


    void setWebServiceUrl()
    {
        Intent i=new Intent(this  ,SettingsysserverActivity.class );
        Bundle bundle = new Bundle() ;
        i.putExtra("SettingsysserverActivity",bundle);
        startActivityForResult(i,3000) ;
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
    private TextView mTextViewServerName;
    private LinearLayout mLinearLayoutServerName;

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
        mTextViewServerName =(TextView)  findViewById(R.id.txtServerName) ;
        mLinearLayoutServerName =(LinearLayout)  findViewById(R.id.linServerName) ;
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
        if(requestCode ==2000)
        {
            webLogin();
        }
        if(requestCode ==3000)
        {
            mRadioButtonLocal .setChecked(true) ;
        }
    }
    //endregion


}
