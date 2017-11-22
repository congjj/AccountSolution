package cn.zgnj.tiexi.shenyang.myaccount.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ContentHandler;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }



}
