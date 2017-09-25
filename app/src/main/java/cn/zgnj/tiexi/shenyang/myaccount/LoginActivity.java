package cn.zgnj.tiexi.shenyang.myaccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.InitializeComponent(savedInstanceState) ;
    }



    Button mbtnLogin;
    TextView mtxvTelNO;

    private void InitializeComponent(final Bundle savedInstanceState)
    {
        //
        ///Load
        //
        //
        new LoginActivityController(LoginActivity .this,null,savedInstanceState) .loginAcivity_Load();

        /// mbtnLogin
        //
        this.mbtnLogin =(Button)findViewById(R.id .btnLogin) ;
        this.mbtnLogin .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new LoginActivityController(LoginActivity.this,view,savedInstanceState).btnLogin_Click();
            }
        }) ;
        //
        //ntxvTelNO
        //
        mtxvTelNO =(TextView)findViewById(R .id.txvTelNO) ;
    }








}
