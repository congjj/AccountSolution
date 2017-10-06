package cn.zgnj.tiexi.shenyang.myaccount;

import android.app.DatePickerDialog;
import android.content.Intent;

import android.view.View;

import java.util.Calendar;

/**
 * Created by CJJ on 2017/9/28.
 */

public class AccountActivityController
{
    static  final Calendar ca = Calendar.getInstance();
    static  int mYear = ca.get(Calendar.YEAR);
    static  int mMonth = ca.get(Calendar.MONTH);
    static  int mDay = ca.get(Calendar.DAY_OF_MONTH);
    public static void Load(Intent intent, AccountActivity accountActivity)
    {
         accountActivity .getTvDate() .setText(mYear +"-"+ mMonth +"-"+mDay ) ;
    }


    public static void SetAccountDate(AccountActivity accountActivity, View v)
    {
        //new DatePickerDialog()
    }
}
