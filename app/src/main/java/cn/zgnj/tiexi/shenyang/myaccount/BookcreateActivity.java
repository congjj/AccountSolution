package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Toolkit;

public class BookcreateActivity extends AppCompatActivity
{
    private long User_ID;
    public static  final int SUCCESS=1000;
    public static  final int FAIL=-1000;

    private void Load(Intent intent, Bundle savedInstanceState)
    {
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("sendBookType");
        User_ID = bundle.getLong("user_ID");
        loadBookTypelist(USERINFO.getOne(User_ID).getACCOUNTBOOKList()) ;
    }


    private void CreateBook(View v)
    {
        USERINFO userinfo =USERINFO.getOne(User_ID);
        String bookname = Toolkit.replaceBlank(mEdtBookName.getText().toString().toUpperCase());
        if(bookname .trim() .length() ==0)
        {
            Toast.makeText(this,"必须填写记账簿的名称！", Toast.LENGTH_LONG ).show();
            return ;
        }
        IModelHelper book=new ACCOUNTBOOK(userinfo,bookname , Toolkit .replaceBlank(mEdtBookRemark.getText().toString())) ;
        if( book._Insert()==-1)
        {
            Toast.makeText(this,"记账簿名称：" + mEdtBookName.getText()+" 重复，请换用其它名称！", Toast.LENGTH_LONG ).show();
        }
        loadBookTypelist(userinfo.getACCOUNTBOOKList()) ;
        doSuccess() ;
    }


    private void Activity_Closing()
    {
        Intent i=new Intent( );
        Bundle bundle = new Bundle() ;
        if(mSpinnerBooklist .getCount() ==0)
        {
            i.putExtra("booklistid",bundle);
            setResult(FAIL ,i) ;
        }
        else
        {
            bundle.putString("index",String.valueOf(mSpinnerBooklist .getSelectedItemId())) ;
            i.putExtra("booklistid",bundle);
            setResult(SUCCESS ,i) ;
        }
    }


    private void loadBookTypelist(List<ACCOUNTBOOK> accountbookList)
    {
        ArrayAdapter<ACCOUNTBOOK> adp=new ArrayAdapter<ACCOUNTBOOK>(this , R.layout.support_simple_spinner_dropdown_item,accountbookList);
        mSpinnerBooklist .setAdapter(adp);
    }


    private void doSuccess()
    {
        mEdtBookName.setText("") ;
        mEdtBookRemark.setText("") ;
    }









    // region description  初始化

    private Button mButtonExit;
    private Button mButtonCreate;
    private Spinner mSpinnerBooklist;
    private EditText mEdtBookName;
    private EditText mEdtBookRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookcreate);
        this.InitializeComponent(savedInstanceState);
    }

    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        LoadView();
        //载入时发生
        Load(getIntent(), savedInstanceState);

        mButtonExit .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Activity_Closing() ;
                finish() ;
            }
        }) ;

        mButtonCreate .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateBook(v);
            }
        }) ;
    }

    private void LoadView()
    {
        mButtonExit =(Button)findViewById(R.id .btnExit_new) ;
        mButtonCreate  =(Button)findViewById(R.id .btnBookType_new) ;
        mSpinnerBooklist =(Spinner)findViewById(R.id.cmbBookTypeList_new) ;
        mEdtBookName =(EditText) findViewById(R .id .edtBookName_new) ;
        mEdtBookRemark =(EditText) findViewById(R.id .edtBookRemark_new) ;
    }

    @Override
    public void onBackPressed()
    {
        Activity_Closing();
        super.onBackPressed();
    }

    //endregion

}
