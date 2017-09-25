package cn.zgnj.tiexi.shenyang.myaccount;


import android.content.Context;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;


/**
 * Created by Administrator on 2017/9/23.
 */

public class LoginActivityController extends LoginActivity
{
    private View mView;
    private Context mContext;
    private Bundle mBundle;
    private TelephonyManager _TelephInfo;

    public LoginActivityController (Context context , View view,Bundle bundle)
    {
        mView =view ;
        mContext =context;
        mBundle =bundle;
    }

    /**
     * 载入时发生
     */
    public void loginAcivity_Load()
    {
       // _TelephInfo =(TelephonyManager)getSystemService(Context .TELEPHONY_SERVICE) ;

        //this.mtxvTelNO .setText( _TelephInfo .getLine1Number() ) ;
    }

    /**
     * 系统登录按钮
     */
    public void btnLogin_Click()
    {
        //new USERINFO() ;
        //USERINFO a =  new USERINFO(1, "a","f","f","d");
        //a.save() ;

       // USERINFO  book = USERINFO.findById(USERINFO .class ,(long)1) ;
        Toast.makeText(mContext , "成功", Toast.LENGTH_SHORT).show();

    }









}
