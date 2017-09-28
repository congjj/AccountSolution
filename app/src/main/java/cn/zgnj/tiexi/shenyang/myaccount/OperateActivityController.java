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
import android.widget.Spinner;

import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

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
    TextView txvOperatet;
    Spinner cmbBookTypeList;
    Context operateActivity ;
    ConstraintLayout pnlCreateBookType;

    public OperateActivityController (OperateActivity  operateActivity , TextView txvOperatet ,Button btnCreateBook ,Button btnCreateSubject
    ,Button btnAccount,Spinner cmbBookTypeList,ConstraintLayout pnlCreateBookType)
    {
        this.operateActivity  =operateActivity ;
        this.btnCreateBook = btnCreateBook;
        this.btnCreateSubject = btnCreateSubject ;
        this.btnAccount =btnAccount ;
        this.txvOperatet =txvOperatet;
        this.cmbBookTypeList =cmbBookTypeList ;
        this.pnlCreateBookType =pnlCreateBookType ;
    }


//    public <T> T[] toArray(@NonNull T[] ts)
//    {
//        return null;
//    }


    //载入
    public void operateAcivity_Load(Bundle savedInstanceState)
    {
        long id = ((OperateActivity)operateActivity).getIntent() .getLongExtra("sendUserID",-1) ;
        this.pnlCreateBookType .setVisibility(View.INVISIBLE) ;


        List<String >list=new ArrayList<String>();
        list.add("fff") ;
        list .add("aaa") ;
        ArrayAdapter<String > adp=new ArrayAdapter<String>(operateActivity, R.layout.support_simple_spinner_dropdown_item,list);



        cmbBookTypeList.setAdapter(adp);
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


}
