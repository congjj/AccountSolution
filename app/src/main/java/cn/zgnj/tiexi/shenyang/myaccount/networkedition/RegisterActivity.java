package cn.zgnj.tiexi.shenyang.myaccount.networkedition;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import cjj.tiexi.shenyang.library.messageutility.DialogResult;
import cjj.tiexi.shenyang.library.messageutility.MessageDialog;
import cjj.tiexi.shenyang.library.security.MD5;
import cjj.tiexi.shenyang.library.webservice.WebServiceHelper;
import cjj.tiexi.shenyang.library.xloading.xloading;
import cn.zgnj.tiexi.shenyang.myaccount.R;
import cn.zgnj.tiexi.shenyang.myaccount.model.SYSCONFIG;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Toolkit;

public class RegisterActivity extends AppCompatActivity
{
    private void Load(Intent intent, Bundle savedInstanceState)
    {
        Bundle bundle = intent.getBundleExtra("RegisterActivity");
        mTextViewUserID .setText(bundle.getString("userid"));
    }


    private void mButtonReg_Click(View v)
    {
        if( !verify() )
        {
            return ;
        }
        String password =mEditTextPass1 .getText() .toString();
        try
        {
            password  = MD5 .getMD5(mEditTextPass1 .getText() .toString());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        String na = Toolkit.SERVERNAMESPACE ;
        String me = "CreateUser";
        String weburl = SYSCONFIG.getWEBURL() ;
        WebServiceHelper serviceHelper =WebServiceHelper .getInstance(weburl ,na ) ;

        String code =mTextViewUserID .getText() .toString();
        String name =mEditTextName .getText().toString();

        Map <String ,Object >map =new HashMap<String,  Object >() ;
        map .put("code",code) ;
        map .put("name",name) ;
        map.put("password",password) ;
        map .put("flag",2) ;
        final Dialog dialog = xloading.showWaitDialog(this,"用户注册中……",false ,false );
        serviceHelper .RunService(me,map) ;
        serviceHelper.SetOnAfterRunService =new WebServiceHelper.AfterRunServicListener()
        {
            @Override
            public void RunService_After(String s, SoapObject soapObject)
            {
                xloading .closeDialog(dialog) ;
                String result = soapObject .getPropertyAsString(0) ;
                if(result.equals("success"))
                {
                    new MessageDialog(RegisterActivity .this).Show("信息","恭喜，注册成功！", DialogResult.DialogIcon .Information ) ;
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,result, Toast.LENGTH_LONG ).show();
                }
            }
        };

    }























    private boolean  verify()
    {
        if(mEditTextName .getText() .toString() .trim() .length() ==0)
        {
            Toast.makeText(this,"注册姓名必须填写！", Toast.LENGTH_LONG ).show();
            return false ;
        }
        if(mEditTextPass1 .getText() .toString() .trim() .length() ==0)
        {
            Toast.makeText(this,"密码不能为空！", Toast.LENGTH_LONG ).show();
            return false ;
        }
        if(mEditTextPass2 .getText() .toString() .trim() .length() ==0)
        {
            Toast.makeText(this,"密码不能为空！", Toast.LENGTH_LONG ).show();
            return false ;
        }
        if(!mEditTextPass1.getText() .toString() .equals(mEditTextPass2 .getText() .toString() ))
        {
            Toast.makeText(this,"两次密码必须相等！", Toast.LENGTH_LONG ).show();
            return false ;
        }
        return true;
    }








    //region description 初始化

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.InitializeComponent(savedInstanceState);
    }

    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        this.LoadView();
        //载入时发生
        Load(getIntent(), savedInstanceState);
    }


    TextView mTextViewUserID;
    Button mButtonReg;
    EditText mEditTextName;
    EditText mEditTextPass1;
    EditText mEditTextPass2;

    private void LoadView()
    {
        this.mTextViewUserID =(TextView )findViewById(R.id.txtregUserID) ;
        mButtonReg =(Button )findViewById(R.id.btnreguser ) ;
        mEditTextName =(EditText) findViewById(R.id.etxtRegusername) ;
        mEditTextPass1 =(EditText)  findViewById(R.id .etxtPassword1 ) ;
        mEditTextPass2 =(EditText)  findViewById(R.id.etxtPassword2) ;
        mButtonReg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mButtonReg_Click(v);
            }
        }) ;
        
    }




    //endregion


}
