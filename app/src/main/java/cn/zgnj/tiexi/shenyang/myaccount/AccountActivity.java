package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        this.InitializeComponent( savedInstanceState) ;
    }


    private void InitializeComponent(Bundle savedInstanceState)
    {

        /**
         * 载入View
         */
        this.LoadView();
        AccountActivityController .Load(getIntent(),AccountActivity .this) ;
        this.mBtnDateSelect .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AccountActivityController .SetAccountDate(AccountActivity .this,v);
            }
        }) ;

    }

    private void LoadView()
    {
        this.mSpnSubject =(Spinner)findViewById(R.id .spSubject ) ;
        this.mTvTitle =(TextView )findViewById(R .id.txvTitle) ;
        this.mEditCount =(EditText )findViewById(R.id .edtCount ) ;
        this.mEditPrice =(EditText )findViewById(R .id .edtPrice ) ;
        this.mEdtRemark =(EditText )findViewById(R.id .edtRemark ) ;
        this.mEdtName =(EditText )findViewById(R.id .edtName ) ;
        this.mBtnAccount =(Button )findViewById(R.id .btnAccount ) ;
        this.mBtnDateSelect =(Button )findViewById(R.id .btnDateSelect ) ;
        this.mTvDate =(TextView )findViewById(R.id .tvDate ) ;
    }

    public TextView getTvDate()
    {
        return mTvDate;
    }

    private TextView mTvDate;
    public Button getBtnDateSelect()
    {
        return mBtnDateSelect;
    }

    private Button mBtnDateSelect;
    public Button getBtnAccount()
    {
        return mBtnAccount;
    }

    private Button mBtnAccount;

    public EditText getEditPrice()
    {
        return mEditPrice;
    }

    private EditText mEditPrice;
    public EditText getEditCount()
    {
        return mEditCount;
    }

    private EditText mEditCount;
    public EditText getEdtRemark()
    {
        return mEdtRemark;
    }

    private EditText mEdtRemark;
    public EditText getEdtName()
    {
        return mEdtName;
    }

    private EditText mEdtName;
    public TextView getTvTitle()
    {
        return mTvTitle;
    }

    private TextView mTvTitle;
    public Spinner getSpnSubject()
    {
        return mSpnSubject;
    }
    private Spinner mSpnSubject;

}
