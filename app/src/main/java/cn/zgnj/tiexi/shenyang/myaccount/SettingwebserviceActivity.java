package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingwebserviceActivity extends AppCompatActivity
{



    //载入是发生
    private void Load(Intent intent, Bundle savedInstanceState)
    {

    }

    private void btnScanQRcode_Click(View v)
    {

    }





    // region description 初始化

    Button mButtonScanQRcode;
    TextView mTextViewShowServerName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingwebservice);
        this.InitializeComponent(savedInstanceState);
    }

    private void InitializeComponent(Bundle savedInstanceState)
    {
        //载入控件
        this.LoadView();
        //载入时发生
        Load(getIntent(), savedInstanceState);
    }

    private void LoadView()
    {
        mButtonScanQRcode =(Button)findViewById(R .id .btnScanQRcode) ;
        this.mButtonScanQRcode .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnScanQRcode_Click(v);
            }
        }) ;
        mTextViewShowServerName =(TextView )findViewById(R.id .txtShowServerName ) ;
    }



    //endregion


}
