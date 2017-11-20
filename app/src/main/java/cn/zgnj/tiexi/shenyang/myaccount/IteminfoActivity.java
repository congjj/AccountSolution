package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTLIST;

public class IteminfoActivity extends AppCompatActivity
{


    private void Load(Intent intent, Bundle savedInstanceState)
    {
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("accountItem");
        String UUID = bundle.getString("accountItem_UUID") ;
        ACCOUNTLIST accountItem=ACCOUNTLIST .getOne(UUID) ;
        List< byte []> aa=accountItem .getBills() ;
    }
    
    
    
    

    
    
    
    




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iteminfo);
        this.InitializeComponent(savedInstanceState);
    }

    private void InitializeComponent(Bundle savedInstanceState)
    {
        //载入控件
        this.LoadView();

        //载入时发生
        Load(getIntent(), savedInstanceState);
        //控件事件
        this.SetListener();
    }


    private void LoadView()
    {
    }


    private void SetListener()
    {
    }

}
