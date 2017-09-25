package cn.zgnj.tiexi.shenyang.myaccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.InitializeComponent() ;
    }



    Button mbtnLogin;

    private void InitializeComponent()
    {
        //
        /// mbtnLogin
        //
        this.mbtnLogin =(Button)findViewById(R.id .btnLogin) ;
        this.mbtnLogin .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new LoginActivityController(LoginActivity.this,view).btnLogin_Click();
            }
        }) ;
    }








}
