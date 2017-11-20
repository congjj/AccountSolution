package cn.zgnj.tiexi.shenyang.myaccount.utility;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Canvas;
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

    public interface AfterSelectedDateDialogListener
    {
        public void AfterSelectedDate(int btnID,Calendar fromCa,Calendar toCa);
    }

    public AfterSelectedDateDialogListener SetOnAfterSelectedDateListener;

    private Calendar mFromCa = Calendar.getInstance(Locale.CHINA);
    private Calendar mToCa = Calendar.getInstance(Locale.CHINA);
    private Button mBtnFrom;
    private Button mBtnTo;
    private TextView mTxvTile;

    private int mFromYear ;
    private int mFromMonth ;
    private int mFromDay ;
    private int mToYear ;
    private int mToMonth ;
    private int mToDay ;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

    }
    public DateSelected4Section (Context context)
    {
        this(context,null,0);
    }

    public DateSelected4Section (Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }


    public DateSelected4Section(final Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dateselected4section ,this ,true);
        mBtnFrom =(Button)view.findViewById(R.id.btnFrom) ;
        mBtnTo =(Button)view.findViewById(R.id.btnTo) ;
        mTxvTile =(TextView)view. findViewById(R.id .textView10 ) ;

        mFromYear =mFromCa .get(Calendar .YEAR);
        mFromMonth =mFromCa .get(Calendar .MONTH);
        mFromDay=mFromCa .get(Calendar.DAY_OF_MONTH) ;
        mToYear =mToCa .get(Calendar .YEAR);
        mToMonth =mToCa .get(Calendar .MONTH)+1;
        mToDay =mToCa .get(Calendar.DAY_OF_MONTH);
        StringBuffer fromdate =new StringBuffer().append(mFromYear).append("-") .append(mFromMonth) .append("-") .append(mFromDay) ;
        StringBuffer todate =new StringBuffer().append(mToYear ).append("-") .append(mToMonth) .append("-") .append(mToDay) ;

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
        this.mBtnTo .setOnClickListener(new OnClickListener()
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
            new DatePickerDialog(context ,mfromDateListener,mFromYear,mFromMonth -1,mFromDay).show();
        }
        else if(btn.getId()==R.id .btnTo)
        {
            new DatePickerDialog(context ,mtoDateListener,mToYear,mToMonth -1,mToDay ).show();
        }
    }

    private DatePickerDialog.OnDateSetListener mfromDateListener=new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {
            mFromYear =year;
            mFromMonth =month;
            mFromDay =dayOfMonth ;
            mBtnFrom .setText(new StringBuffer() .append(year ) .append("-") .append(month +1) .append("-") .append(dayOfMonth )) ;
            if(SetOnAfterSelectedDateListener!=null)
            {
                SetOnAfterSelectedDateListener.AfterSelectedDate(R.id .btnFrom ,GetFromDate(), GetToDate());
            }
        }
    };

    private DatePickerDialog.OnDateSetListener mtoDateListener=new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {
            mToYear =year;
            mToMonth =month ;
            mToDay =dayOfMonth ;
            mBtnTo .setText(new StringBuffer() .append(year ) .append("-") .append(month +1) .append("-") .append(dayOfMonth )) ;
            if(SetOnAfterSelectedDateListener !=null)
            {
                SetOnAfterSelectedDateListener.AfterSelectedDate(R.id .btnTo ,GetFromDate(), GetToDate());
            }
        }
    };

    public Calendar GetFromDate()
    {
        mFromCa .set(mFromYear ,mFromMonth ,mFromDay) ;
        return mFromCa;
    }

    public Calendar GetToDate()
    {
        mToCa .set(mToDay ,this.mToMonth ,mToDay);
        return mToCa ;
    }

    public void SetTitle(String title)
    {
        this.mTxvTile.setText(title) ;
    }



}
