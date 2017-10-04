package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/10/4.
 */

public class SubjectItmeAdapter extends RecyclerView.Adapter<SubjectItmeAdapter.SubjectItemView>
{
    List<String> mDatas;
    Context mContext ;
    public SubjectItmeAdapter (List <String>subjectList,Context showClassConText)
    {
        this.mDatas =subjectList;
        mContext =showClassConText ;
    }

    @Override
    public SubjectItemView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        SubjectItemView holder =new SubjectItemView(LayoutInflater.from(mContext ).inflate(
             R.layout .adapter_subjectitme ,parent ,false
        ) ) ;
        return holder;
    }

    @Override
    public void onBindViewHolder(SubjectItemView holder ,int posision)
    {
        holder .mTextView .setText(mDatas .get(posision)) ;
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }


    public class SubjectItemView extends RecyclerView.ViewHolder
    {
        TextView mTextView ;
        public SubjectItemView (View view)
        {
            super(view);
            mTextView =(TextView)view.findViewById(R.id .item_subject  ) ;
        }
    }
}
