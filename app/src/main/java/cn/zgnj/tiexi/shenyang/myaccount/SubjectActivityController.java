package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;

import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTSUBJECT;

/**
 * Created by CJJ on 2017/9/28.
 */

public class SubjectActivityController
{
    private static  long accountBookID;

    public static void Load(Intent intent,SubjectActivity subjectActivity )
    {
        Bundle bundle =new Bundle() ;
        bundle =intent.getBundleExtra("sendBookType") ;
        TextView titleView = subjectActivity .getTxvSubjectTitle() ;
        titleView .setText(bundle .getString("name") +"【" +bundle.getString("remark") +"】") ;
        accountBookID =bundle .getLong("book_ID") ;

        List<String > mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("dddd" + (char) i);
        }
        SubjectItmeAdapter mAdapter = new SubjectItmeAdapter(mDatas ,subjectActivity ) ;
        subjectActivity .getRecyclerView().setLayoutManager(new LinearLayoutManager(subjectActivity));
        subjectActivity .getRecyclerView().setAdapter(mAdapter );
    }


    public static void RbdInChecked(SubjectActivity subjectActivity,CompoundButton compoundButton, boolean b)
    {
        subjectActivity .getRdbOut() .setChecked(!b) ;
    }

    public static void RbdOutChecked(SubjectActivity subjectActivity,CompoundButton compoundButton, boolean b)
    {
        subjectActivity .getRdbIn().setChecked(!b) ;
    }

    public static void createOneSubject(SubjectActivity subjectActivity, View view)
    {
         boolean isout=subjectActivity .getRdbOut().isChecked();
         ACCOUNTBOOK accountbook = ACCOUNTBOOK .getItSelf(accountBookID) ;
         String name=subjectActivity .getEdtSubjectName() .getText().toString() ;
         String remark=subjectActivity .getEdtSubjectRemark() .getText().toString();
        IModelHelper temp=new ACCOUNTSUBJECT(accountbook ,name  ,remark,isout );
         if(temp ._Insert() ==-1)
         {
             Toast.makeText(subjectActivity , "记账科目重复", Toast.LENGTH_LONG ).show();
             return ;
         }
         else
         {
             doSuccess(subjectActivity) ;
             Toast.makeText(subjectActivity , "成功！", Toast.LENGTH_LONG ).show();
         }
    }



    static void showSubjectList(SubjectActivity subjectActivity)
    {
        List<ACCOUNTSUBJECT>accountsubjectList = ACCOUNTSUBJECT .getList4Book(accountBookID ) ;
        ArrayAdapter<ACCOUNTSUBJECT> adp=new ArrayAdapter<ACCOUNTSUBJECT>(subjectActivity, R.layout.support_simple_spinner_dropdown_item,accountsubjectList);

        //gridView .setAdapter(accountsubjectList) ;
        //CursorAdapter

    }






    static void doSuccess(SubjectActivity subjectActivity)
    {
        subjectActivity .getEdtSubjectRemark() .setText("") ;
        subjectActivity .getEdtSubjectName().setText("") ;
    }
}
