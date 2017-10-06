package cn.zgnj.tiexi.shenyang.myaccount;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class AccountActivity extends AppCompatActivity
{

    final Calendar ca = Calendar.getInstance();
    int mYear = ca.get(Calendar.YEAR);
    int mMonth = ca.get(Calendar.MONTH);
    int mDay = ca.get(Calendar.DAY_OF_MONTH);
    final int DATE_DIALOG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        this.InitializeComponent( savedInstanceState) ;
    }

    /**
     * 载入时发生
     * @param intent
     * @param savedInstanceState
     */
    private void Load(Intent intent, Bundle savedInstanceState)
    {
        display() ;
    }



    private void InitializeComponent(Bundle savedInstanceState)
    {

        /**
         * 载入View
         */
        this.LoadView();
        Load(getIntent(),savedInstanceState) ;
        this.mBtnDateSelect .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDialog(DATE_DIALOG) ;
            }
        }) ;
    }


    private  DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener()
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

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    void  display() {
        mTvDate.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
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
