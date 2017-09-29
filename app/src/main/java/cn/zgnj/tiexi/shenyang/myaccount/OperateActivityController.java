package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.AndroidException;
import android.view.View;


import android.view.ViewAnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;

import static android.R.id.accessibilityActionContextClick;
import static android.R.id.list;

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
    }

    //开始记账
    public void btnAccount_Click(View send)
    {
    }

    //创建一个记账簿
    public void btnBookType_Click(View view)
    {

        ACCOUNTBOOK book=new ACCOUNTBOOK(_UserInfo ,this.edtBookName .getText().toString() ,this.edtBookRemark .getText().toString()) ;
        book._Insert() ;
        loadBookTypelist(_UserInfo .getACCOUNTBOOKList()) ;
        doSuccess() ;
    }

    //退出创建记账簿
    public void btnExit_Click(View view)
    {
        doSuccess() ;
        this.pnlCreateBookType .setVisibility(View.INVISIBLE) ;
    }

    //载入记账簿
    void loadBookTypelist(List<ACCOUNTBOOK > booklist)
    {
        List<String >list=new ArrayList<String>();
        for(ACCOUNTBOOK temp :booklist )
        {
            list .add(temp.toString()) ;
        }
        ArrayAdapter<String > adp=new ArrayAdapter<String>(operateActivity, R.layout.support_simple_spinner_dropdown_item,list);
        cmbBookTypeList.setAdapter(adp);
    }

    void doSuccess()
    {
        this.edtBookName.setText("") ;
        this.edtBookRemark .setText("") ;
    }


}
