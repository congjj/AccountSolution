package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.InitializeComponent( savedInstanceState) ;
    }




    private LoginActivityController mThisController;
    Button mbtnLogin;
    TextView mtxvTelNO;

    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        this.LoadView();

        /**
         * 载入
         */
        mThisController .loginAcivity_Load(savedInstanceState);
        /**
         * 登录按钮单击
         */
        this.mbtnLogin .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mThisController .btnLogin_Click (view);
            }
        }) ;

    }

    private void LoadView()
    {
        //
        //mbtnLogin
        //
        this.mbtnLogin =(Button)findViewById(R.id .btnLogin) ;
        //
        //ntxvTelNO
        //
        mtxvTelNO =(TextView)findViewById(R .id.txvTelNO) ;
        mThisController =new LoginActivityController(LoginActivity.this,this.mbtnLogin ,this.mtxvTelNO) ;
    }


}
