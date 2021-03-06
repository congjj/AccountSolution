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
    List<String> mDatasName;
    List<String> mDatasRemark;
    List <String>mDatasOut;
    Context mContext ;
    public SubjectItmeAdapter (List <String>subjectListName,List<String > subjectListRemark,
                               List<String>subjectListOut,Context showClassConText)
    {
        this.mDatasName =subjectListName;
        this.mDatasRemark =subjectListRemark ;
        this.mDatasOut =subjectListOut ;
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
        holder .mTextView .setText(mDatasName  .get(posision)) ;
        holder .mTextView1 .setText(mDatasRemark  .get(posision)) ;
        holder .mTextView2 .setText(mDatasOut  .get(posision)) ;
    }

    @Override
    public int getItemCount()
    {
        return mDatasName .size();
    }


    public class SubjectItemView extends RecyclerView.ViewHolder
    {
        TextView mTextView ;
        TextView mTextView1;
        TextView mTextView2;
        public SubjectItemView (View view)
        {
            super(view);
            mTextView =(TextView)view.findViewById(R.id .item_subjectcode   ) ;
            mTextView1 =(TextView)view.findViewById(R.id .item_subjectremark    ) ;
            mTextView2 =(TextView)view.findViewById(R.id .item_subjectout    ) ;
        }
    }
}
