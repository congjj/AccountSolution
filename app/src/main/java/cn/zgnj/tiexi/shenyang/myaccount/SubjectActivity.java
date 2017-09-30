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
        mSubjectActivityController.subjectActivity_Load(savedInstanceState);
        /**
         * 按钮生成
         */
        this.mbtnSubjectCreate .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mSubjectActivityController .btnSubjectCreate_Click(view)   ;
            }
        }) ;
        /**
         * rdbIn
         */
        this.mrdbIn .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {

            }
        }) ;
        /**
         * rdbOut
         */
        this.mrdbOut .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {

            }
        }) ;


    }






    private SubjectActivityController mSubjectActivityController;
    TextView  mtxvSubjectTitle;
    RadioButton mrdbIn;
    RadioButton mrdbOut;
    EditText medtSubjectName;
    EditText medtSubjectRemark;
    Button mbtnSubjectCreate;
    private void LoadView()
    {
        this.mtxvSubjectTitle =(TextView )findViewById(R.id .txvSubjectTitle) ;
        this.mrdbIn =(RadioButton )findViewById(R .id .rdbIn) ;
        this.mrdbOut =(RadioButton)findViewById(R.id .rdbOut ) ;
        this.medtSubjectName =(EditText)findViewById(R.id .edtSubjectName) ;
        this.medtSubjectRemark =(EditText )findViewById(R.id .edtBookRemark ) ;
        this.mbtnSubjectCreate =(Button )findViewById(R.id .btnSubjectCreate) ;
        mSubjectActivityController =new SubjectActivityController(SubjectActivity.this ,mtxvSubjectTitle ,mrdbIn ,mrdbOut ,medtSubjectName ,
                medtSubjectRemark ,mbtnSubjectCreate )  ;
    }



}
