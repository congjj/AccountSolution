package cn.zgnj.tiexi.shenyang.myaccount;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by CJJ on 2017/11/15.
 */

public class ReprotitemlistAdapter  extends RecyclerView.Adapter<ReprotitemlistAdapter.ReprotitemlistView>
{


    List<String> mItemlist;
    List<String> mItemRemarklist;
    List <String>mCountlist;
    List <Date>mDatelist;
    List <String>mIschecklist;
    List<String> mUUIDlist;
    Context mContext ;

    public ReprotitemlistAdapter (List<String>itemIDlist , List<String> itemlist, List<String > itemRemarklist,
                                  List<String>countlist, List<Date>datelist, List<String> ischecklist, Context showClassConText)
    {
        this.mUUIDlist =itemIDlist ;
        this.mIschecklist =itemlist ;
        this.mItemlist =itemlist ;
        this.mItemRemarklist =itemRemarklist  ;
        this.mCountlist  =countlist ;
        this.mDatelist =datelist ;
        this.mIschecklist =ischecklist ;
        mContext =showClassConText ;
    }

    @Override
    public ReprotitemlistView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        ReprotitemlistAdapter .ReprotitemlistView holder =new  ReprotitemlistAdapter .ReprotitemlistView(LayoutInflater.from(mContext ).inflate(
                R.layout.adaper_reprotitmelist ,parent ,false
        ) ) ;
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ReprotitemlistView holder, int position)
    {
        holder .mTextView .setText(mItemlist  .get(position)) ;
        holder .mTextView1 .setText(mItemRemarklist  .get(position)) ;
        Float count =Float .parseFloat(mCountlist  .get(position));
        if(count >0)
        {
            holder .mTextView2 .setTextColor(R.color.colorBlue) ;
        }
        else
        {
            holder .mTextView2 .setTextColor(R.color.colorRed) ;
        }
        holder .mTextView2 .setText(count.toString()) ;
        holder.mTextView3.setText(new SimpleDateFormat("yyyy-MM-dd").format( mDatelist.get(position))) ;
        holder. mTextView4.setText(mIschecklist .get(position) ) ;
        holder .mTextView .setTag(mUUIDlist .get(position)) ;
        holder .mTextView1 .setTag(mUUIDlist  .get(position)) ;
        holder .mTextView2 .setTag(mUUIDlist .get(position)) ;
        holder.mTextView3.setTag(mUUIDlist .get(position)) ;
        holder. mTextView4.setTag(mUUIDlist .get(position) ) ;
    }

    @Override
    public int getItemCount()
    {
        return mUUIDlist.size();
    }


    public class ReprotitemlistView extends RecyclerView.ViewHolder
    {
        TextView mTextView ;
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;
        TextView mTextView4;
        public ReprotitemlistView (View view)
        {
            super(view);
            mTextView =(TextView)view.findViewById(R.id .rep_item  ) ;
            mTextView1 =(TextView)view.findViewById(R.id .rep_itemremark     ) ;
            mTextView2 =(TextView)view.findViewById(R.id .rep_count     ) ;
            mTextView3 =(TextView)view.findViewById(R.id .rep_date     ) ;
            mTextView4 =(TextView)view.findViewById(R.id .rep_ischeck    ) ;
        }
    }


}
