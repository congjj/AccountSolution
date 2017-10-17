package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.* ;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Toolkit;

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

    //region description 初始化

    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        this.LoadView();
        /**
         * 载入
         */
        Load(savedInstanceState );
        /**
         * 生成账簿
         */
        this.mBtnCreateBook .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ShowBookTypeView(view);
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
                CreateSubjct(view);
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
                StartAccount(view);
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
                CreateBookType(view);
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
                HideBookTypeView(view);
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
                BookTypeListItemSelected(adapterView ,view,i,l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        }) ;
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
    //endregion



    private void Load(Bundle savedInstanceState)
    {
        long id = getIntent().getLongExtra("sendUserID",-1) ;
        mPnlCreateBookType.setVisibility(View.INVISIBLE) ;
        loadBookTypelist(USERINFO.getOne(id).getACCOUNTBOOKList()) ;

    }

    private void CreateSubjct(View view)
    {
        ACCOUNTBOOK book = (ACCOUNTBOOK) mCmbBookTypeList.getSelectedItem() ;
        if(book==null)
        {
            Toast.makeText(this, "请选择账簿或创建一个账簿！", Toast.LENGTH_LONG ).show();
            return ;
        }
        Intent i=new Intent(this,SubjectActivity .class );
        Bundle bundle = new Bundle() ;
        bundle.putString("name",book .getNAME()) ;
        bundle .putString("remark",book .getREMARK()) ;
        bundle .putLong("book_ID",book .getId()) ;
        i.putExtra("sendBookType",bundle);
        startActivity(i);
    }

    private void CreateBookType(View view)
    {
        long id = getIntent().getLongExtra("sendUserID",-1) ;
        USERINFO userinfo =USERINFO.getOne(id);
        IModelHelper book=new ACCOUNTBOOK(userinfo, Toolkit.replaceBlank(mEdtBookName.getText().toString().toUpperCase()),
               Toolkit .replaceBlank(mEdtBookRemark.getText().toString())) ;
        if( book._Insert()==-1)
        {
            Toast.makeText(this,"记账簿名称：" + mEdtBookName.getText()+" 重复，请换用其它名称！", Toast.LENGTH_LONG ).show();
        }
        loadBookTypelist(userinfo.getACCOUNTBOOKList()) ;
        doSuccess() ;
    }

    private  void loadBookTypelist(List<ACCOUNTBOOK > booklist)
    {
        ArrayAdapter<ACCOUNTBOOK> adp=new ArrayAdapter<ACCOUNTBOOK>(this , R.layout.support_simple_spinner_dropdown_item,booklist);
        mCmbBookTypeList .setAdapter(adp);
    }

    private void ShowBookTypeView(View view)
    {
        mPnlCreateBookType.setVisibility(View.VISIBLE) ;
    }
    private void HideBookTypeView(View view)
    {
        doSuccess() ;
        mPnlCreateBookType .setVisibility(View.INVISIBLE) ;
    }

    private void StartAccount(View view)
    {
        ACCOUNTBOOK book = (ACCOUNTBOOK)mCmbBookTypeList.getSelectedItem() ;
        if(book==null)
        {
            Toast.makeText(this  , "请选择账簿或创建一个账簿！", Toast.LENGTH_LONG ).show();
            return ;
        }
        Intent i=new Intent(this  ,AccountActivity.class );
        Bundle bundle = new Bundle() ;
        bundle.putString("name",book .getNAME()) ;
        bundle .putString("remark",book .getREMARK()) ;
        bundle .putLong("book_ID",book .getId()) ;
        i.putExtra("sendBookType",bundle);
        startActivity(i);
    }
    private void BookTypeListItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
    }

    void doSuccess()
    {
        mEdtBookName.setText("") ;
        mEdtBookRemark.setText("") ;
    }





}
