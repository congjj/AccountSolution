package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewAnimationUtils;
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
    //载入
    public static void Load(OperateActivity operateActivity)
    {
        long id = operateActivity.getIntent().getLongExtra("sendUserID",-1) ;
        operateActivity .getPnlCreateBookType() .setVisibility(View.INVISIBLE) ;
        loadBookTypelist(operateActivity,USERINFO.getOne(id).getACCOUNTBOOKList()) ;
    }

    //显示账簿创建窗口
    public static void ShowBookTypeView(OperateActivity operateActivity)
    {
        operateActivity .getPnlCreateBookType() .setVisibility(View.VISIBLE) ;
    }


    //调用创建科目窗口并传送 账簿参数
    public static void CreateSubjct(OperateActivity operateActivity)
    {
        ACCOUNTBOOK book = (ACCOUNTBOOK)operateActivity .getCmbBookTypeList().getSelectedItem() ;
        if(book==null)
        {
            Toast.makeText(operateActivity , "请选择账簿或创建一个账簿！", Toast.LENGTH_LONG ).show();
            return ;
        }
        Intent i=new Intent(operateActivity ,SubjectActivity .class );
        Bundle bundle = new Bundle() ;
        bundle.putString("name",book .getNAME()) ;
        bundle .putString("remark",book .getREMARK()) ;
        bundle .putLong("book_ID",book .getId()) ;
        i.putExtra("sendBookType",bundle);
        operateActivity . startActivity(i);
    }

    //开始记账
    public static void StartAccount(View view)
    {
    }

    //创建一个记账簿
    public static void CreateBookType(OperateActivity operateActivity)
    {
        long id = operateActivity.getIntent().getLongExtra("sendUserID",-1) ;
        USERINFO userinfo =USERINFO.getOne(id);
        IModelHelper book=new ACCOUNTBOOK(userinfo,operateActivity.getEdtBookName().getText().toString().toUpperCase(),
                operateActivity .getEdtBookRemark().getText().toString()) ;
        if( book._Insert()==-1)
        {
            Toast.makeText(operateActivity,"记账簿名称：" + operateActivity.getEdtBookName().getText()+" 重复，请换用其它名称！", Toast.LENGTH_LONG ).show();
        }
        loadBookTypelist(operateActivity ,userinfo.getACCOUNTBOOKList()) ;
        doSuccess(operateActivity) ;
    }

    //退出创建记账簿
    public static void HideBookTypeView(OperateActivity operateActivity)
    {
        doSuccess(operateActivity) ;
        operateActivity .getPnlCreateBookType() .setVisibility(View.INVISIBLE) ;
    }

    //记账簿选项发生改变是发生
    public static void BookTypeListItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {


    }


    //载入记账簿
    private static void loadBookTypelist(OperateActivity operateActivity,List<ACCOUNTBOOK > booklist)
    {
        ArrayAdapter<ACCOUNTBOOK> adp=new ArrayAdapter<ACCOUNTBOOK>(operateActivity, R.layout.support_simple_spinner_dropdown_item,booklist);
        operateActivity .getCmbBookTypeList() .setAdapter(adp);
    }

    static void doSuccess(OperateActivity operateActivity)
    {
        operateActivity .getEdtBookName() .setText("") ;
        operateActivity .getEdtBookRemark() .setText("") ;
    }



}
