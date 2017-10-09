package cn.zgnj.tiexi.shenyang.myaccount.utility;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import cn.zgnj.tiexi.shenyang.myaccount.R;

/**
 * Created by Administrator on 2017/10/9.
 */

public class DateSelectedDialog extends DialogFragment
{
    final Calendar ca = Calendar.getInstance();
    int mYear = ca.get(Calendar.YEAR);
    int mMonth = ca.get(Calendar.MONTH);
    int mDay = ca.get(Calendar.DAY_OF_MONTH);
    private Button mBtnSelect ;
    private Context mCtSelect;


    public void setBtnSelect(Button btnSelect)
    {
        mBtnSelect = btnSelect;
    }

    public String getDate1()
    {
        return mDate;
    }

    String mDate;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.layout_dateselected , null);
        mBtnSelect =(Button)view .findViewById(R.id .btnDateSelect ) ;
        mBtnSelect .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                
            }
        }) ;
        return new DatePickerDialog(mCtSelect , mdateListener, mYear, mMonth, mDay);
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            setDate();
        }
    };
    void setDate()
    {
        mDate =(new StringBuffer().append(mMonth).append("-").append(mDay).append("-").append(mYear).append(" ")).toString() ;
    }
}
