package cn.zgnj.tiexi.shenyang.myaccount.utility;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.zgnj.tiexi.shenyang.myaccount.R;


/**
 * Created by CJJ on 2017/10/9.
 */

public class DateSelected extends LinearLayout
{
    private TextView mTvTitle;
    private TextView mTvDateValue;
    private Button mBtnSelect;
    private  Calendar mCa = Calendar.getInstance(Locale.CHINA);
    private int mYear ;
    private int mMonth ;
    private int mDay ;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

    }

    /**
     * 获取本机时间 注意：月份是从 0 月开始
     * @return
     */
    public Calendar getNow()
    {
        return Calendar.getInstance();
    }

    /**
     * 设置标题
     * @param text
     */
    public void setTitleText(String text)
    {
        mTvTitle.setText(text) ;
    }

    /**
     * 获取选择的日期 注意：月份是从 0 月开始
     * @return
     */
    public Calendar getSelectDate()
    {
        mCa .set(mYear ,mMonth ,mDay) ;
        return mCa;
    }

    public DateSelected (Context context)
    {
        this(context,null,0);
    }

    public DateSelected (Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public DateSelected(final Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_dateselected ,this ,true);
        mBtnSelect =(Button)view.findViewById(R.id .btnSelectDate) ;
        mTvTitle =(TextView )view.findViewById(R.id .tvTitile);
        mTvDateValue =(TextView)view.findViewById(R.id .tvDateValue) ;

        mYear = mCa.get(Calendar.YEAR);
        mMonth =mCa.get(Calendar .MONTH );
        mDay =mCa.get(Calendar .DAY_OF_MONTH );

        setDate() ;
        mBtnSelect.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showSelectDateDialog(context);
            }
        }) ;
    }

    private void showSelectDateDialog(Context context)
    {
        new DatePickerDialog(context ,mdateListener,mYear ,mMonth ,mDay).show();
    }


    //设置时间
    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            setDate() ;
        }
    };



    private void setDate()
    {
        mTvDateValue .setText(new StringBuffer().append(mYear).append("-").append(mMonth+1).append("-").append(mDay));
    }


}
