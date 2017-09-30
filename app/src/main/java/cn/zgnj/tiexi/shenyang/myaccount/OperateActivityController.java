package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;


/**
 * Created by Administrator on 2017/9/23.
 */

public class OperateActivityController
{
    Button btnCreateBook;
    Button btnCreateSubject;
    Button btnAccount;
    Button btnBookType;
    Button btnExit;
    EditText edtBookName;
    EditText edtBookRemark;
    TextView txvOperatet;
    Spinner cmbBookTypeList;
    Context operateActivity ;
    ConstraintLayout pnlCreateBookType;
    USERINFO  _UserInfo;

    public OperateActivityController (OperateActivity  operateActivity , TextView txvOperatet ,Button btnCreateBook ,Button btnCreateSubject
    ,Button btnAccount,Spinner cmbBookTypeList,ConstraintLayout pnlCreateBookType,Button btnBookType ,Button btnExit ,EditText edtBookName ,
                                      EditText edtBookRemark )
    {
        this.operateActivity  =operateActivity ;
        this.btnCreateBook = btnCreateBook;
        this.btnCreateSubject = btnCreateSubject ;
        this.btnAccount =btnAccount ;
        this.txvOperatet =txvOperatet;
        this.cmbBookTypeList =cmbBookTypeList ;
        this.pnlCreateBookType =pnlCreateBookType ;
        this.btnBookType =btnBookType ;
        this.btnExit =btnExit ;
        this.edtBookName =edtBookName ;
        this.edtBookRemark =edtBookRemark ;
    }

    //载入
    public void operateAcivity_Load(Bundle savedInstanceState)
    {
        long id = ((OperateActivity)operateActivity).getIntent().getLongExtra("sendUserID",-1) ;
        this.pnlCreateBookType .setVisibility(View.INVISIBLE) ;
        _UserInfo =USERINFO.findById(USERINFO.class ,id) ;
        this.loadBookTypelist(_UserInfo .getACCOUNTBOOKList()) ;
    }

    //显示创建账簿
    public void btnCreateBook_Click(View send)
    {
        this.pnlCreateBookType .setVisibility(View.VISIBLE) ;
    }

    //显示账目科目
    public void btnCreateSubject_Click(View send)
    {
        ACCOUNTBOOK book = (ACCOUNTBOOK)this.cmbBookTypeList.getSelectedItem() ;
        Intent i=new Intent(operateActivity ,SubjectActivity .class );
        Bundle bundle = new Bundle() ;
        bundle.putString("name",book .getNAME()) ;
        bundle .putString("remark",book .getREMARK()) ;
        bundle .putLong("book_ID",book .getId()) ;
        i.putExtra("sendBookType",bundle);
        operateActivity . startActivity(i);
    }

    //开始记账
    public void btnAccount_Click(View send)
    {
    }

    //创建一个记账簿
    public void btnBookType_Click(View view)
    {
        IModelHelper book=new ACCOUNTBOOK(_UserInfo ,this.edtBookName .getText().toString().toUpperCase(),this.edtBookRemark .getText().toString()) ;
        if( book._Insert()==-1)
        {
            Toast.makeText(operateActivity,"记账簿名称：" + this.edtBookName .getText() +" 重复，请换用其它名称！", Toast.LENGTH_LONG ).show();
        }
        loadBookTypelist(_UserInfo .getACCOUNTBOOKList()) ;
        doSuccess() ;
    }

    //退出创建记账簿
    public void btnExit_Click(View view)
    {
        doSuccess() ;
        this.pnlCreateBookType .setVisibility(View.INVISIBLE) ;
    }

    //记账簿选项发生改变是发生
    public void cmbBookTypeList_ItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {

    }



    //载入记账簿
    void loadBookTypelist(List<ACCOUNTBOOK > booklist)
    {
        ArrayAdapter<ACCOUNTBOOK> adp=new ArrayAdapter<ACCOUNTBOOK>(operateActivity, R.layout.support_simple_spinner_dropdown_item,booklist);
        cmbBookTypeList.setAdapter(adp);
    }

    void doSuccess()
    {
        this.edtBookName.setText("") ;
        this.edtBookRemark .setText("") ;
    }



}
