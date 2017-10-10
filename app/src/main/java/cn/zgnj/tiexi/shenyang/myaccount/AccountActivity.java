package cn.zgnj.tiexi.shenyang.myaccount;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

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





    /**
     * 载入时发生
     *
     * @param intent
     * @param savedInstanceState
     */
    private void Load(Intent intent, Bundle savedInstanceState)
    {

    }




    //region description 初始化
    private void InitializeComponent(Bundle savedInstanceState)
    {

        /**
         * 载入View
         */
        this.LoadView();
        Load(getIntent(), savedInstanceState);

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
