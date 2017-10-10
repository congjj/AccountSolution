package cn.zgnj.tiexi.shenyang.myaccount;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTSUBJECT;
import cn.zgnj.tiexi.shenyang.myaccount.utility.DateSelected;

public class AccountActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        this.InitializeComponent(savedInstanceState);
    }

    private long accountBookID;

    /**
     * 载入时发生
     *
     * @param intent
     * @param savedInstanceState
     */
    private void Load(Intent intent, Bundle savedInstanceState)
    {
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("sendBookType");
        accountBookID = bundle.getLong("book_ID");
        mTvTitle .setText(bundle .getString("name")+"【"+bundle .getString("remark") +"】" ) ;

        List<ACCOUNTSUBJECT> list = ACCOUNTSUBJECT.getList4Book(accountBookID);
        loadBookTypelist(list) ;
    }

    /**
     * 生成一条记账
     * @param v
     */
    private void CreateIitem(View v)
    {
    }


    private  void loadBookTypelist(List<ACCOUNTSUBJECT> booklist)
    {
        ArrayAdapter<ACCOUNTSUBJECT> adp=new ArrayAdapter<ACCOUNTSUBJECT>(this , R.layout.support_simple_spinner_dropdown_item,booklist);
        this.mSpnSubject.setAdapter(adp);
    }

    //region description 初始化
    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        this.LoadView();
        Load(getIntent(), savedInstanceState);
        this.mBtnAccount .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateIitem(v);
            }
        }) ;
    }


    private Button mBtnDateSelect;
    private Button mBtnAccount;
    private Button mBtnAccountUpdate;
    private Button mBtnAccountCheck;
    private Button mBtnAccountReport;
    private EditText mEditPrice;
    private EditText mEditCount;
    private EditText mEdtRemark;
    private EditText mEdtName;
    private TextView mTvTitle;
    private Spinner mSpnSubject;
    private DateSelected mDateSelected ;
    private void LoadView()
    {
        this.mSpnSubject = (Spinner) findViewById(R.id.spSubject);
        this.mTvTitle = (TextView) findViewById(R.id.txvTitle);
        this.mEditCount = (EditText) findViewById(R.id.edtCount);
        this.mEditPrice = (EditText) findViewById(R.id.edtPrice);
        this.mEdtRemark = (EditText) findViewById(R.id.edtRemark);
        this.mEdtName = (EditText) findViewById(R.id.edtName);
        this.mBtnAccount = (Button) findViewById(R.id.btnAccount);
        this.mBtnAccountUpdate =(Button )findViewById(R.id.btnAccountUpdate) ;
        this.mBtnAccountCheck =(Button )findViewById(R.id.btnAccountCheck) ;
        this.mBtnAccountReport  =(Button )findViewById(R.id.btnAccountReport) ;
        mDateSelected =(DateSelected)findViewById(R.id.dateSelected);

    }


    //endregion

}
