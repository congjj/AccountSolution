package cn.zgnj.tiexi.shenyang.myaccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OperateActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate);

        this.InitializeComponent( savedInstanceState) ;



        long id = getIntent() .getLongExtra("data",-1) ;
        //Toast.makeText(OperateActivity.class , "成功"+ id , Toast.LENGTH_LONG ).show();
    }


    Button mbtnOperate;
    TextView mtxvOperatet;



    private OperateActivityController  mThisController;


    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        this.LoadView();

        /**
         * 载入
         */
        mThisController .operateAcivity_Load(savedInstanceState);

    }



    private void LoadView()
    {
        //
        //mbtnOperate;
        //
        this.mbtnOperate =(Button)findViewById(R.id .btnOperate ) ;
        //
        //ntxvTelNO
        //
        this.mtxvOperatet =(TextView)findViewById(R .id.txvOperate) ;
        mThisController =new OperateActivityController(OperateActivity.this,this .mbtnOperate ,this.mtxvOperatet) ;
    }


}
