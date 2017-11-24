package cn.zgnj.tiexi.shenyang.myaccount.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import cn.zgnj.tiexi.shenyang.myaccount.utility.bzip2.CBZip2InputStream;
import cn.zgnj.tiexi.shenyang.myaccount.utility.bzip2.CBZip2OutputStream;

/**
 * Created by Administrator on 2017/10/16.
 */

public class Toolkit
{
    /**
     * bitmap 转换到字节流
     * @param bitmap
     * @return
     */
    public static byte[] bitmap4byte(Bitmap bitmap)
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap .compress(Bitmap.CompressFormat.PNG,100,os);
        return os.toByteArray() ;
    }

    /**
     * bitmap 转换到字节流
     * @param bitmap
     * @param qulity 质量 100 为100%
     * @return
     */
    public static byte[] bitmap4Byte(Bitmap bitmap,int qulity)
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap .compress(Bitmap.CompressFormat.PNG,qulity,os);
        return os.toByteArray() ;
    }


    /**
     * 字节流转换 bitmap
     * @param bytes
     * @return
     */
    public static Bitmap byte4bitmap(byte[] bytes)
    {
        return  BitmapFactory.decodeByteArray(bytes, 0, bytes .length);
    }


    public static byte[] blob4Bytes(Blob blob)
    {

        BufferedInputStream is = null;
        try
        {
            is = new BufferedInputStream(blob.getBinaryStream());
            byte[] bytes = new byte[(int) blob.length()];
            int len = bytes.length;
            int offset = 0;
            int read = 0;
            while (offset < len && (read = is.read(bytes, offset, len - offset)) >= 0)
            {
                offset += read;
            }
            return bytes;
        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            try
            {
                is.close();
                is = null;
            } catch (IOException e)
            {
                return null;
            }
        }
    }

    public static byte [] image4Byte(String filePath) throws IOException
    {
        //String filePath = "e:\\cc.jpg";

        File inFile = new File(filePath);

        FileInputStream fis= new FileInputStream(inFile);

        byte [] bytes=new byte[fis.available()];
        return bytes;
    }


    public static byte [] getBytes4Uri(Uri uri) throws IOException
    {
        //String filePath = "e:\\cc.jpg";
        String a =uri.toString() ;
        File inFile = new File(uri.toString());

        FileInputStream fis= new FileInputStream(inFile);

        byte [] bytes=new byte[fis.available()];
        return bytes;
    }

    /**
     * 获取Uri下的图片
     * @param context
     * @param uri
     * @return
     */
    public static Bitmap getBitmap4Uri(Context context ,Uri uri)
    {
        try
        {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context .getContentResolver(), uri);
            return bitmap;
        }
        catch (Exception e)
        {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }

    public static String replaceBlank(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern = Pattern.compile("\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }


    static String mCurrentPhotoPath;


    public static File createImageFile(Context context ) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =context . getExternalFilesDir(Environment.DIRECTORY_PICTURES);//由getExternalFilesDir(),以及getFilesDir()创建的目录，应用卸载后会被删除！

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        if(!image .exists())
        {
            storageDir .mkdirs() ;
        }
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }


    public static File createImageFileShare() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);//由getExternalFilesDir(),以及getFilesDir()创建的目录，应用卸载后会被删除！

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        if(!image .exists())
        {
            storageDir .mkdirs() ;
        }

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }


    /**
     * 照相返回照片路径
     * 兼容7.0
     * @param activity
     * @param filename
     * @param requestCode
     * @return 照片的Uri
     */
    public static Uri startCamera4filePath(Activity activity, String filename, int requestCode)
    {
        try
        {
            Uri uri=null;
            if (activity == null)
            {
                uri = null;
            }
            FileUtils fileUtils =new FileUtils() ;
            File photoFile =File .createTempFile(filename ,".jpg",new File(fileUtils .getFileDir())) ;

            if (photoFile != null)
            {
                uri = fileUtils.getUriForFile(activity , photoFile);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                activity.startActivityForResult(intent, requestCode);
            }
            return uri;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }



    /**
     * 打开相机
     * 兼容7.0
     *
     * @param activity    Activity
     * @param requestCode result requestCode
     */
    public static void startActioCamera(Activity activity,  int requestCode)
    {
        if (activity == null)
        {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, requestCode);
    }


    //region description 数据压缩
    /***
     * 压缩GZip
     *
     * @param data
     * @return
     */
    public static byte[] gZip(byte[] data)
    {
        byte[] b = null;
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 解压GZip
     *
     * @param data
     * @return
     */
    public static byte[] unGZip(byte[] data)
    {
        byte[] b = null;
        try
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return b;
    }


    /***
     * 压缩Zip
     *
     * @param data
     * @return
     */
    public static byte[] zip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(bos);
            ZipEntry entry = new ZipEntry("zip");
            entry.setSize(data.length);
            zip.putNextEntry(entry);
            zip.write(data);
            zip.closeEntry();
            zip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 解压Zip
     *
     * @param data
     * @return
     */
    public static byte[] unZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ZipInputStream zip = new ZipInputStream(bis);
            while (zip.getNextEntry() != null) {
                byte[] buf = new byte[1024];
                int num = -1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((num = zip.read(buf, 0, buf.length)) != -1) {
                    baos.write(buf, 0, num);
                }
                b = baos.toByteArray();
                baos.flush();
                baos.close();
            }
            zip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 压缩BZip2
     *
     * @param data
     * @return
     */
    public static byte[] bZip2(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            CBZip2OutputStream bzip2 = new CBZip2OutputStream(bos);
            bzip2.write(data);
            bzip2.flush();
            bzip2.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 解压BZip2
     *
     * @param data
     * @return
     */
    public static byte[] unBZip2(byte[] data)
    {
        byte[] b = null;
        try
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            CBZip2InputStream bzip2 = new CBZip2InputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = bzip2.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            bzip2.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * 把字节数组转换成16进制字符串
     *
     * @param bArray
     * @return
     */
    public static String bytesToHexString(byte[] bArray)
    {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    //endregion


}
