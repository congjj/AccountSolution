package cn.zgnj.tiexi.shenyang.myaccount.networkedition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.zgnj.tiexi.shenyang.myaccount.R;

public class SettingsysserverActivity extends AppCompatActivity
{

    private void Load(Intent intent, Bundle savedInstanceState)
    {
        String a = "";

    }


    private void SetServer(View v)
    {

    }

    private void ScanQRCode(View v)
    {

    }









    // region description  初始化

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsysserver);
        this.InitializeComponent(savedInstanceState);
    }

    Button mButtonScanCode;
    Button mButtonSetServer;
    TextView mTextViewServerName;
    EditText mEditTextServerUrl;

    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        this.LoadView();
        //载入时发生
        Load(getIntent(), savedInstanceState);
    }

    private void LoadView()
    {
        mButtonScanCode =(Button)findViewById(R.id.btnScanQRcode) ;
        mButtonSetServer =(Button)findViewById(R.id.btnServerSet) ;
        mButtonSetServer .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ScanQRCode(v);
            }
        }) ;
        mButtonScanCode .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SetServer(v);
            }
        }) ;
        mTextViewServerName =(TextView)findViewById(R.id.txtServerName) ;
        mEditTextServerUrl =(EditText )findViewById(R.id.etxtServerUrl) ;
    }


//endregion


}
