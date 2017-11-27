package cn.zgnj.tiexi.shenyang.myaccount.model;

import android.hardware.camera2.TotalCaptureResult;
import android.net.Uri;
import android.provider.ContactsContract;

import com.orm.SugarRecord;
import com.orm.dsl.Column;

import java.io.Serializable;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.IModelHelper;

/**
 * Created by Administrator on 2017/10/6.
 */

public class ACCOUNTLIST extends SugarRecord implements Serializable,IModelHelper
{
    /**
     * 外键 记账项目 ID
     */
    @Column(name = "ACCOUNTSUBJECT_ID",notNull = true)
    ACCOUNTSUBJECT  mACCOUNTSUBJECT ;

    /**
     * 唯一ID
     */
    @Column(name = "UUID",notNull = true,unique = true)
    String mUUID;

    /**
     * 花销名称
     */
    @Column(name = "NAME",notNull = true)
    String mNAME;

    /**
     * 花销数量
     */
    @Column(name = "COUNT",notNull = true)
    Double mCOUNT;

    /**
     * 单价
     */
    @Column(name = "PRICE",notNull = true)
    Double mPRICE;

    /**
     * 是否已对账
     */
    @Column(name = "ISCHECKED",notNull = true)
    boolean mISCHECKED;
    /**
     * 是否有效
     */
    @Column(name = "ISACTIVED",notNull = true)
    boolean  mISACTIVED;
    /**
     * 操作时间
     */
    @Column(name = "CREATETIME",notNull = true)
    Date  mCREATETIME;

    /**
     * 入账日期
     */
    @Column(name = "ACCOUNTTIME",notNull = true)
    Date  mACCOUNTTIME;

    /**
     * 是否已上传
     */
    @Column(name = "ISUPLOAD",notNull = true)
    boolean  mISUPLOAD;
    /**
     * 备注
     */
    @Column(name = "REMARK")
    String mREMARK;


    public boolean getISCHECKED ()
    {
        return mISCHECKED ;
    }

    public String getUUID ()
    {
        return mUUID ;
    }

    public String getNAME ()
    {
        return mNAME ;
    }

    public double getPrice()
    {
        return this.mPRICE ;
    }

    public double getCount()
    {
        return mCOUNT ;
    }

    public String getSubjectName()
    {
        return mACCOUNTSUBJECT .getNAME() ;
    }


    public String getValues()
    {
        Double result ;
        if(this.mACCOUNTSUBJECT .ISOUT )
        {
            result =this.mCOUNT * this.mPRICE *(-1);
            return result .toString() ;
        }
        else
        {
            result =this.mCOUNT * this.mPRICE ;
            return result .toString() ;
        }
    }

    public boolean getIsOut()
    {
        return this.mACCOUNTSUBJECT .ISOUT ;
    }

    public Date getACCOUNTTIME ()
    {
        return mACCOUNTTIME ;
    }

    public Date getCREATETIME ()
    {
        return mCREATETIME ;
    }

    public String getREMARK ()
    {
        return mREMARK ;
    }

    /**
     * 获取一条记录的票据照片
     * @return
     */
    public List<byte []>getBills()
    {
         return  ACCOUNTBILL .geBills(this.getId() .toString() ) ;
    }

    /**
     * 获取一条记录的票据照片 的 路径 用于查看原始图像
     * @return
     */
    public List<Uri>getBillsPath()
    {
        return ACCOUNTBILL .getBillsUri(this.getId() .toString()) ;
    }


    public ACCOUNTLIST(){}

    /**
     * 初始化
     * @param mAccountSubject
     * @param mUUID
     * @param mName
     * @param mCount
     * @param mPrice
     * @param mIsChecked
     * @param mIsActived
     * @param mCreateTime
     * @param mAccountTime
     * @param mIsUpload
     * @param mRemark
     */
    public ACCOUNTLIST (ACCOUNTSUBJECT mAccountSubject ,String mUUID,String mName ,double mCount,double mPrice, boolean mIsChecked,
            boolean mIsActived,Date mCreateTime,Date mAccountTime,boolean mIsUpload,String mRemark)
    {
        this.mACCOUNTSUBJECT =mAccountSubject ;
        this.mUUID =mUUID ;
        this.mNAME =mName ;
        this.mCOUNT =mCount ;
        this.mPRICE =mPrice ;
        this.mISCHECKED =mIsChecked ;
        this.mISACTIVED =mIsActived ;
        this.mCREATETIME =mCreateTime ;
        this.mACCOUNTTIME =mAccountTime ;
        this.mISUPLOAD =mIsUpload ;
        this.mREMARK =mRemark ;
    }


    /**
     * 根据uuID查找一条记账记录
     * @param uuID
     * @return
     */
    public static  ACCOUNTLIST getOne(String uuID)
    {
        return (ACCOUNTLIST) ACCOUNTLIST.find(ACCOUNTLIST.class ,"UUID=?",uuID).toArray() [0];
    }

    @Override
    public long _Insert()
    {
        return this.save();
    }


    /**
     * 记账本下的明细
     * @param ACCOUNTBOOKID
     * @return
     */
    public static List<ACCOUNTLIST>GetSome(Calendar fromTime, Calendar toTime, String ACCOUNTBOOKID)
    {
        String ftime=String.valueOf(fromTime .getTimeInMillis());////fromTime .toString() ;
        String ttime=String.valueOf(toTime .getTimeInMillis()) ;
        return   ACCOUNTLIST.findWithQuery(ACCOUNTLIST .class ,"select * from ACCOUNTLIST where ACCOUNTTIME>=? and ACCOUNTTIME<? " +
               "and ACCOUNTSUBJECT_ID in (select ID from ACCOUNTSUBJECT where ACCOUNTBOOK_ID=?) order by ACCOUNTTIME",ftime , ttime ,ACCOUNTBOOKID) ;
    }


}
