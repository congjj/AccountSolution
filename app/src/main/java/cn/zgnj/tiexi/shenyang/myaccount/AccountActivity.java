package cn.zgnj.tiexi.shenyang.myaccount;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cjj.tiexi.shenyang.library.xloading.xloading;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBILL;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTLIST;
import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTSUBJECT;
import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;
import cn.zgnj.tiexi.shenyang.myaccount.utility.DateSelected;
import cn.zgnj.tiexi.shenyang.myaccount.utility.FileUtils;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Permissionhelper;
import cn.zgnj.tiexi.shenyang.myaccount.utility.PermissionsChecker;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Toolkit;
import cn.zgnj.tiexi.shenyang.myaccount.webservice.UploadAccountItem;


public class AccountActivity extends AppCompatActivity
{


    private long accountBookID;
    private final int CAMERA_REQUEST_CODE = 100;
    private final int W_EXTERNAL_STORAGE = 101;
    private final int R_EXTERNAL_STORAGE = 101;
    private List<Bitmap> billsItemlist;
    private List<Uri>billItemPathlist;
    private BillsitemAdapter mAdapter;
    private long userinfoID;

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
        userinfoID =bundle .getLong("user_ID") ;
        mTvTitle.setText(bundle.getString("name") + "【" + bundle.getString("remark") + "】");
        List<ACCOUNTSUBJECT> list = ACCOUNTSUBJECT.getList4Book(accountBookID);
        loadBookTypelist(list);
        billsItemlist = new ArrayList<Bitmap>();
        billItemPathlist =new ArrayList<Uri>() ;
        mAdapter = new BillsitemAdapter(billsItemlist, this);
        doCaremaSuccess();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        mRcvPiclist.setLayoutManager(linearLayoutManager);
    }

    Uri mUri=null;
    /**
     * 开始拍照
     *
     * @param v
     */
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
        mUri =Toolkit .startCamera4filePath(this,filename ,Activity.DEFAULT_KEYS_DIALER) ;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
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
    }

    private void UploadAccount_Click(View v)
    {
        final Dialog mTestDialog;
        USERINFO userinfo =USERINFO .getOne(userinfoID) ;
        final String serUrl=//"http://172.16.40.189:9981/MyAccount/AccountManager.asmx";
        userinfo .getSERVERURL() ;
       // UploadAccountItem webser=new UploadAccountItem(serUrl ,"Test");
        UploadAccountItem webserTest=new UploadAccountItem(serUrl) ;
        mTestDialog = xloading.showWaitDialog(AccountActivity.this,"上传服务器连接中……",false  ,false  );
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
                        final Dialog mUploadItem = xloading.showWaitDialog(AccountActivity.this,"账目上传中……",false  ,false  );
                        Bundle bookid = new Bundle() ;
                        bookid .putLong("bookid",accountBookID) ;
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
                                        Toast.makeText(AccountActivity.this, "成功上传账目数据【"+uuIDlist.length +"】条", Toast.LENGTH_LONG).show();
                                        if(uuIDlist .length ==0)
                                            return ;
                                        final String url = bundle .getString("Url") ;
                                        final UploadAccountItem webser=new UploadAccountItem(url);
                                        Bundle bundleuuIDlist = new Bundle() ;
                                        bundleuuIDlist .putStringArray("uuIDlist",uuIDlist) ;
                                        final Dialog mUploadItemPic = xloading.showWaitDialog(AccountActivity.this,"账目票据上传中……",false  ,false  );
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
                                                        Toast.makeText(AccountActivity.this, "成功上传账目票据【"+count +"】张", Toast.LENGTH_LONG).show();
                                                    }
                                                    else
                                                    {
                                                        String returninfo=bundle .getString("returninfo") ;
                                                        Toast.makeText(AccountActivity.this, "账目票据上传失败【"+returninfo +"】", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }
                                        };
                                    }
                                    else
                                    {
                                        String returninfo=bundle .getString("returninfo") ;
                                        Toast.makeText(AccountActivity.this, "账目上传失败【"+returninfo +"】", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        };
                    }
                    else
                    {
                        Intent i = new Intent(AccountActivity.this, SettingwebserviceActivity.class);
                        Bundle SetUrlBundle = new Bundle();
                        SetUrlBundle.putLong("userID", userinfoID);
                        i.putExtra("scanQRcode", SetUrlBundle);
                        startActivity(i);
                    }
                }

            }
        };
       // webser .SetOnAfterServiceRunSuccess =new

//        final UploadAccountItem webser=new UploadAccountItem(serUrl ,"Test");
//        new Thread()
//        {
//            @Override
//            public void run()
//            {
//                Message msg = new Message();
//                try
//                {
//                    if(webser.verify4ConnectRight())
//                    {
//                        msg.what = 1;
//                        Bundle bundle =new Bundle() ;
//                        bundle.putString("serUrl",serUrl) ;
//                        bundle.putLong("accountBookID",accountBookID) ;
//                        msg.setData(bundle) ;
//                        handler.sendMessage(msg);
//                    }
//                    else
//                    {
//                        msg.what = 0;
//                        handler.sendMessage(msg);
//                    }
//                }
//                catch (IOException e)
//                {
//                    msg.what = 0;
//                    handler.sendMessage(msg);
//                }
//                catch (XmlPullParserException e)
//                {
//                    msg.what = 0;
//                    handler.sendMessage(msg);
//                }
//            }
//        }.start();
    }

//    //上传账目处理
//    Handler handler = new Handler()
//    {
//        @Override
//        public void handleMessage(Message msg)
//        {
//            if(msg.what ==0)
//            {
//                Intent i = new Intent(AccountActivity.this, SettingwebserviceActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putLong("user_ID", userinfoID);
//                i.putExtra("scanQRcode", bundle);
//                startActivity(i);
//                //Toast.makeText(AccountActivity.this, "连接服务器失败", Toast.LENGTH_LONG).show();
//            }
//            else if(msg.what ==1)
//            {
//                Bundle bundle =msg .getData() ;
//                final String serUrl =bundle .getString("serUrl") ;
//                final long bookid = bundle .getLong("accountBookID") ;
//                final UploadAccountItem webser=new UploadAccountItem(serUrl ,"UpLoad_Mobile");
//
//                new Thread()
//                {
//                    @Override
//                    public void run()
//                    {
//                        Message msg = new Message();
//                        try
//                        {
//                            String[] uuIDlist = webser.uploadBooks4AccountItem(bookid);
//
//                                msg.what = 1;
//                                Bundle bundle =new Bundle() ;
//                                bundle.putStringArray("uuIDlist",uuIDlist) ;
//                                bundle .putLong("bookid", bookid) ;
//                                bundle .putString("serUrl",serUrl) ;
//                                msg.setData(bundle) ;
//                                handler1.sendMessage(msg);
//
//                        }
//                        catch (IOException e)
//                        {
//                            msg.what = 0;
//                            handler1.sendMessage(msg);
//                        }
//                        catch (XmlPullParserException e)
//                        {
//                            msg.what = 0;
//                            handler1.sendMessage(msg);
//                        }
//                    }
//                }.start();
//
//            }
//            else
//            {}
//        }
//    };




//    //成功后上传票据处理
//    Handler handler1 = new Handler()
//    {
//        @Override
//        public void handleMessage(Message msg)
//        {
//            if (msg.what == 0)
//            {
//                Toast.makeText(AccountActivity.this, "上传数据失败！", Toast.LENGTH_LONG).show();
//            }
//            if(msg.what ==1)
//            {
//                Bundle bundle =msg.getData() ;
//                final String []uuIDlist =bundle .getStringArray("uuIDlist") ;
//                Toast.makeText(AccountActivity.this, "成功上传【"+uuIDlist.length +"】条数据", Toast.LENGTH_LONG).show();
//
//                if(uuIDlist .length == 0)
//                    return ;
//                final String serUrl =bundle .getString("serUrl") ;
//                final long bookid =bundle .getLong("bookid") ;
//                final UploadAccountItem webser=new UploadAccountItem(serUrl ,"UpLoadBillsPic_Mobile");
//                new Thread()
//                {
//                    @Override
//                    public void run()
//                    {
//                        Message msg = new Message();
//                        try
//                        {
//
//                            int count = webser.uploadBillPic(uuIDlist) ;
//
//                            msg.what = 1;
//                            Bundle bundle =new Bundle() ;
//                            bundle.putInt("uploadcount",count) ;
//                            bundle .putLong("bookid", bookid) ;
//                            msg.setData(bundle) ;
//                            handler2.sendMessage(msg);
//
//                        }
//                        catch (IOException e)
//                        {
//                            msg.what = 0;
//                            handler2.sendMessage(msg);
//                        }
//                        catch (XmlPullParserException e)
//                        {
//                            msg.what = 0;
//                            handler2.sendMessage(msg);
//                        }
//                    }
//                }.start();
//
//            }
//        }
//    };


//
//    //票据上传后处理
//    Handler handler2 = new Handler()
//    {
//        @Override
//        public void handleMessage(Message msg)
//        {
//            if (msg.what == 0)
//            {
//                Toast.makeText(AccountActivity.this, "上传票据失败！", Toast.LENGTH_LONG).show();
//            }
//            if (msg.what == 1)
//            {
//                Bundle bundle = msg.getData();
//                int count = bundle.getInt("uploadcount");
//                Toast.makeText(AccountActivity.this, "成功上传【" + count + "】张票据", Toast.LENGTH_LONG).show();
//            }
//        }
//
//    };





    /**
     * 生成一条记账
     *
     * @param v
     */
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


    //查询账目
    private void ShowAccountReport(View v)
    {
        Intent i=new Intent(this  ,AccountreportActivity .class );
        Bundle bundle = new Bundle() ;
        bundle.putLong("book_ID",accountBookID) ;
        bundle .putLong("user_ID",userinfoID) ;
        i.putExtra("sendBookID",bundle);
        startActivity(i);
    }

    private void loadBookTypelist(List<ACCOUNTSUBJECT> booklist)
    {
        ArrayAdapter<ACCOUNTSUBJECT> adp = new ArrayAdapter<ACCOUNTSUBJECT>(this, R.layout.support_simple_spinner_dropdown_item, booklist);
        this.mSpnSubject.setAdapter(adp);
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
        } else
        {
            return true;
        }
    }



    //region description 初始化

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        this.InitializeComponent(savedInstanceState);
    }

    private void InitializeComponent(Bundle savedInstanceState)
    {
        /**
         * 载入View
         */
        this.LoadView();
        //载入时发生
        Load(getIntent(), savedInstanceState);
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
    }




    private Button mBtnDateSelect;
    private Button mBtnAccount;
    private ImageButton mBtnCamera;
    private RecyclerView mRcvPiclist;

    private Button mBtnAccountCheck;
    private Button mBtnAccountReport;
    private EditText mEditPrice;
    private EditText mEditCount;
    private EditText mEdtRemark;
    private EditText mEdtName;
    private TextView mTvTitle;
    private Spinner mSpnSubject;
    private DateSelected mDateSelected;
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
        this.mBtnAccountCheck = (Button) findViewById(R.id.btnAccountItemCheck);
        this.mBtnAccountReport = (Button) findViewById(R.id.btnAccountReport);
        this.mBtnCamera = (ImageButton) findViewById(R.id.ibtnCamera);
        mDateSelected = (DateSelected) findViewById(R.id.dateSelected);
        this.mRcvPiclist = (RecyclerView) findViewById(R.id.rcvPiclist);
        this.mIsCheckAccount = (CheckBox) findViewById(R.id.chkAccountCheck);
        this.mBtnAccountReport = (Button) findViewById(R.id.btnAccountReport);
    }
    //endregion

}
