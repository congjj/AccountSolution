package cn.zgnj.tiexi.shenyang.myaccount;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBILL;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTLIST;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Toolkit;

public class IteminfoActivity extends AppCompatActivity
{

    ACCOUNTLIST accountItem;
    private void Load(Intent intent, Bundle savedInstanceState)
    {
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("accountItem");
        String UUID = bundle.getString("accountItem_UUID") ;
        accountItem=ACCOUNTLIST .getOne(UUID) ;
        this.mEditTextRemark .setEnabled(false) ;
        this.mtxvSubjectItem.setText(accountItem .getSubjectName()) ;
        this .mtxvItemName .setText(accountItem .getNAME() ) ;
        this.mtxvPrice .setText(" "+String.valueOf(accountItem .getPrice())) ;
        this.mtxvCount .setText(" "+String.valueOf(accountItem .getCount())) ;
        this.mEditTextRemark .setText(accountItem .getREMARK()) ;
        String valuestring ="";
        if(accountItem .getIsOut())
        {
            valuestring ="支出 "+Math .abs(Double .parseDouble(accountItem .getValues()))+"=>";
        }
        else
        {
            valuestring ="收入 "+Math .abs(Double .parseDouble(accountItem .getValues()))+"=>";
        }
        this.mTextViewIsOut .setText(valuestring ) ;
        SimpleDateFormat df = new SimpleDateFormat(" "+"yyyy-MM-dd HH:mm");
        this.mTextViewCreateTime .setText(df.format(accountItem .getCREATETIME())) ;
        df = new SimpleDateFormat("yyyy-MM-dd");
        this.mTextViewAccountDate .setText(" "+df.format(accountItem .getACCOUNTTIME())) ;
        showItemImg(accountItem ,this.mCheckBoxImgNoZip.isChecked()) ;

    }

    private void ShowImg4NoZip(CompoundButton buttonView, boolean isChecked)
    {
        showItemImg(accountItem ,isChecked) ;
    }
    
    

    void showItemImg(ACCOUNTLIST accountItem,boolean NoZip)
    {
        List<Bitmap> billlist = new ArrayList<Bitmap> ();

        if(NoZip)
        {
            for (Uri temp : accountItem.getBillsPath())
            {
                Uri tem = temp;
                billlist.add(Toolkit.getBitmap4Uri(this, temp));
            }
        }
        else
        {
            for (byte[] temp : accountItem.getBills())
            {
                billlist.add(Toolkit.byte4bitmap(Toolkit.unGZip(temp)));
            }
        }
        LinearLayout.LayoutParams mLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                ,LinearLayout.LayoutParams.MATCH_PARENT);
        BillsitemAdapter mAdapter = new BillsitemAdapter(billlist, this,mLayoutParams);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(1);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }


    //region description
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iteminfo);
        this.InitializeComponent(savedInstanceState);
    }

    private void InitializeComponent(Bundle savedInstanceState)
    {
        //载入控件
        this.LoadView();

        //载入时发生
        Load(getIntent(), savedInstanceState);
        //控件事件
        this.SetListener();
    }

    TextView mtxvSubjectItem;
    TextView mtxvItemName;
    TextView mtxvCount;
    TextView mtxvPrice;
    TextView mTextViewIsOut;
    EditText mEditTextRemark;
    TextView mTextViewAccountDate;
    TextView mTextViewCreateTime;
    RecyclerView  mRecyclerView ;
    CheckBox mCheckBoxImgNoZip;
    private void LoadView()
    {
        this.mtxvCount =(TextView)findViewById(R.id .txvItemCount) ;
        this.mtxvSubjectItem =(TextView )findViewById(R.id .txvItemSubject) ;
        this.mtxvItemName =(TextView )findViewById(R.id.txvSubjectName) ;
        this.mtxvPrice =(TextView )findViewById(R.id .txvItemPrice ) ;
        this.mTextViewIsOut =(TextView)  findViewById(R.id .txvItemIsOut) ;
        this.mEditTextRemark =(EditText)findViewById(R.id .etxtItemRemark) ;
        this.mTextViewAccountDate =(TextView )findViewById(R.id .txvItemAccountdate) ;
        this.mTextViewCreateTime =(TextView )findViewById(R.id .txvItemCreateTime);
        this.mCheckBoxImgNoZip =(CheckBox) findViewById(R.id .chkImgNoZip) ;
        mRecyclerView =(RecyclerView )findViewById(R.id .RcvBillslist) ;
    }


    private void SetListener()
    {
        this.mCheckBoxImgNoZip .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                ShowImg4NoZip(buttonView ,isChecked);
            }
        }) ;
    }



    //endregion

}
