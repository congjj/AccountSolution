package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.zgnj.tiexi.shenyang.myaccount.model.* ;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;

public class OperateActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate);
        this.InitializeComponent( savedInstanceState) ;

        //long id = getIntent() .getLongExtra("data",-1) ;
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
        OperateActivityController.Load(OperateActivity .this);
        /**
         * 生成账簿
         */
        this.mBtnCreateBook .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                OperateActivityController .ShowBookTypeView(OperateActivity .this);
            }
        }) ;
        /**
         * 生成记账科目
         */
        this.mBtnCreateSubject .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                OperateActivityController .CreateSubjct(OperateActivity.this);
            }
        }) ;
        /**
         * 开始记账
         */
        this.mBtnAccount .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                OperateActivityController . StartAccount(view);
            }
        }) ;
        /**
         * 创建一个记账簿
         */
        this.mBtnBookType .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                OperateActivityController.CreateBookType(OperateActivity.this);
            }
        }) ;
        /**
         * 退出账簿操作
         */
        this.mBtnExit .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                OperateActivityController .HideBookTypeView(OperateActivity .this);
            }
        }) ;
        /**
         *AccountBook 选择时发生
         */
        this.mCmbBookTypeList .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                OperateActivityController.BookTypeListItemSelected(adapterView ,view,i,l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        }) ;
    }

    private void LoadView()
    {

        this.mBtnCreateBook  =(Button)findViewById(R.id.btnCreateBook) ;
        //
        //mbtnCreateSubject
        //
        this.mBtnCreateSubject =(Button) findViewById(R.id.btnCreateSubject) ;
        //
        //mbtnAccount
        //
        this.mBtnAccount =(Button) findViewById(R.id .btnAccount) ;
        //
        //ntxvTelNO
        //
        this.mTxvOperatet =(TextView)findViewById(R .id.txvBook) ;
        //
        //mcmbBookTypeList
        //
        this.mCmbBookTypeList =(Spinner)findViewById(R.id .cmbBookTypeList) ;
        //
        //mpnlCreateBookType
        //
        this.mPnlCreateBookType =(ConstraintLayout)  findViewById(R .id .pnlCreateBookType) ;
        //
        //mbtnBookType;
        //
        this.mBtnBookType =(Button)findViewById(R.id .btnBookType) ;
        //
        //mbtnExit
        //
        this.mBtnExit =(Button)  findViewById(R.id .btnExit) ;
        //
        //medtBookName
        //
        this.mEdtBookName =(EditText) findViewById(R .id .edtBookName) ;
        //
        //medtBookRemark
        //
        this.mEdtBookRemark =(EditText) findViewById(R.id .edtBookRemark ) ;


    }


    private Button mBtnCreateBook;
    private Button mBtnCreateSubject;
    private Button mBtnAccount;
    private Button mBtnBookType;
    private Button mBtnExit;
    private EditText mEdtBookName;
    private EditText mEdtBookRemark;
    private TextView mTxvOperatet;
    private Spinner mCmbBookTypeList;
    private ConstraintLayout mPnlCreateBookType;


    public Button getBtnCreateBook()
    {
        return mBtnCreateBook;
    }

    public Button getBtnCreateSubject()
    {
        return mBtnCreateSubject;
    }

    public Button getBtnAccount()
    {
        return mBtnAccount;
    }

    public Button getBtnExit()
    {
        return mBtnExit;
    }

    public Button getBtnBookType()
    {
        return mBtnBookType;
    }
    public EditText getEdtBookName()
    {
        return mEdtBookName;
    }
    public EditText getEdtBookRemark()
    {
        return mEdtBookRemark;
    }
    public ConstraintLayout getPnlCreateBookType()
    {
        return mPnlCreateBookType;
    }
    public TextView getTxvOperatet()
    {
        return mTxvOperatet;
    }
    public Spinner getCmbBookTypeList()
    {
        return mCmbBookTypeList;
    }



}
