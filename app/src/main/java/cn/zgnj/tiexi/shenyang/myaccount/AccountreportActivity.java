package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;

public class AccountreportActivity extends AppCompatActivity
{


    private ACCOUNTBOOK mACCOUNTBOOK ;

    //载入时发生
    private void Load(Intent intent, Bundle savedInstanceState)
    {
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("sendBookID");
        mACCOUNTBOOK =ACCOUNTBOOK .getItSelf(bundle.getLong("book_ID"));
        this.mTxvReprotBookName .setText(mACCOUNTBOOK .toString());
    }

    private void txvReprotBookName_AfterTextChanged(Editable s)
    {

    }

    private void spnSubjectItem_ItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if(this.mSpnSubjectItem.isEnabled())
        {}
        else
        {}
    }




    private void chkIsSubject_CheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        this.mSpnSubjectItem .setEnabled(!isChecked) ;
    }



    // region description 初始化

    private TextView mTxvReprotBookName ;
    private CheckBox mChkIsSubject;
    private Spinner mSpnSubjectItem;
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
        //控件事件
        this.SetListener();
        //载入时发生
        Load(getIntent(), savedInstanceState);
    }

    private void LoadView()
    {
        this.mTxvReprotBookName =(TextView)findViewById(R.id .txvReprotBookName) ;
        this.mChkIsSubject =(CheckBox )findViewById(R.id.chkIsSubject) ;
        this.mSpnSubjectItem =(Spinner)findViewById(R.id.spnSubjectItem) ;
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

        this.mChkIsSubject .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                chkIsSubject_CheckedChanged(buttonView, isChecked);
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
    }




    //endregion

}
