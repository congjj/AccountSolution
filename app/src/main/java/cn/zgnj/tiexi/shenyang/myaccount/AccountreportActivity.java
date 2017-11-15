package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;
import cn.zgnj.tiexi.shenyang.myaccount.utility.DateSelected4Section;

import static cn.zgnj.tiexi.shenyang.myaccount.R.id.dsDate;

public class AccountreportActivity extends AppCompatActivity
{


    private ACCOUNTBOOK mACCOUNTBOOK ;

    //载入时发生
    private void Load(Intent intent, Bundle savedInstanceState)
    {
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("sendBookID");
        mACCOUNTBOOK =ACCOUNTBOOK .getItSelf(bundle.getLong("book_ID"));
        this.mTxvReprotBookName .setText("您的记账簿");

        List<ACCOUNTBOOK>booklist = USERINFO.getOne(bundle.getLong("user_ID")).getACCOUNTBOOKList();
        ArrayAdapter<ACCOUNTBOOK> adp=new ArrayAdapter<ACCOUNTBOOK>(this , R.layout.support_simple_spinner_dropdown_item,booklist);
        mSpnSubjectItem.setAdapter(adp);
    }

    private void txvReprotBookName_AfterTextChanged(Editable s)
    {

    }

    //
    private void spnSubjectItem_ItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if(this.mSpnSubjectItem.isEnabled())
        {}
        else
        {}
    }

    private void mDateSelected4Section_AfterSelectedDate(int btnID, Calendar fromCa, Calendar toCa)
    {

    }


    private void chkIsSubject_CheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        this.mSpnSubjectItem .setEnabled(!isChecked) ;
    }



    // region description 初始化

    private TextView mTxvReprotBookName ;

    private Spinner mSpnSubjectItem;
    private DateSelected4Section mDateSelected4Section ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountreport);
        this.InitializeComponent(savedInstanceState);
    }

    private void InitializeComponent(Bundle savedInstanceState)
    {
        //载入控件
        this.LoadView();

        //载入时发生
        Load(getIntent(), savedInstanceState);
        //控件事件
        this.SetListener();
    }

    private void LoadView()
    {
        this.mTxvReprotBookName =(TextView)findViewById(R.id .txvReprotBookName) ;
        this.mSpnSubjectItem =(Spinner)findViewById(R.id.spnSubjectItem) ;
        this.mDateSelected4Section =(DateSelected4Section)findViewById(R.id .dsDate) ;
    }

    private void SetListener()
    {
        this.mTxvReprotBookName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                txvReprotBookName_AfterTextChanged(s);
            }
        }) ;

        this.mSpnSubjectItem .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                spnSubjectItem_ItemSelected(parent,view,  position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        }) ;


        this.mDateSelected4Section.SetOnAfterSelectedDateListener =new DateSelected4Section.AfterSelectedDateDialogListener()
        {
            @Override
            public void AfterSelectedDate(int btnID, Calendar fromCa, Calendar toCa)
            {
                mDateSelected4Section_AfterSelectedDate( btnID,  fromCa, toCa);
            }
        };

    }




    //endregion

}
