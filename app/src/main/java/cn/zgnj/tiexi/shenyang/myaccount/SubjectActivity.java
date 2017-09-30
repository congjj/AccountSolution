package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class SubjectActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        this.InitializeComponent( savedInstanceState) ;
    }


    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        this.LoadView();
        /**
         * 载入
         */
         SubjectActivityController .Load(getIntent(),SubjectActivity .this) ;
        /**
         * 按钮生成
         */
        this.mBtnSubjectCreate .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        }) ;
        /**
         * rdbIn
         */
        this.mRdbIn .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {

            }
        }) ;
        /**
         * rdbOut
         */
        this.mRdbOut .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {

            }
        }) ;
    }


    public TextView getTxvSubjectTitle()
    {
        return mTxvSubjectTitle;
    }

    public RadioButton getRdbIn()
    {
        return mRdbIn;
    }

    public EditText getEdtSubjectName()
    {
        return mEdtSubjectName;
    }

    public EditText getEdtSubjectRemark()
    {
        return mEdtSubjectRemark;
    }

    public Button getBtnSubjectCreate()
    {
        return mBtnSubjectCreate;
    }

    private TextView  mTxvSubjectTitle;
    private EditText mEdtSubjectRemark;
    private EditText mEdtSubjectName;
    private Button mBtnSubjectCreate;
    private RadioButton mRdbIn;
    private RadioButton mRdbOut;

    private void LoadView()
    {
        this.mTxvSubjectTitle =(TextView )findViewById(R.id .txvSubjectTitle) ;
        this.mRdbIn =(RadioButton )findViewById(R .id .rdbIn) ;
        this.mRdbOut =(RadioButton)findViewById(R.id .rdbOut ) ;
        this.mEdtSubjectName =(EditText)findViewById(R.id .edtSubjectName) ;
        this.mEdtSubjectRemark =(EditText )findViewById(R.id .edtBookRemark ) ;
        this.mBtnSubjectCreate =(Button)findViewById(R.id .btnSubjectCreate) ;
    }



}
