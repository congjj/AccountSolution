package cn.zgnj.tiexi.shenyang.myaccount.model;

import android.provider.ContactsContract;

import com.orm.SugarRecord;
import com.orm.dsl.Column;

import java.io.Serializable;
import java.util.Date;

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
}
