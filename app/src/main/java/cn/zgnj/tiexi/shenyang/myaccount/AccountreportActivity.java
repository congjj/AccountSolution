package cn.zgnj.tiexi.shenyang.myaccount;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTLIST;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTSUBJECT;
import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;
import cn.zgnj.tiexi.shenyang.myaccount.utility.DateSelected4Section;

import static cn.zgnj.tiexi.shenyang.myaccount.R.id.dateSelected;
import static cn.zgnj.tiexi.shenyang.myaccount.R.id.dsDate;
import static cn.zgnj.tiexi.shenyang.myaccount.R.id.rvItemList;
import static cn.zgnj.tiexi.shenyang.myaccount.R.id.textView;
import static cn.zgnj.tiexi.shenyang.myaccount.R.id.view_offset_helper;

public class AccountreportActivity extends AppCompatActivity
{

    public interface ItemClickListener
    {
        public void ItemClick(String UUID);
    }

    public ItemClickListener addOnItemClickListener;

    private ACCOUNTBOOK mACCOUNTBOOK ;

    //载入时发生
    private void Load(Intent intent, Bundle savedInstanceState)
    {
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("sendBookID");
        mACCOUNTBOOK =ACCOUNTBOOK .getItSelf(bundle.getLong("book_ID"));
        this.mTxvReprotBookName .setText("您的记账簿");

        List<ACCOUNTBOOK>booklist = USERINFO.getOne(bundle.getLong("user_ID")).getACCOUNTBOOKList();
        ArrayAdapter<ACCOUNTBOOK> adp=new ArrayAdapter<ACCOUNTBOOK>(this , R.layout.support_simple_spinner_dropdown_item,booklist);
        mSpnSubjectItem.setAdapter(adp);
    }


    private void spnSubjectItem_ItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        showReport() ;
    }

    private void mDateSelected4Section_AfterSelectedDate(int btnID, Calendar fromCa, Calendar toCa)
    {

        showReport() ;
    }

    private void reportItem_ItemClick(String uuid)
    {
        Intent i=new Intent(this  ,IteminfoActivity.class );
        Bundle bundle = new Bundle() ;
        bundle.putString("accountItem_UUID",uuid) ;
        i.putExtra("accountItem",bundle);
        startActivity(i);
    }


    private void showReport()
    {
        ACCOUNTBOOK accountbook=(ACCOUNTBOOK) mSpnSubjectItem .getSelectedItem() ;
        String bookid = accountbook .getId() .toString() ;
        List<String>mUUIDlist=new ArrayList<String>() ;
        List<String> mitmelist = new ArrayList<String>();
        List<String> mitemremarklist = new ArrayList<String>();
        List<String> mvalueslist = new ArrayList<String>();
        List<Date>mdatelist=new ArrayList<Date> ();
        List<String> ischecklist=new ArrayList<String>() ;
        Float inValues =Float .parseFloat("0") ;
        Float outValues =Float .parseFloat("0") ;

        Calendar fromTime = this.mDateSelected4Section .GetFromDate() ;
        Calendar  toTime=this.mDateSelected4Section .GetToDate() ;
        for(ACCOUNTLIST  temp:ACCOUNTLIST .GetSome(fromTime ,toTime ,bookid))
        {
            mUUIDlist .add(temp .getUUID()) ;
            mitmelist .add(temp.getSubjectName()) ;
            mitemremarklist .add(temp .getNAME()) ;
            if(Float .parseFloat(temp .getValues())>0)
            {
                inValues +=Float .parseFloat(temp .getValues());
            }
            else
            {
                outValues +=Float .parseFloat(temp .getValues());
            }
            mvalueslist .add(temp.getValues()) ;
            mdatelist .add(temp .getACCOUNTTIME() ) ;
            ischecklist .add(temp.getISCHECKED() ?"√":"！") ;
        }
        ReprotitemlistAdapter mAdapter = new ReprotitemlistAdapter(mUUIDlist,mitmelist,mitemremarklist,mvalueslist,mdatelist,ischecklist,this) ;
        RvItemList.setLayoutManager(new LinearLayoutManager(this));
        RvItemList.setAdapter(mAdapter);
        mTxvAllIn .setText("收入："+inValues) ;
        mTxvAllOut .setText("支出："+outValues*(-1)) ;
        Float  result = inValues + outValues ;
        mTxvAll .setText("合计："+ result .toString() ) ;

        mAdapter .SetOnItemClickListener =new ReprotitemlistAdapter.ItemClickListener()
        {
            @Override
            public void ItemClick(String UUID)
            {
                reportItem_ItemClick(UUID);
            }
        };
    }


    // region description 初始化

    private TextView mTxvReprotBookName ;
    private RecyclerView RvItemList;
    private Spinner mSpnSubjectItem;
    private DateSelected4Section mDateSelected4Section ;
    private TextView mTxvAllIn;
    private TextView mTxvAllOut;
    private TextView mTxvAll;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountreport);
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

    private void LoadView()
    {
        this.mTxvReprotBookName =(TextView)findViewById(R.id .txvReprotBookName) ;
        this.mSpnSubjectItem =(Spinner)findViewById(R.id.spnSubjectItem) ;
        this.mDateSelected4Section =(DateSelected4Section)findViewById(R.id .dsDate) ;
        this.RvItemList =(RecyclerView)findViewById(R.id .rvItemList) ;
        this.mTxvAllIn =(TextView)findViewById(R.id.txvInCount) ;
        this.mTxvAllOut =(TextView)findViewById(R.id.txvOutCount) ;
        this.mTxvAll =(TextView)findViewById(R.id.txvCount) ;

    }

    @SuppressLint("ClickableViewAccessibility")
    private void SetListener()
    {

        this.mSpnSubjectItem .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                spnSubjectItem_ItemSelected(parent,view,  position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        }) ;


        this.mDateSelected4Section.SetOnAfterSelectedDateListener =new DateSelected4Section.AfterSelectedDateDialogListener()
        {
            @Override
            public void AfterSelectedDate(int btnID, Calendar fromCa, Calendar toCa)
            {
                mDateSelected4Section_AfterSelectedDate( btnID,  fromCa, toCa);
            }
        };


    }

    //endregion

}
