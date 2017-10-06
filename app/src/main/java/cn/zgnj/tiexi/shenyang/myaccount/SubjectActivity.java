package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
                SubjectActivityController .createOneSubject(SubjectActivity .this,view);
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
                SubjectActivityController .RbdInChecked(SubjectActivity.this,compoundButton ,b);
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
                SubjectActivityController .RbdOutChecked(SubjectActivity.this,compoundButton ,b);
            }
        }) ;
    }


    public TextView getTxvSubjectTitle()
    {
        return mTxvSubjectTitle;
    }
    public RadioButton getRdbOut()
    {
        return mRdbOut;
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
    public RecyclerView getRecyclerView()
    {
        return mRecyclerView;
    }


    private TextView  mTxvSubjectTitle;
    private EditText mEdtSubjectRemark;
    private EditText mEdtSubjectName;
    private Button mBtnSubjectCreate;
    private RadioButton mRdbIn;
    private RadioButton mRdbOut;
    private RecyclerView mRecyclerView ;

    private void LoadView()
    {
        this.mTxvSubjectTitle =(TextView )findViewById(R.id .txvSubjectTitle) ;
        this.mRdbIn =(RadioButton )findViewById(R .id .rdbIn) ;
        this.mRdbOut =(RadioButton)findViewById(R.id .rdbOut ) ;
        this.mEdtSubjectName =(EditText)findViewById(R.id .edtSubjectName) ;
        this.mEdtSubjectRemark =(EditText )findViewById(R.id .edtSubjectRemark ) ;
        this.mBtnSubjectCreate =(Button)findViewById(R.id .btnSubjectCreate) ;
        this.mRecyclerView =(RecyclerView)findViewById(R.id .revSubjectList) ;
    }

}
