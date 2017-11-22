package cn.zgnj.tiexi.shenyang.myaccount;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import cn.zgnj.tiexi.shenyang.myaccount.utility.Permissionhelper;
import cn.zgnj.tiexi.shenyang.myaccount.utility.Toolkit;

public class AccountcheckedActivity extends AppCompatActivity
{

    private void Load(Intent intent, Bundle savedInstanceState)
    {

    }


    private void CheckAccountItem(View v)
    {
        Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
        //it.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        if (it .resolveActivity(getPackageManager()) != null)
        {
            File photoFile = null;
            try
            {
                photoFile = Toolkit.createImageFileShare();
                if (photoFile != null)
                {
                    Uri uri = Uri.fromFile(photoFile);
                    it.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }
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
//                File file = Toolkit .createImageFileShare();
//                Uri uri = Uri.fromFile(file);
//
//                data.setData(uri) ;

                Bitmap thumbnail = data.getParcelableExtra("data");
                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");
                this.mImageViewItemShow .setImageURI(data.getData()) ;
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
        mImageViewItemShow .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckAccountItem(v);
            }
        }) ;
    }




}
