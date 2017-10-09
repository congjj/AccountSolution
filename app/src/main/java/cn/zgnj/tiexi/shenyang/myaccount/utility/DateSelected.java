package cn.zgnj.tiexi.shenyang.myaccount.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.zgnj.tiexi.shenyang.myaccount.R;


/**
 * Created by CJJ on 2017/10/9.
 */

public class DateSelected extends View
{
    private TextView mTvTitle;
    private TextView mTvDateValue;
    private Button mBtnSelect;
    private Context _context;


    public DateSelected (Context context)
    {
        this(context,null,0);
    }

    public DateSelected (Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public DateSelected(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dateselected ,null,true);
        mBtnSelect =(Button)view.findViewById(R.id .btnSelectDate) ;
        mTvTitle =(TextView )view.findViewById(R.id .tvTitile);
        mTvDateValue =(TextView)view.findViewById(R.id .tvDateValue) ;
    }





}
