package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.List;

/**
 * Created by Administrator on 2017/10/14.
 */

public class BillsitemAdapter extends RecyclerView.Adapter<BillsitemAdapter.BillsItemView>
{
    Context mContext ;
    List<Bitmap>mBillsList;
    LinearLayout.LayoutParams mLayoutParams;

    /**
     * 初始化  layout 默认：width=MATCH_PARENT ; height=MATCH_PARENT
     * @param billsList
     * @param showClassConText
     */
    public BillsitemAdapter (List<Bitmap> billsList, Context showClassConText)
    {
        mContext =showClassConText ;
        mBillsList =billsList;
        mLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                ,LinearLayout.LayoutParams.MATCH_PARENT);
    }

    /**
     * 初始化
     * @param billsList
     * @param showClassConText
     * @param layoutParams
     */
    public BillsitemAdapter (List<Bitmap> billsList, Context showClassConText,LinearLayout.LayoutParams layoutParams)
    {
        mContext =showClassConText ;
        mBillsList =billsList;
        mLayoutParams =layoutParams ;
    }

    @Override
    public BillsitemAdapter.BillsItemView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        BillsitemAdapter.BillsItemView holder =new BillsitemAdapter.BillsItemView(LayoutInflater.from(mContext ).inflate(
                R.layout .adapter_billsitem ,parent ,false)) ;
        return holder;
    }

    @Override
    public void onBindViewHolder(BillsitemAdapter.BillsItemView holder , int posision)
    {
        holder .mImageBills.setImageBitmap(mBillsList .get(posision)) ;
        holder .mLinearLayout .setLayoutParams(mLayoutParams) ;
    }

    /**
     * 获取项目个数
     * @return
     */
    @Override
    public int getItemCount()
    {
        return mBillsList.size();
    }

    /**
     * 清空
     */
    public void setClear()
    {
        mBillsList .clear() ;
    }



    public class BillsItemView extends RecyclerView.ViewHolder
    {
        ImageView  mImageBills;
        LinearLayout mLinearLayout ;
        public BillsItemView (View view)
        {
            super(view);
            mImageBills =(ImageView)view.findViewById(R.id .imvBillsItem ) ;
            mLinearLayout =(LinearLayout)view.findViewById(R.id .linearFrom) ;
        }
    }
}
