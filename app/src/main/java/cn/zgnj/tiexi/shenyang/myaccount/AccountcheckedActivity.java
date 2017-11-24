package cn.zgnj.tiexi.shenyang.myaccount;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.model.ACCOUNTBOOK;
import cn.zgnj.tiexi.shenyang.myaccount.model.USERINFO;
import cn.zgnj.tiexi.shenyang.myaccount.utility.FileUtils;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Permissionhelper;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Toolkit;

public class AccountcheckedActivity extends AppCompatActivity
{


    private ACCOUNTBOOK mACCOUNTBOOK ;
    private void Load(Intent intent, Bundle savedInstanceState)
    {
        Bundle bundle = new Bundle();
        bundle = intent.getBundleExtra("sendBookID");
        mACCOUNTBOOK = ACCOUNTBOOK.getItSelf(bundle.getLong("book_ID"));


        List<ACCOUNTBOOK> booklist = USERINFO.getOne(bundle.getLong("user_ID")).getACCOUNTBOOKList();
        ArrayAdapter<ACCOUNTBOOK> adp=new ArrayAdapter<ACCOUNTBOOK>(this , R.layout.support_simple_spinner_dropdown_item,booklist);

    }

    Uri uri1;
    private void CheckAccountItem(View v)
    {
        String filename ="Img"+ Long.toString(System.currentTimeMillis());
        uri1 =Toolkit .startCamera4filePath(this,filename ,Activity.DEFAULT_KEYS_DIALER) ;


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //拍照后返回照片
        if (Activity.DEFAULT_KEYS_DIALER == requestCode)
        {
            try
            {
//                Bitmap thumbnail = data.getParcelableExtra("data");
//                Bundle extras = data.getExtras();
//                Intent a =data;
//                Bitmap b = (Bitmap) extras.get("data");
                Uri uri =uri1 ;

                Bitmap aa = Toolkit.getBitmap4Uri(this, uri) ;
               //File file = new File() ;
                //this.mImageViewItemShow .setImageURI(uri1) ;

               this.mImageViewItemShow .setImageBitmap(aa) ;
                //  U.ResizeBitmap(U.getBitmapForFile(F.SD_CARD_TEMP_PHOTO_PATH), 640);
                //take = b;
//                ImageView img = (ImageView)findViewById(R.id.imageView );
//                img.setImageBitmap(b);

            } catch (Exception e)
            {
            }
        }
    }


















    Button mButtonItemCheck;
    ImageView mImageViewItemShow;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountchecked);
        this.InitializeComponent(savedInstanceState);
    }

    private void InitializeComponent(Bundle savedInstanceState)
    {
        //载入控件
        this.LoadView();

        //载入时发生
        Load(getIntent(), savedInstanceState);
        //控件事件
        SetListener();
    }

    private void LoadView()
    {
        mImageViewItemShow =(ImageView )findViewById(R.id .ivItemShow) ;
        mButtonItemCheck =(Button)findViewById(R.id.btnItemCheckOp) ;
    }

    private void SetListener()
    {

        this.mButtonItemCheck .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckAccountItem(v);
            }
        }) ;
    }




}
