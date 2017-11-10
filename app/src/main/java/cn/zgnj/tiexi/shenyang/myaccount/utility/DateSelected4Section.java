package cn.zgnj.tiexi.shenyang.myaccount.utility;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by CJJ on 2017/11/9.
 */

public class DateSelected4Section extends LinearLayout
{

    private Calendar mFromCa = Calendar.getInstance(Locale.CHINA);
    private Calendar mToCa = Calendar.getInstance(Locale.CHINA);
    private Button mBtnFrom;
    private Button mBtnTo;
    private TextView mTxvTile;

    private int mFromYear ;
    private int mFromMonth ;
    private int mFromDay ;



    public DateSelected4Section(Context context)
    {
        this(context,null,0);
    }

    public DateSelected4Section(Context context, @Nullable AttributeSet attrs)
    {
        this(context,null,0);
    }

    public DateSelected4Section(final Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dateselected4section ,this ,true);
        mBtnFrom =(Button)findViewById(R.id.btnFrom) ;
        mBtnTo =(Button)findViewById(R.id.btnTo) ;
        mTxvTile =(TextView)  findViewById(R.id .textView10 ) ;
        StringBuffer fromdate =new StringBuffer().append(mFromCa .get(Calendar .YEAR)).append("-") .append(mFromCa .get(Calendar .MONTH)) .append("-") .append(mFromCa .get(Calendar.DAY_OF_MONTH)) ;
        StringBuffer todate =new StringBuffer().append(mToCa .get(Calendar .YEAR)).append("-") .append(mToCa .get(Calendar .MONTH)+1) .append("-") .append(mToCa .get(Calendar.DAY_OF_MONTH)) ;
        mBtnFrom .setText(fromdate) ;
        mBtnTo .setText(todate ) ;

        this.mBtnFrom .setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showSelectDateDialog( context,v);
            }
        }) ;
        this.mBtnFrom .setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showSelectDateDialog(context,v);
            }
        }) ;
    }


    private void showSelectDateDialog(Context context, View view )
    {
        Button btn = (Button) view;
        if( btn.getId()==R.id .btnFrom )
        {
            new DatePickerDialog(context ,mfromDateListener,mFromYear,mFromMonth ,mFromDay).show();
        }
        else if(btn.getId()==R.id .btnTo)
        {
            new DatePickerDialog(context ,mtoDateListener,mFromYear,mFromMonth ,mFromDay).show();
        }
    }

    private DatePickerDialog.OnDateSetListener mfromDateListener=new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {

        }
    };

    private DatePickerDialog.OnDateSetListener mtoDateListener=new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {

        }
    };

    public Calendar GetFromDate()
    {
        mFromCa .set(mFromYear ,mFromMonth ,mFromDay) ;
        return mFromCa;
    }


    public void SetTitle(String title)
    {
        this.mTxvTile .setText(title) ;
    }





}
