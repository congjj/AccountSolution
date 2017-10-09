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

import cn.zgnj.tiexi.shenyang.myaccount.R;


/**
 * Created by CJJ on 2017/10/9.
 */

public class DateSelected extends LinearLayout
{
    private TextView mTvTitle;
    private TextView mTvDateValue;
    private Button mBtnSelect;




    private static  Context mContext ;

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
     * 获取本机时间
     * @return
     */
    public Date getNow()
    {
        return new Date() ;
    }

    /**
     * 设置标题
     * @param text
     */
    public void setTitleText(String text)
    {
        mTvTitle.setText(text) ;
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        mTvDateValue .setText(df.format(getNow())) ;

    }








}
