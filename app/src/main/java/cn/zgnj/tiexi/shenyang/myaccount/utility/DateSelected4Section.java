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

    private Calendar mFromCa ;
    private Calendar mToCa;
    private Button mBtnFrom;
    private Button mBtnTo;
    private TextView mTxvTile;

//    private int mFromYear ;
//    private int mFromMonth ;
//    private int mFromDay ;
//    private int mToYear ;
//    private int mToMonth ;
//    private int mToDay ;

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
        mFromCa = Calendar.getInstance(Locale.CHINA);
        mToCa = Calendar.getInstance(Locale.CHINA);
        mFromCa .add(Calendar .MONTH ,-1) ;

        mBtnFrom =(Button)view.findViewById(R.id.btnFrom) ;
        mBtnTo =(Button)view.findViewById(R.id.btnTo) ;
        mTxvTile =(TextView)view. findViewById(R.id .textView10 ) ;

//        mFromYear =mFromCa .get(Calendar .YEAR);
//        mFromMonth =mFromCa .get(Calendar .MONTH);
//        mFromDay=mFromCa .get(Calendar.DAY_OF_MONTH) ;
//        mToYear =mToCa .get(Calendar .YEAR);
//        mToMonth =mToCa .get(Calendar .MONTH);
//        mToDay =mToCa .get(Calendar.DAY_OF_MONTH);
        StringBuffer fromdate =new StringBuffer().append(mFromCa .get(Calendar .YEAR)).append("-") .append(mFromCa .get(Calendar .MONTH)+1)
                .append("-") .append(mFromCa .get(Calendar.DAY_OF_MONTH)) ;
        StringBuffer todate =new StringBuffer().append(mToCa .get(Calendar .YEAR)).append("-") .append(mToCa .get(Calendar .MONTH)+1)
                .append("-") .append(mToCa .get(Calendar.DAY_OF_MONTH)) ;

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
            new DatePickerDialog(context ,mfromDateListener,mFromCa .get(Calendar .YEAR),mFromCa .get(Calendar .MONTH) ,mFromCa .get(Calendar.DAY_OF_MONTH)).show();
        }
        else if(btn.getId()==R.id .btnTo)
        {
            new DatePickerDialog(context ,mtoDateListener,mToCa .get(Calendar .YEAR),mToCa .get(Calendar .MONTH) ,mToCa .get(Calendar.DAY_OF_MONTH) ).show();
        }
    }

    private DatePickerDialog.OnDateSetListener mfromDateListener=new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {
            mFromCa .set(view.getYear() ,view.getMonth() ,view.getDayOfMonth()) ;
            mBtnFrom .setText(new StringBuffer() .append(view.getYear()) .append("-") .append(view.getMonth() +1).append("-") .append(view.getDayOfMonth())) ;
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
            mToCa .set(view.getYear() ,view.getMonth() ,view .getDayOfMonth());
            mBtnTo .setText(new StringBuffer() .append(view.getYear() ) .append("-") .append(view.getMonth() +1).append("-") .append(view .getDayOfMonth())) ;
            if(SetOnAfterSelectedDateListener !=null)
            {
                SetOnAfterSelectedDateListener.AfterSelectedDate(R.id .btnTo ,GetFromDate(), GetToDate());
            }
        }
    };

    public Calendar GetFromDate()
    {
        return mFromCa;
    }

    public Calendar GetToDate()
    {
        return mToCa ;
    }

    public void SetTitle(String title)
    {
        this.mTxvTile.setText(title) ;
    }



}
