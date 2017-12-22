package cn.zgnj.tiexi.shenyang.myaccount;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cjj.tiexi.shenyang.library.xloading.xloading;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBILL;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTLIST;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTSUBJECT;
import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;
import cn.zgnj.tiexi.shenyang.myaccount.utility.DateSelected;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Permissionhelper;
import cn.zgnj.tiexi.shenyang.myaccount.utility.PermissionsChecker;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Toolkit;
import cn.zgnj.tiexi.shenyang.myaccount.webservice.UploadAccountItem;

public class DoaccountActivity extends AppCompatActivity
{
    private List<Uri>billItemPathlist;
    private List<Bitmap> billsItemlist;
    private BillsitemAdapter mAdapter;
    private final int CAMERA_REQUEST_CODE = 100;
    private final int W_EXTERNAL_STORAGE = 101;
    private final int R_EXTERNAL_STORAGE = 101;
    private final int RETURN_CREATE_ACCOUNTSUBJECT=300;
    private final int RETURN_CREATE_ACCOUNTBOOK=200;
    private long mUserID;
    private ACCOUNTBOOK mACCOUNTBOOK ;
    private ACCOUNTSUBJECT mACCOUNTSUBJECT;
    Uri mUri=null;

    private void Load(Intent intent, Bundle savedInstanceState)
    {
        mUserID = getIntent().getLongExtra("sendUserID",-1) ;
        loadBookTypelist(USERINFO.getOne(mUserID).getACCOUNTBOOKList()) ;

        billsItemlist = new ArrayList<Bitmap>();
        billItemPathlist =new ArrayList<Uri>() ;
        mAdapter = new BillsitemAdapter(billsItemlist, this);
        doCaremaSuccess();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        mRcvPiclist.setLayoutManager(linearLayoutManager);

    }

    private void CreateIitem(View v)
    {
        if (!verify()) return;
        ACCOUNTSUBJECT subject = (ACCOUNTSUBJECT) this.mSpnSubject.getSelectedItem();
        String accountName = this.mEdtName.getText().toString();
        String accountCount = this.mEditCount.getText().toString();
        String accountPrice = this.mEditPrice.getText().toString();
        String accountRemark = this.mEdtRemark.getText().toString();
        boolean ischeck = this.mIsCheckAccount.isChecked();
        Date createTime = mDateSelected.getNow().getTime();
        Date accountTime = mDateSelected.getSelectDate().getTime();
        String itmeID = java.util.UUID.randomUUID().toString();
        IModelHelper accountItem = new ACCOUNTLIST(subject, itmeID, accountName, Double.parseDouble(accountCount),
                Double.parseDouble(accountPrice), ischeck, true, createTime, accountTime, false, accountRemark);
        if (accountItem._Insert() > 0)
        {
            if (mAdapter.getItemCount() > 0)
            {
                ACCOUNTLIST temp = ACCOUNTLIST.getOne(itmeID);

                int index = 1;
                for (Bitmap bitmap : billsItemlist)
                {
                    Uri tempUri=billItemPathlist .get(index-1);
                    new ACCOUNTBILL(temp, java.util.UUID.randomUUID().toString(), index,
                            Toolkit .gZip(Toolkit.bitmap4byte(bitmap)),tempUri,true ,true)._Insert();
                    index ++;
                }
            }
            success();
        }
    }

    private void StartCamera(View v)
    {
        //无权限是设置权限
        if (new PermissionsChecker(this).lacksPermissions(Manifest.permission.CAMERA))
        {
            //设置权限
            Permissionhelper.startActivityForResult(this, CAMERA_REQUEST_CODE,
                    Manifest.permission.CAMERA);
        }
        if (new PermissionsChecker(this).lacksPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            //写入存储权限
            Permissionhelper.startActivityForResult(this, W_EXTERNAL_STORAGE ,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (new PermissionsChecker(this).lacksPermissions(Manifest.permission.READ_EXTERNAL_STORAGE ))
        {
            //读存储权限
            Permissionhelper.startActivityForResult(this, R_EXTERNAL_STORAGE ,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        String filename ="Img"+ Long.toString(System.currentTimeMillis());
        mUri =Toolkit .startCamera4filePath(this,filename , Activity.DEFAULT_KEYS_DIALER) ;
    }


    private void StartCamera_finished(Intent data)
    {
        try
        {
            Bitmap b=null;
            if(mUri==null)
            {
                Bundle extras = data.getExtras();
                b = (Bitmap) extras.get("data");
                billItemPathlist .add(data .getData()) ;
            }
            else
            {
                b = Toolkit .getBitmap4Uri4zip(this,800f,480,100,mUri );
                billItemPathlist .add(mUri) ;
            }
            if(b==null)
                return ;
            billsItemlist.add(b);
            mAdapter = new BillsitemAdapter(billsItemlist, this);
            mRcvPiclist.setAdapter(mAdapter);
            doCaremaSuccess();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    private void ShowAccountReport(View v)
    {
        Intent i=new Intent(this  ,AccountreportActivity .class );
        Bundle bundle = new Bundle() ;
        bundle.putLong("book_ID",mACCOUNTBOOK .getId()) ;
        bundle .putLong("user_ID",mUserID) ;
        i.putExtra("sendBookID",bundle);
        startActivity(i);
    }

    private void UploadAccount_Click(View v)
    {
        final Dialog mTestDialog;
        USERINFO userinfo =USERINFO .getOne(mUserID) ;
        final String serUrl=//"http://172.16.40.189:9981/MyAccount/AccountManager.asmx";
                userinfo .getSERVERURL() ;
        // UploadAccountItem webser=new UploadAccountItem(serUrl ,"Test");
        UploadAccountItem webserTest=new UploadAccountItem(serUrl) ;
        mTestDialog = xloading.showWaitDialog(DoaccountActivity.this,"上传服务器连接中……",false  ,false  );
        webserTest .RunService("Test",null) ;
        webserTest .SetOnAfterServiceRunResult =new UploadAccountItem.AfterServiceRunResultListener()
        {
            @Override
            public void RunAfterResult(String methodName, boolean isSuccess, Bundle bundle)
            {
                if(methodName .equals("Test"))
                {
                    xloading .closeDialog(mTestDialog) ;
                    if(isSuccess)
                    {
                        String url = bundle .getString("Url") ;
                        UploadAccountItem webserUpLoad_Mobile=new UploadAccountItem(url) ;
                        final Dialog mUploadItem = xloading.showWaitDialog(DoaccountActivity.this,"账目上传中……",false  ,false  );
                        Bundle bookid = new Bundle() ;
                        bookid .putLong("bookid",mACCOUNTBOOK.getId()) ;
                        webserUpLoad_Mobile .RunService("UpLoad_Mobile",bookid) ;
                        webserUpLoad_Mobile .SetOnAfterServiceRunResult =new UploadAccountItem.AfterServiceRunResultListener()
                        {
                            @Override
                            public void RunAfterResult(String methodName, boolean isSuccess, Bundle bundle)
                            {
                                if(methodName .equals("UpLoad_Mobile"))
                                {
                                    xloading .closeDialog(mUploadItem) ;
                                    if(isSuccess )
                                    {
                                        final String []uuIDlist =bundle .getStringArray("uuIDlist") ;
                                        Toast.makeText(DoaccountActivity.this, "成功上传账目数据【"+uuIDlist.length +"】条", Toast.LENGTH_LONG).show();
                                        if(uuIDlist .length ==0)
                                            return ;
                                        final String url = bundle .getString("Url") ;
                                        final UploadAccountItem webser=new UploadAccountItem(url);
                                        Bundle bundleuuIDlist = new Bundle() ;
                                        bundleuuIDlist .putStringArray("uuIDlist",uuIDlist) ;
                                        final Dialog mUploadItemPic = xloading.showWaitDialog(DoaccountActivity.this,"账目票据上传中……",false  ,false  );
                                        webser .RunService("UpLoadBillsPic_Mobile",bundleuuIDlist) ;
                                        webser .SetOnAfterServiceRunResult =new UploadAccountItem.AfterServiceRunResultListener()
                                        {
                                            @Override
                                            public void RunAfterResult(String methodName, boolean isSuccess, Bundle bundle)
                                            {
                                                if(methodName .endsWith("UpLoadBillsPic_Mobile") )
                                                {
                                                    xloading .closeDialog(mUploadItemPic) ;
                                                    if(isSuccess)
                                                    {
                                                        int count = bundle .getInt("count") ;
                                                        Toast.makeText(DoaccountActivity.this, "成功上传账目票据【"+count +"】张", Toast.LENGTH_LONG).show();
                                                    }
                                                    else
                                                    {
                                                        String returninfo=bundle .getString("returninfo") ;
                                                        Toast.makeText(DoaccountActivity.this, "账目票据上传失败【"+returninfo +"】", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }
                                        };
                                    }
                                    else
                                    {
                                        String returninfo=bundle .getString("returninfo") ;
                                        Toast.makeText(DoaccountActivity.this, "账目上传失败【"+returninfo +"】", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        };
                    }
                    else
                    {
                        Intent i = new Intent(DoaccountActivity.this, SettingwebserviceActivity.class);
                        Bundle SetUrlBundle = new Bundle();
                        SetUrlBundle.putLong("userID", mUserID);
                        i.putExtra("scanQRcode", SetUrlBundle);
                        startActivity(i);
                    }
                }

            }
        };
    }

    private void CreateAccountSubject()
    {
        if(mACCOUNTBOOK ==null)
        {
            Toast.makeText(this, "请选择账簿或创建一个账簿！", Toast.LENGTH_LONG).show();
            return;
        }
        Intent i=new Intent(this,SubjectActivity .class );
        Bundle bundle = new Bundle() ;
        bundle.putString("name",mACCOUNTBOOK .getNAME()) ;
        bundle .putString("remark",mACCOUNTBOOK .getREMARK()) ;
        bundle .putLong("book_ID",mACCOUNTBOOK  .getId()) ;
        i.putExtra("sendBookType",bundle);
        startActivityForResult(i,RETURN_CREATE_ACCOUNTSUBJECT) ;
        //startActivity(i);
    }

    private void AccountSubjectCreate_Closed(Intent data)
    {
        List<ACCOUNTSUBJECT> list = ACCOUNTSUBJECT.getList4Book(mACCOUNTBOOK .getId());
        loadBookSubjectlist(list);
    }

    private void CreateAccountbook()
    {
        Intent i=new Intent(this,BookcreateActivity.class );
        Bundle bundle = new Bundle() ;
        bundle .putLong("user_ID",mUserID) ;
        i.putExtra("sendBookType",bundle);
        startActivityForResult(i,RETURN_CREATE_ACCOUNTBOOK) ;
    }

    private void AccountBookCreate_Closed(Intent data,int resultCode)
    {
        loadBookTypelist(USERINFO.getOne(mUserID).getACCOUNTBOOKList()) ;
        if(BookcreateActivity .SUCCESS ==resultCode )
        {
            Bundle bundle =data.getBundleExtra("booklistid");
            int booklistid =Integer .parseInt(bundle .getString("index") ) ;
            mSpinnerTitle.setSelection(booklistid) ;
        }
    }

    private void AccountBook_ItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        ACCOUNTBOOK accountbook =(ACCOUNTBOOK)  this.mSpinnerTitle .getSelectedItem() ;
        if(accountbook ==null)
            return ;
        mACCOUNTBOOK =accountbook ;
        List<ACCOUNTSUBJECT> list = ACCOUNTSUBJECT.getList4Book(mACCOUNTBOOK .getId());
        loadBookSubjectlist(list);
    }

    private void BookSubject_ItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        ACCOUNTSUBJECT accountsubject =(ACCOUNTSUBJECT )this.mSpnSubject .getSelectedItem() ;
        if(accountsubject ==null)
            return ;
        mACCOUNTSUBJECT  =accountsubject ;
    }

    private  void loadBookTypelist(List<ACCOUNTBOOK > booklist)
    {
        ArrayAdapter<ACCOUNTBOOK> adp=new ArrayAdapter<ACCOUNTBOOK>(this , R.layout.support_simple_spinner_dropdown_item,booklist);
        mSpinnerTitle .setAdapter(adp);
    }

    private void loadBookSubjectlist(List<ACCOUNTSUBJECT> booksubjectlist)
    {
        ArrayAdapter<ACCOUNTSUBJECT> adp = new ArrayAdapter<ACCOUNTSUBJECT>(this, R.layout.support_simple_spinner_dropdown_item, booksubjectlist );
        this.mSpnSubject.setAdapter(adp);
    }



    private void doCaremaSuccess()
    {
        //没有图片隐藏图片显示区域
        if (mAdapter.getItemCount() == 0)
        {
            this.mRcvPiclist.setVisibility(View.GONE);
        } else
        {
            this.mRcvPiclist.setVisibility(View.VISIBLE);
        }
    }


    //判断记账填写是否合法
    private boolean verify()
    {
        if (this.mEdtName.getText().toString().trim().length() == 0 && mAdapter.getItemCount() == 0)
        {
            Toast.makeText(this, "记账名称或拍照必须必须填写其一！", Toast.LENGTH_LONG).show();
            return false;
        } else if (this.mEditPrice.getText().toString().trim().length() == 0)
        {
            Toast.makeText(this, "单价必须填写！", Toast.LENGTH_LONG).show();
            return false;
        } else if (this.mEditCount.getText().toString().trim().length() == 0)
        {
            Toast.makeText(this, "数量必须填写！", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(mACCOUNTBOOK ==null)
        {
            Toast.makeText(this, "记账簿不能为空！", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(mACCOUNTSUBJECT ==null )
        {
            Toast.makeText(this, "记账科目不能为空！", Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            return true;
        }
    }


    private void success()
    {
        this.mEdtName.setText("");
        this.mEditCount.setText("");
        this.mEditPrice.setText("");
        this.mEdtRemark.setText("");
        mAdapter.setClear();
        doCaremaSuccess();
    }


    //region description 初始化
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doaccount);
        this.InitializeComponent(savedInstanceState);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private Toolbar toolbar;
    private Button mBtnAccount;
    private ImageButton mBtnCamera;
    private RecyclerView mRcvPiclist;

    private Button mBtnAccountCheck;
    private Button mBtnAccountReport;
    private EditText mEditPrice;
    private EditText mEditCount;
    private EditText mEdtRemark;
    private EditText mEdtName;
    private Spinner  mSpinnerTitle;
    private Spinner mSpnSubject;
    private DateSelected mDateSelected;
    private CheckBox mIsCheckAccount;


    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        LoadView();
        //载入时发生
        Load(getIntent(), savedInstanceState);
        setSupportActionBar(toolbar);

        //控件时间生成
        this.mBtnAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateIitem(v);
            }
        });
        this.mBtnCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StartCamera(v);
            }
        });
        this.mBtnAccountReport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ShowAccountReport(v);
            }
        });
        this.mBtnAccountCheck .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UploadAccount_Click(v);
            }
        }) ;
        this.mSpinnerTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                AccountBook_ItemSelected(parent ,view,position ,id );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        }) ;
        this.mSpnSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                BookSubject_ItemSelected(parent ,view,position ,id );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        }) ;
    }


    private void LoadView()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        this.mSpnSubject = (Spinner) findViewById(R.id.spSubject_con);
        this.mSpinnerTitle = (Spinner) findViewById(R.id.spBookName_con);
        this.mEditCount = (EditText) findViewById(R.id.edtCount_con);
        this.mEditPrice = (EditText) findViewById(R.id.edtPrice_con);
        this.mEdtRemark = (EditText) findViewById(R.id.edtRemark_con);
        this.mEdtName = (EditText) findViewById(R.id.edtName_con);
        this.mBtnAccount = (Button) findViewById(R.id.btnAccount_con);
        this.mBtnAccountCheck = (Button) findViewById(R.id.btnAccountItemCheck_con);
        this.mBtnAccountReport = (Button) findViewById(R.id.btnAccountReport_con);
        this.mBtnCamera = (ImageButton) findViewById(R.id.ibtnCamera_con);
        mDateSelected = (DateSelected) findViewById(R.id.dateSelected_con);
        this.mRcvPiclist = (RecyclerView) findViewById(R.id.rcvPiclist_con);
        this.mIsCheckAccount = (CheckBox) findViewById(R.id.chkAccountCheck_con);
        this.mBtnAccountReport = (Button) findViewById(R.id.btnAccountReport_con);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doaccount, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.itemaccountsubject)
        {
            CreateAccountSubject();
            return true;
        }

        if (id == R.id.itemaddaccountbook)
        {
            CreateAccountbook();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(RETURN_CREATE_ACCOUNTSUBJECT ==requestCode)
        {
            AccountSubjectCreate_Closed(data);
        }
        if(RETURN_CREATE_ACCOUNTBOOK ==requestCode)
        {
            AccountBookCreate_Closed(data,resultCode );
        }
        //拍照权限申请拒绝后
        if (CAMERA_REQUEST_CODE == requestCode && resultCode == Permissionhelper.PERMISSIONS_DENIED)
        {
            finish();
        }
        if (W_EXTERNAL_STORAGE  == requestCode && resultCode == Permissionhelper.PERMISSIONS_DENIED)
        {
            finish();
        }
        if (R_EXTERNAL_STORAGE == requestCode && resultCode == Permissionhelper.PERMISSIONS_DENIED)
        {
            finish();
        }
        //拍照后返回照片
        if (Activity.DEFAULT_KEYS_DIALER == requestCode)
        {
            StartCamera_finished(data);
        }

    }


    //endregion
}
