package cn.zgnj.tiexi.shenyang.myaccount;


import android.content.Context;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;


/**
 * Created by Administrator on 2017/9/23.
 */

public class LoginActivityController
{

    Button btnLogin;
    TextView txvTelNO;
    Context loginActivity ;


    private TelephonyManager _TelephInfo;

    public LoginActivityController (Context loginActivity ,Button btnLogin , TextView txvTelNO)
    {
        this.loginActivity  =loginActivity;
        this.btnLogin =btnLogin ;
        this.txvTelNO =txvTelNO;
    }

    /**
     * 载入时发生
     */
    public void loginAcivity_Load(Bundle Send)
    {
        _TelephInfo =(TelephonyManager)loginActivity .getSystemService(Context .TELEPHONY_SERVICE) ;
        String id =_TelephInfo .getLine1Number().trim() .length() ==0 ? _TelephInfo .getSubscriberId()
                :_TelephInfo .getLine1Number() ;
        this.txvTelNO .setText( id) ;



    }

    /**
     * 系统登录按钮
     */
    public void btnLogin_Click(View Send)
    {
        //new USERINFO() ;
        //USERINFO a =  new USERINFO(1, "a","f","f","d");
        //a.save() ;

        IModelHelper a=new USERINFO("","",_TelephInfo .getLine1Number(),_TelephInfo .getSubscriberId(),"","");
        String cc=_TelephInfo .getSubscriberId();
        List <USERINFO > list = USERINFO .find(USERINFO .class ,"SIM_ISMI=?",cc) ;

        long c = a.Insert() ;
       // USERINFO  book = USERINFO.findById(USERINFO .class ,(long)1) ;

        Toast.makeText(loginActivity  , "成功"+ c , Toast.LENGTH_SHORT).show();

    }









}
