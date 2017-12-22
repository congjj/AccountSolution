package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTSUBJECT;
import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;
import cn.zgnj.tiexi.shenyang.myaccount.utility.DateSelected;

public class DoaccountActivity extends AppCompatActivity
{

    private final int RETURN_CREATE_ACCOUNTSUBJECT=100;
    private final int RETURN_CREATE_ACCOUNTBOOK=200;
    private long mUserID;
    private ACCOUNTBOOK mACCOUNTBOOK ;
    private ACCOUNTSUBJECT mACCOUNTSUBJECT;


    private void Load(Intent intent, Bundle savedInstanceState)
    {
        mUserID = getIntent().getLongExtra("sendUserID",-1) ;
        loadBookTypelist(USERINFO.getOne(mUserID).getACCOUNTBOOKList()) ;
    }

    private void CreateIitem(View v)
    {
    }

    private void StartCamera(View v)
    {
    }

    private void ShowAccountReport(View v)
    {
    }

    private void UploadAccount_Click(View v)
    {
    }

    private void CreateAccountSubject()
    {
        if(mACCOUNTBOOK ==null)
        {
            Toast.makeText(this, "请选择账簿或创建一个账簿！", Toast.LENGTH_LONG).show();
            return;
        }
        Intent i=new Intent(this,SubjectActivity .class );
        Bundle bundle = new Bundle() ;
        bundle.putString("name",mACCOUNTBOOK .getNAME()) ;
        bundle .putString("remark",mACCOUNTBOOK .getREMARK()) ;
        bundle .putLong("book_ID",mACCOUNTBOOK  .getId()) ;
        i.putExtra("sendBookType",bundle);
        startActivityForResult(i,RETURN_CREATE_ACCOUNTSUBJECT) ;
        //startActivity(i);
    }

    private void AccountSubjectCreate_Closed(Intent data)
    {
        List<ACCOUNTSUBJECT> list = ACCOUNTSUBJECT.getList4Book(mACCOUNTBOOK .getId());
        loadBookSubjectlist(list);
    }

    private void CreateAccountbook()
    {
        Intent i=new Intent(this,BookcreateActivity.class );
        Bundle bundle = new Bundle() ;
        bundle .putLong("user_ID",mUserID) ;
        i.putExtra("sendBookType",bundle);
        startActivityForResult(i,RETURN_CREATE_ACCOUNTBOOK) ;
    }

    private void AccountBookCreate_Closed(Intent data)
    {
        Intent a =data ;
    }

    private void AccountBook_ItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        ACCOUNTBOOK accountbook =(ACCOUNTBOOK)  this.mSpinnerTitle .getSelectedItem() ;
        if(accountbook ==null)
            return ;
        mACCOUNTBOOK =accountbook ;
        List<ACCOUNTSUBJECT> list = ACCOUNTSUBJECT.getList4Book(mACCOUNTBOOK .getId());
        loadBookSubjectlist(list);
    }

    private void BookSubject_ItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        ACCOUNTSUBJECT accountsubject =(ACCOUNTSUBJECT )this.mSpnSubject .getSelectedItem() ;
        if(accountsubject ==null)
            return ;
        mACCOUNTSUBJECT  =accountsubject ;
    }

    private  void loadBookTypelist(List<ACCOUNTBOOK > booklist)
    {
        ArrayAdapter<ACCOUNTBOOK> adp=new ArrayAdapter<ACCOUNTBOOK>(this , R.layout.support_simple_spinner_dropdown_item,booklist);
        mSpinnerTitle .setAdapter(adp);
    }

    private void loadBookSubjectlist(List<ACCOUNTSUBJECT> booksubjectlist)
    {
        ArrayAdapter<ACCOUNTSUBJECT> adp = new ArrayAdapter<ACCOUNTSUBJECT>(this, R.layout.support_simple_spinner_dropdown_item, booksubjectlist );
        this.mSpnSubject.setAdapter(adp);
    }







    //region description 初始化
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doaccount);
        this.InitializeComponent(savedInstanceState);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private Toolbar toolbar;
    private Button mBtnAccount;
    private ImageButton mBtnCamera;
    private RecyclerView mRcvPiclist;

    private Button mBtnAccountCheck;
    private Button mBtnAccountReport;
    private EditText mEditPrice;
    private EditText mEditCount;
    private EditText mEdtRemark;
    private EditText mEdtName;
    private Spinner  mSpinnerTitle;
    private Spinner mSpnSubject;
    private DateSelected mDateSelected;
    private CheckBox mIsCheckAccount;


    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        LoadView();
        //载入时发生
        Load(getIntent(), savedInstanceState);
        setSupportActionBar(toolbar);

        //控件时间生成
        this.mBtnAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateIitem(v);
            }
        });
        this.mBtnCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StartCamera(v);
            }
        });
        this.mBtnAccountReport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ShowAccountReport(v);
            }
        });
        this.mBtnAccountCheck .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UploadAccount_Click(v);
            }
        }) ;
        this.mSpinnerTitle .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                AccountBook_ItemSelected(parent ,view,position ,id );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        }) ;
        this.mSpnSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                BookSubject_ItemSelected(parent ,view,position ,id );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        }) ;
    }


    private void LoadView()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        this.mSpnSubject = (Spinner) findViewById(R.id.spSubject_con);
        this.mSpinnerTitle = (Spinner) findViewById(R.id.spBookName_con);
        this.mEditCount = (EditText) findViewById(R.id.edtCount_con);
        this.mEditPrice = (EditText) findViewById(R.id.edtPrice_con);
        this.mEdtRemark = (EditText) findViewById(R.id.edtRemark_con);
        this.mEdtName = (EditText) findViewById(R.id.edtName_con);
        this.mBtnAccount = (Button) findViewById(R.id.btnAccount_con);
        this.mBtnAccountCheck = (Button) findViewById(R.id.btnAccountItemCheck_con);
        this.mBtnAccountReport = (Button) findViewById(R.id.btnAccountReport_con);
        this.mBtnCamera = (ImageButton) findViewById(R.id.ibtnCamera_con);
        mDateSelected = (DateSelected) findViewById(R.id.dateSelected_con);
        this.mRcvPiclist = (RecyclerView) findViewById(R.id.rcvPiclist_con);
        this.mIsCheckAccount = (CheckBox) findViewById(R.id.chkAccountCheck_con);
        this.mBtnAccountReport = (Button) findViewById(R.id.btnAccountReport_con);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doaccount, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.itemaccountsubject)
        {
            CreateAccountSubject();
            return true;
        }

        if (id == R.id.itemaddaccountbook)
        {
            CreateAccountbook();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(RETURN_CREATE_ACCOUNTSUBJECT ==requestCode)
        {
            AccountSubjectCreate_Closed(data);
        }
        if(RETURN_CREATE_ACCOUNTBOOK ==requestCode)
        {
            AccountBookCreate_Closed(data);
        }

    }




    //endregion
}
