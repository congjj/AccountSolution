package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AccountreportActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountreport);
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

    //空间加载
    private void LoadView()
    {
    }


    //载入时发生
    private void Load(Intent intent, Bundle savedInstanceState)
    {

    }



}
