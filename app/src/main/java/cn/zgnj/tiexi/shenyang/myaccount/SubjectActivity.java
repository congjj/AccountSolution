package cn.zgnj.tiexi.shenyang.myaccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }






    private SubjectActivityController mSubjectActivityController;
    TextView  mtxvSubjectTitle;
    RadioButton mrdbIn;
    RadioButton mrdbOut;
    private void LoadView()
    {
        this.mtxvSubjectTitle =(TextView )findViewById(R.id .txvSubjectTitle) ;
        this.mrdbIn =(RadioButton )findViewById(R .id .rdbIn) ;
        this.mrdbOut =(RadioButton)findViewById(R.id .rdbOut ) ;
    }



}
