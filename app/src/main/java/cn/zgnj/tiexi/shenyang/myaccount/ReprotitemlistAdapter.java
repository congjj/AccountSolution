package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by CJJ on 2017/11/15.
 */

public class ReprotitemlistAdapter  extends RecyclerView.Adapter<ReprotitemlistAdapter.ReprotitemlistView>
{


    List<String> mItemlist;
    List<String> mItemRemarklist;
    List <String>mCountlist;
    List <String>mDatelist;
    List <String>mIschecklist;
    List<Long> mItemIDlist;
    Context mContext ;
    float outValues;
    float inValues;
    public ReprotitemlistAdapter (List<Long>itemIDlist , List<String> itemlist, List<String > itemRemarklist,
                                  List<String>countlist, List<String>datelist, List<String> ischecklist, Context showClassConText)
    {
        this.mItemIDlist =itemIDlist ;
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

    @Override
    public void onBindViewHolder(ReprotitemlistView holder, int position)
    {
        holder .mTextView .setText(mItemlist  .get(position)) ;
        holder .mTextView1 .setText(mItemRemarklist  .get(position)) ;
        Float count =Float .parseFloat(mCountlist  .get(position));
        if(count >0)
        {
            inValues +=count ;
            holder .mTextView2 .setText(count.toString()) ;
        }
        else
        {
            outValues +=count ;
            holder .mTextView2 .setText("-"+count.toString()) ;
        }

        holder.mTextView3.setText(mDatelist .get(position)) ;
        holder. mTextView4.setText(mIschecklist .get(position) ) ;

        holder .mTextView .setTag(mItemIDlist .get(position)) ;
        holder .mTextView1 .setTag(mItemIDlist  .get(position)) ;
        holder .mTextView2 .setTag(mItemIDlist .get(position)) ;
        holder.mTextView3.setTag(mItemIDlist .get(position)) ;
        holder. mTextView4.setTag(mItemIDlist .get(position) ) ;
    }

    @Override
    public int getItemCount()
    {
        return mItemIDlist.size();
    }

    public Float  getOutValuse()
    {
       return outValues ;
    }

    public Float getInValuse()
    {
        return inValues ;
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
