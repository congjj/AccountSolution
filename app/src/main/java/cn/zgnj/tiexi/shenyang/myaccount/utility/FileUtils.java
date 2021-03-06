package cn.zgnj.tiexi.shenyang.myaccount.utility;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by CJJ on 2017/11/22.
 */

public class FileUtils
{
    private final String AUTHORITIES="cn.zgnj.tiexi.shenyang.myaccount.fileprovider";
    private final String  APP_DIR_NAME = "accountfiles";
    private final String FILE_DIR_NAME = "accountPicture";
    private String mRootDir;
    private String mAppRootDir;
    private String mFileDir;

    public FileUtils ()
    {
        mRootDir = getRootPath();
        if (mRootDir != null && !"".equals(mRootDir))
        {
            mAppRootDir = mRootDir + File.separator + APP_DIR_NAME;
            mFileDir = mAppRootDir + File.separator + FILE_DIR_NAME;
            File appDir = new File(mAppRootDir);
            if (!appDir.exists())
            {
                appDir.mkdirs();
            }
            File fileDir = new File(mAppRootDir + File .separator  + FILE_DIR_NAME);
            if (!fileDir.exists())
            {
                fileDir.mkdirs();
            }
        }
        else
        {
            mRootDir = "";
            mAppRootDir = "";
            mFileDir = "";
        }   
    }

    public String getFileDir()
    {
        return mFileDir;
    }

    public String getRootPath()
    {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            return Environment.getExternalStorageDirectory().getAbsolutePath(); // filePath:  /sdcard/
        }
        else
        {
            return Environment.getDataDirectory().getAbsolutePath() + File .separator +"data"; // filePath:  /data/data/
        }
    }


    /**
     * 打开相机
     * 兼容7.0
     *
     * @param activity    Activity
     * @param file        File
     * @param requestCode result requestCode
     */
    public void startActionCamera(Activity activity, File file, int requestCode)
    {
        if (activity == null)
        {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file));
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 打开相机
     * 兼容7.0
     *
     * @param activity    Activity
     * @param requestCode result requestCode
     */
    public void startActioCamera(Activity activity,  int requestCode)
    {
        if (activity == null)
        {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 打开文件
     * 兼容7.0
     *
     * @param context     activity
     * @param file        File
     * @param contentType 文件类型如：文本（text/html）
     *                    当手机中没有一个app可以打开file时会抛ActivityNotFoundException
     */
    public void openActionFile(Context context, File file, String contentType) throws ActivityNotFoundException
    {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//增加读写权限
        intent.setDataAndType(getUriForFile(context, file), contentType);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }


    /**
     * 返回文件路径
     * 兼容7.0
     * @param context
     * @param file
     * @return
     */
    public  Uri getUriForFile(Context context, File file)
    {
        if (context == null || file == null)
        {
            throw new NullPointerException();
        }
        if(!file .exists() )
        {
            file .mkdirs() ;
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24)
        {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), AUTHORITIES, file);
        }
        else
        {
            uri = Uri.fromFile(file);
        }
        return uri;
    }



}
