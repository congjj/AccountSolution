package cn.zgnj.tiexi.shenyang.myaccount;


import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;


/**
 * Created by Administrator on 2017/9/23.
 */

public class LoginActivityController
{

    //载入电话号码或ID
    public static void Load(LoginActivity loginActivity)
    {
        TelephonyManager _TelephInfo =getTelephoneManager(loginActivity) ;
        String id =_TelephInfo .getLine1Number().trim() .length() ==0 ? _TelephInfo .getSubscriberId()
                :_TelephInfo .getLine1Number() ;
        loginActivity .gettxvTelNO() .setText( id) ;
    }


    /**
     * 系统登录按钮
     */
    public static void Login(LoginActivity loginActivity)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        IModelHelper a=new USERINFO("","",getTelephoneManager( loginActivity).getLine1Number(),
                getTelephoneManager(loginActivity).getSubscriberId(),"",df.format(new Date()) ,df.format(new Date()) ,"");
        long returnid = a._Insert() ;
        Toast.makeText(loginActivity  , "登录成功-欢迎试用", Toast.LENGTH_LONG ).show();

        Intent i=new Intent(loginActivity ,OperateActivity.class);
        i.putExtra("sendUserID",returnid);
        loginActivity . startActivity(i);
    }




    private static TelephonyManager getTelephoneManager(LoginActivity loginActivity)
    {
        return (TelephonyManager)loginActivity .getSystemService(Context .TELEPHONY_SERVICE) ;
    }



}
