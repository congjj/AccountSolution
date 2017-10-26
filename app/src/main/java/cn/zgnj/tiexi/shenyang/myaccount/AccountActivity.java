package cn.zgnj.tiexi.shenyang.myaccount;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBILL;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTLIST;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTSUBJECT;
import cn.zgnj.tiexi.shenyang.myaccount.utility.DateSelected;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Permissionhelper;
import cn.zgnj.tiexi.shenyang.myaccount.utility.PermissionsChecker;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Toolkit;


public class AccountActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        this.InitializeComponent(savedInstanceState);
    }

    private long accountBookID;
    private final  int CAMERA_REQUEST_CODE =100;
    private List<Bitmap> billsItemlist ;
    private BillsitemAdapter mAdapter;
    /**
     * 载入时发生
     *
     * @param intent
     * @param savedInstanceState
     */
    private void Load(Intent intent, Bundle savedInstanceState)
    {
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("sendBookType");
        accountBookID = bundle.getLong("book_ID");
        mTvTitle .setText(bundle .getString("name")+"【"+bundle .getString("remark") +"】" ) ;
        List<ACCOUNTSUBJECT> list = ACCOUNTSUBJECT.getList4Book(accountBookID);
        loadBookTypelist(list) ;
        billsItemlist =new ArrayList<>();
        mAdapter =new BillsitemAdapter(billsItemlist ,this) ;
        doCaremaSuccess() ;
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        mRcvPiclist.setLayoutManager(linearLayoutManager);
    }

    /**
     * 开始拍照
     * @param v
     */
    private void StartCamera(View v)
    {
        //无权限是设置权限
        if(new PermissionsChecker(this).lacksPermissions(Manifest.permission.CAMERA))
        {
            //设置权限
            Permissionhelper.startActivityForResult(this, CAMERA_REQUEST_CODE,Manifest.permission.CAMERA); ;
        }
        Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照权限申请拒绝后
        if(CAMERA_REQUEST_CODE==requestCode  && resultCode == Permissionhelper.PERMISSIONS_DENIED)
        {
            finish() ;
        }
        //拍照后返回照片
        else if(Activity.DEFAULT_KEYS_DIALER==requestCode)
        {
            try
            {
                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");
                //take = b;
//                ImageView img = (ImageView)findViewById(R.id.imageView );
//                img.setImageBitmap(b);
                billsItemlist.add(b) ;
                mAdapter = new BillsitemAdapter(billsItemlist, this);
                mRcvPiclist.setAdapter(mAdapter);
                doCaremaSuccess();
            }
            catch(Exception e)
            {
            }
        }
    }


    /**
     * 生成一条记账
     * @param v
     */
    private void CreateIitem(View v)
    {
        if(!verify()) return ;
        ACCOUNTSUBJECT subject = (ACCOUNTSUBJECT)this.mSpnSubject.getSelectedItem();
        String accountName=this.mEdtName .getText().toString();
        String accountCount=this.mEditCount .getText() .toString() ;
        String accountPrice =this.mEditPrice .getText() .toString() ;
        String accountRemark=this.mEdtRemark .getText() .toString() ;
        boolean ischeck =this.mIsCheckAccount.isChecked() ;
        Date createTime = mDateSelected .getNow().getTime();
        Date accountTime = mDateSelected .getSelectDate() .getTime() ;
        String itmeID=java.util.UUID.randomUUID().toString();
        IModelHelper accountItem = new ACCOUNTLIST(subject,itmeID,accountName , Double .parseDouble(accountCount),
                Double .parseDouble(accountPrice ),ischeck ,true ,createTime ,accountTime , false ,accountRemark  ) ;
        if(accountItem ._Insert() >0)
        {
            if(mAdapter .getItemCount() >0)
            {
                ACCOUNTLIST temp = ACCOUNTLIST.getOne(itmeID) ;
                int index=1;
                for(Bitmap bitmap :billsItemlist)
                {
                    new ACCOUNTBILL(temp ,java.util.UUID.randomUUID().toString(),index ++,
                        Toolkit .bitmap4byte( bitmap)
                            ,true) ._Insert() ;
                }
            }
            success() ;
        }
    }


    //查询账目
    private void ShowAccountReport(View v)
    {

    }

    private void loadBookTypelist(List<ACCOUNTSUBJECT> booklist)
    {
        ArrayAdapter<ACCOUNTSUBJECT> adp=new ArrayAdapter<ACCOUNTSUBJECT>(this , R.layout.support_simple_spinner_dropdown_item,booklist);
        this.mSpnSubject.setAdapter(adp);
    }

    private void success()
    {
        this.mEdtName .setText("") ;
        this.mEditCount .setText("") ;
        this.mEditPrice .setText("") ;
        this.mEdtRemark .setText("") ;
        mAdapter .setClear() ;
        doCaremaSuccess() ;
    }

    private void doCaremaSuccess()
    {
        //没有图片隐藏图片显示区域
        if(mAdapter .getItemCount()==0)
        {
            this.mRcvPiclist .setVisibility(View.GONE) ;
        }
        else
        {
            this.mRcvPiclist .setVisibility(View.VISIBLE);
        }
    }



    //判断记账填写是否合法
    private boolean verify()
    {
        if(this.mEdtName.getText().toString() .trim() .length() ==0)
        {
            Toast.makeText(this, "记账名称必须填写！", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(this.mEditPrice .getText() .toString() .trim().length()==0)
        {
            Toast.makeText(this, "单价必须填写！", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(this.mEditCount .getText() .toString() .trim() .length() ==0)
        {
            Toast.makeText(this, "数量必须填写！", Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            return true;
        }
    }



    //region description 初始化
    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        this.LoadView();
        //载入时发生
        Load(getIntent(), savedInstanceState);
        //控件时间生成
        this.mBtnAccount .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateIitem(v);
            }
        }) ;
        this.mBtnCamera .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StartCamera(v);
            }
        }) ;
        this.mBtnAccountReport .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ShowAccountReport(v);
            }
        }) ;
    }



    private Button mBtnDateSelect;
    private Button mBtnAccount;
    private ImageButton mBtnCamera;
    private RecyclerView mRcvPiclist;
    private Button mBtnAccountUpdate;
    private Button mBtnAccountCheck;
    private Button mBtnAccountReport;
    private EditText mEditPrice;
    private EditText mEditCount;
    private EditText mEdtRemark;
    private EditText mEdtName;
    private TextView mTvTitle;
    private Spinner mSpnSubject;
    private DateSelected mDateSelected ;
    private CheckBox mIsCheckAccount;
    private void LoadView()
    {
        this.mSpnSubject = (Spinner) findViewById(R.id.spSubject);
        this.mTvTitle = (TextView) findViewById(R.id.txvTitle);
        this.mEditCount = (EditText) findViewById(R.id.edtCount);
        this.mEditPrice = (EditText) findViewById(R.id.edtPrice);
        this.mEdtRemark = (EditText) findViewById(R.id.edtRemark);
        this.mEdtName = (EditText) findViewById(R.id.edtName);
        this.mBtnAccount = (Button) findViewById(R.id.btnAccount);
        this.mBtnAccountUpdate =(Button )findViewById(R.id.btnAccountUpdate) ;
        this.mBtnAccountCheck =(Button )findViewById(R.id.btnAccountCheck) ;
        this.mBtnAccountReport  =(Button )findViewById(R.id.btnAccountReport) ;
        this.mBtnCamera =(ImageButton)findViewById(R.id .ibtnCamera ) ;
        mDateSelected =(DateSelected)findViewById(R.id.dateSelected);
        this.mRcvPiclist =(RecyclerView)findViewById(R.id .rcvPiclist) ;
        this.mIsCheckAccount =(CheckBox)findViewById(R.id .chkAccountCheck) ;
        this.mBtnAccountReport=(Button)findViewById(R.id.btnAccountReport) ;
    }


    //endregion

}
