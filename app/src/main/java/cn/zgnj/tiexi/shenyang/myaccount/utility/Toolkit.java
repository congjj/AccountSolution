package cn.zgnj.tiexi.shenyang.myaccount.utility;

import android.graphics.Bitmap;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;

/**
 * Created by Administrator on 2017/10/16.
 */

public class Toolkit
{
    public static byte[] bitmap4byte(Bitmap bitmap)
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap .compress(Bitmap.CompressFormat.PNG,100,os);
        return os.toByteArray() ;
    }

    public static byte[] bitmap4Byte(Bitmap bitmap,int qulity)
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap .compress(Bitmap.CompressFormat.PNG,qulity,os);
        return os.toByteArray() ;
    }


    private byte[] blob4Bytes(Blob blob)
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


}
