package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.List;

/**
 * Created by Administrator on 2017/10/14.
 */

public class BillsitemAdapter extends RecyclerView.Adapter<BillsitemAdapter.BillsItemView>
{
    Context mContext ;
    List<Bitmap>mBillsList;
    public BillsitemAdapter (List<Bitmap> billsList, Context showClassConText)
    {
        mContext =showClassConText ;
        mBillsList =billsList;
    }

    @Override
    public BillsitemAdapter.BillsItemView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        BillsitemAdapter.BillsItemView holder =new BillsitemAdapter.BillsItemView(LayoutInflater.from(mContext ).inflate(
                R.layout .adapter_billsitem ,parent ,false
        ) ) ;
        return holder;
    }

    @Override
    public void onBindViewHolder(BillsitemAdapter.BillsItemView holder , int posision)
    {
        holder .mImageBills.setImageBitmap(mBillsList .get(posision)) ;
    }

    @Override
    public int getItemCount()
    {
        return mBillsList.size();
    }


    public class BillsItemView extends RecyclerView.ViewHolder
    {
        ImageView  mImageBills;
        public BillsItemView (View view)
        {
            super(view);
            mImageBills =(ImageView)view.findViewById(R.id .imvBillsItem ) ;
        }
    }
}
