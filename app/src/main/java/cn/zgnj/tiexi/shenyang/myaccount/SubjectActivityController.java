package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by CJJ on 2017/9/28.
 */

public class SubjectActivityController
{
    Context subjectActivity;
    TextView txvSubjectTitle;
    RadioButton rdbIn;
    RadioButton rdbOut; 
    EditText edtSubjectName;
    EditText edtSubjectRemark;
    Button btnSubjectCreate;
    
    public SubjectActivityController(SubjectActivity  msubjectActivity, TextView mtxvSubjectTitle, RadioButton mrdbIn, RadioButton mrdbOut,
                                     EditText medtSubjectName, EditText medtSubjectRemark, Button mbtnSubjectCreate)
    {
        this.subjectActivity =msubjectActivity ;
        this.txvSubjectTitle =mtxvSubjectTitle;
        rdbIn =mrdbIn;
        rdbOut =mrdbOut ;
        edtSubjectName =medtSubjectName ;
        edtSubjectRemark =medtSubjectRemark;
        btnSubjectCreate =mbtnSubjectCreate;
    }


    public void subjectActivity_Load(Bundle savedInstanceState)
    {
    }

    public void btnSubjectCreate_Click(View view)
    {
    }
}
