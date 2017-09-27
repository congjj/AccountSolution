package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/9/23.
 */

public class OperateActivityController
{
    Button btnLogin;
    TextView txvTelNO;
    Context operateActivity ;

    public OperateActivityController (Context operateActivity  ,Button btnLogin , TextView txvTelNO)
    {
        this.operateActivity  =operateActivity ;
        this.btnLogin =btnLogin ;
        this.txvTelNO =txvTelNO;
    }





    public void operateAcivity_Load(Bundle savedInstanceState)
    {
        long id =((OperateActivity)operateActivity).getIntent() .getLongExtra("data",-1) ;
    }
}
