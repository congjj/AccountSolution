package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.TextView;

public class AccountreportActivity extends AppCompatActivity
{



    //载入时发生
    private void Load(Intent intent, Bundle savedInstanceState)
    {

        this .mTxvReprotBookName .setTag("as") ;
        this .mTxvReprotBookName .setText("as") ;
    }

    private void txvReprotBookName_AfterTextChanged(Editable s)
    {
         mChkIsSubject .setText(s.toString()+"fff") ;

    }







    // region description 初始化

    private TextView mTxvReprotBookName ;
    private CheckBox mChkIsSubject;
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
    }




    //endregion

}
