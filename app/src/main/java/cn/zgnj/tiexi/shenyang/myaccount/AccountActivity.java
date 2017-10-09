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

public class AccountActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        this.InitializeComponent(savedInstanceState);
    }


    final Calendar ca = Calendar.getInstance();
    final int DATE_DIALOG = 1;
    int mYear = ca.get(Calendar.YEAR);
    int mMonth = ca.get(Calendar.MONTH);
    int mDay = ca.get(Calendar.DAY_OF_MONTH);
    private TextView mTvDate;
    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };


    /**
     * 载入时发生
     *
     * @param intent
     * @param savedInstanceState
     */
    private void Load(Intent intent, Bundle savedInstanceState)
    {
        display();
    }




    //region description 初始化
    private void InitializeComponent(Bundle savedInstanceState)
    {

        /**
         * 载入View
         */
        this.LoadView();
        Load(getIntent(), savedInstanceState);
        this.mBtnDateSelect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDialog(DATE_DIALOG);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    void display()
    {
        mTvDate.setText(new StringBuffer().append(mMonth).append("-").append(mDay).append("-").append(mYear).append(" "));
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
    private void LoadView()
    {
        this.mSpnSubject = (Spinner) findViewById(R.id.spSubject);
        this.mTvTitle = (TextView) findViewById(R.id.txvTitle);
        this.mEditCount = (EditText) findViewById(R.id.edtCount);
        this.mEditPrice = (EditText) findViewById(R.id.edtPrice);
        this.mEdtRemark = (EditText) findViewById(R.id.edtRemark);
        this.mEdtName = (EditText) findViewById(R.id.edtName);
        this.mBtnAccount = (Button) findViewById(R.id.btnAccount);
        this.mBtnDateSelect = (Button) findViewById(R.id.btnDateSelect);
        this.mTvDate = (TextView) findViewById(R.id.tvDate);
        this.mBtnAccountUpdate =(Button )findViewById(R.id.btnAccountUpdate) ;
        this.mBtnAccountCheck =(Button )findViewById(R.id.btnAccountCheck) ;
        this.mBtnAccountReport  =(Button )findViewById(R.id.btnAccountReport) ;
    }


    //endregion

}
