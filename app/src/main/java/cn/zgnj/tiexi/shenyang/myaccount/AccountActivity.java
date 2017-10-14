package cn.zgnj.tiexi.shenyang.myaccount;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTSUBJECT;
import cn.zgnj.tiexi.shenyang.myaccount.utility.DateSelected;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Permissionhelper;
import cn.zgnj.tiexi.shenyang.myaccount.utility.PermissionsChecker;


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
        //java.util.UUID.randomUUID().toString();
        List<ACCOUNTSUBJECT> list = ACCOUNTSUBJECT.getList4Book(accountBookID);
        loadBookTypelist(list) ;
        this.mRcvPiclist .setVisibility(View.GONE) ;
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
        this.mRcvPiclist .setVisibility(View.VISIBLE) ;
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
    }

    private  void loadBookTypelist(List<ACCOUNTSUBJECT> booklist)
    {
        ArrayAdapter<ACCOUNTSUBJECT> adp=new ArrayAdapter<ACCOUNTSUBJECT>(this , R.layout.support_simple_spinner_dropdown_item,booklist);
        this.mSpnSubject.setAdapter(adp);
    }

    //region description 初始化
    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        this.LoadView();
        Load(getIntent(), savedInstanceState);
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
    }


    //endregion

}
