package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.content.Intent;
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



    public static void Load(Intent intent,SubjectActivity subjectActivity )
    {
        Bundle bundle =new Bundle() ;
        bundle =intent.getBundleExtra("sendBookType") ;
        TextView titleView = subjectActivity .getTxvSubjectTitle() ;
        titleView .setText(bundle .getString("name") +"【" +bundle.getString("remark") +"】") ;


    }


}
