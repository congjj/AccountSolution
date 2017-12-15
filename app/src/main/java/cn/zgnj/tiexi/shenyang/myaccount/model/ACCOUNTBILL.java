package cn.zgnj.tiexi.shenyang.myaccount.model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.IModelHelper;

/**
 * Created by CJJ on 2017/10/11.
 */

public class ACCOUNTBILL extends SugarRecord implements Serializable,IModelHelper
{
    /**
     * 外键 花销 ID
     */
    @Column(name = "ACCOUNTLIST_ID", notNull = true)
    ACCOUNTLIST mACCOUNTLIST;

    /**
     * 票据名称
     */
    @Column(name = "NAME", notNull = true)
    String mNAME;

    /**
     * 序号
     */
    @Column(name = "NOINDEX", notNull = true)
    int mINDEX;

    /**
     * 图片数据
     */
    @Column(name = "PIC")
    byte []  mPIC;


    /**
     * 图片数据路径
     */
    @Column(name = "PICPATH")
    String mPICPATH;

    /**
     * 是否有效
     */
    @Column(name = "ISUPLOAD", notNull = true)
    boolean mISUPLOAD;

    /**
     * 是否有效
     */
    @Column(name = "ISACTIVED", notNull = true)
    boolean mISACTIVED;

    public byte[]getPIC ()
    {
        return this.mPIC;
    }


    public void setISUPLOAD (boolean isupload )
    {
        this.mISUPLOAD =isupload;
    }

    public ACCOUNTBILL()
    {}

    protected static List<byte []>geBills(String accountlist_ID)
    {
        List<byte []>list=new ArrayList<byte[]>() ;
        for(ACCOUNTBILL temp : ACCOUNTBILL .find(ACCOUNTBILL .class ,"ACCOUNTLIST_ID=?",accountlist_ID) )
        {
            list .add(temp.mPIC) ;
        }
        return list;
    }

    protected static List<Uri>getBillsUri(String accountlist_ID)
    {
        List<Uri>list = new ArrayList<Uri>() ;
        for(ACCOUNTBILL temp : ACCOUNTBILL .find(ACCOUNTBILL .class ,"ACCOUNTLIST_ID=?",accountlist_ID) )
        {
            Uri aa= Uri.parse(temp.mPICPATH) ;
            list .add(aa) ;
        }
        return list;
    }

    public ACCOUNTBILL(ACCOUNTLIST mAccountList,String mName ,int mIndex
            ,byte [] mPic,Uri mPicPath,boolean ISUPLOAD ,boolean mIsActived)
    {
        this.mACCOUNTLIST =mAccountList ;
        this.mNAME =mName ;
        this.mINDEX =mIndex ;
        this.mPIC =mPic ;
        this.mPICPATH =mPicPath .toString() ;
        this.mISACTIVED =mIsActived ;
        this.mISUPLOAD =ISUPLOAD ;
    }




    @Override
    public long _Insert()
    {
        return this.save();
    }

}
