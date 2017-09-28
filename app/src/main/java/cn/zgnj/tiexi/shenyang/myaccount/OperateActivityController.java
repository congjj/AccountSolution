package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;


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


    public OperateActivityController (OperateActivity  operateActivity , TextView txvOperatet ,Button btnCreateBook ,Button btnCreateSubject
    ,Button btnAccount,Spinner cmbBookTypeList)
    {
        this.operateActivity  =operateActivity ;
        this.btnCreateBook = btnCreateBook;
        this.btnCreateSubject = btnCreateSubject ;
        this.btnAccount =btnAccount ;
        this.txvOperatet =txvOperatet;
        this.cmbBookTypeList =cmbBookTypeList ;
    }


//    public <T> T[] toArray(@NonNull T[] ts)
//    {
//        return null;
//    }

    public void operateAcivity_Load(Bundle savedInstanceState)
    {
        long id = ((OperateActivity)operateActivity).getIntent() .getLongExtra("sendUserID",-1) ;


        List<String >list=new ArrayList<String>();
        list.add("fff") ;
        list .add("aaa") ;
        ArrayAdapter<String > adp=new ArrayAdapter<String>(operateActivity,R.layout .support_simple_spinner_dropdown_item ,list);



        cmbBookTypeList.setAdapter(adp);
    }

    public void btnCreateBook_Click(View send)
    {
    }

    public void btnCreateSubject_Click(View send)
    {
    }

    public void btnAccount_Click(View send)
    {
    }


}
