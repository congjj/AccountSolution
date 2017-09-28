package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/9/23.
 */

public class OperateActivityController
{
    Button btnCreateBook;
    Button btnCreateSubject;
    Button btnAccount;
    TextView txvOperatet;
    OperateActivity  operateActivity ;

    public OperateActivityController (OperateActivity  operateActivity , TextView txvOperatet ,Button btnCreateBook ,Button btnCreateSubject
    ,Button btnAccount)
    {
        this.operateActivity  =operateActivity ;
        this.btnCreateBook = btnCreateBook;
        this.btnCreateSubject = btnCreateSubject ;
        this.btnAccount =btnAccount ;
        this.txvOperatet =txvOperatet;
    }



    public void operateAcivity_Load(Bundle savedInstanceState)
    {
        long id = operateActivity.getIntent() .getLongExtra("data",-1) ;
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
