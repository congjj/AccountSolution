package cn.zgnj.tiexi.shenyang.myaccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
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
        /**
         * 生成账簿
         */
        this.mbtnCreateBook .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mThisController.btnCreateBook_Click(view);
            }
        }) ;
        /**
         * 生成记账科目
         */
        this.mbtnCreateSubject .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mThisController .btnCreateSubject_Click(view);
            }
        }) ;
        /**
         * 开始记账
         */
        this.mbtnAccount .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mThisController .btnAccount_Click(view);
            }
        }) ;
    }


    Button mbtnCreateBook;
    Button mbtnCreateSubject;
    Button mbtnAccount;
    TextView mtxvOperatet;

    private OperateActivityController  mThisController;

    private void LoadView()
    {
        //
        //mbtnCreateBook;
        //
        this.mbtnCreateBook  =(Button)findViewById(R.id.btnCreateBook) ;
        //
        //mbtnCreateSubject
        //
        this.mbtnCreateSubject =(Button) findViewById(R.id.btnCreateSubject) ;
        //
        //mbtnAccount
        //
        this.mbtnAccount =(Button) findViewById(R.id .btnAccount) ;
        //
        //ntxvTelNO
        //
        this.mtxvOperatet =(TextView)findViewById(R .id.txvBook) ;
        mThisController =new OperateActivityController(OperateActivity.this,this.mtxvOperatet ,this.mbtnCreateBook,this.mbtnCreateSubject ,
                this.mbtnAccount ) ;

    }


}
