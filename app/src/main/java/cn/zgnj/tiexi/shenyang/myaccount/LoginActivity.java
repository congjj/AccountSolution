package cn.zgnj.tiexi.shenyang.myaccount;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import cn.zgnj.tiexi.shenyang.myaccount.utility.* ;

import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;

public class LoginActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.InitializeComponent(savedInstanceState);
    }

    //region description 初始化

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
    }

    int MY_PERMISSION_REQUEST_CODE=1000;
    private Button mbtnLogin;
    private TextView mtxvTelNO;
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
    }
    //endregion

    private TelephonyManager _TelephInfo;
    private void Load(Bundle savedInstanceState)
    {
//        try
//        {
//            if(new PermissionsChecker(this).apllyPermissions(Manifest.permission.READ_PHONE_STATE ))
//            {
        ActivityCompat.requestPermissions(
                this,
                new String[] { Manifest.permission.READ_PHONE_STATE} ,
                MY_PERMISSION_REQUEST_CODE);

                _TelephInfo = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String idb = _TelephInfo.getSubscriberId();
                String ida = _TelephInfo.getLine1Number();
                String id = _TelephInfo.getLine1Number().trim().length() == 0 ? _TelephInfo.getSubscriberId()
                        : _TelephInfo.getLine1Number();
                mtxvTelNO.setText(id);
//            }
//        }
//        catch (Exception ex)
//        {
//            throw ex;
//        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
//            boolean isAllGranted = true;
//
//            // 判断是否所有的权限都已经授予了
//            for (int grant : grantResults) {
//                if (grant != PackageManager.PERMISSION_GRANTED) {
//                    isAllGranted = false;
//                    break;
//                }
//            }
//
//            if (isAllGranted) {
//                // 如果所有的权限都授予了, 则执行备份代码
//
//
//            } else {
//                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
//
//            }
//        }
//    }











    private void Login(View view)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        IModelHelper a = new USERINFO("", "", _TelephInfo.getLine1Number(),
                _TelephInfo.getSubscriberId(), "", df.format(new Date()), df.format(new Date()), "");
        long returnid = a._Insert();
        Toast.makeText(this, "登录成功-欢迎试用", Toast.LENGTH_LONG).show();

        Intent i = new Intent(this, OperateActivity.class);
        i.putExtra("sendUserID", returnid);
        startActivity(i);
    }



}
