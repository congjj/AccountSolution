package cn.zgnj.tiexi.shenyang.myaccount.model;

import android.graphics.Bitmap;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import java.io.Serializable;
import java.sql.Blob;

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
    @Column(name = "INDEX", notNull = true)
    int mINDEX;

    /**
     * 图片数据
     */
    @Column(name = "PIC", notNull = true)
    Bitmap mPIC;

    /**
     * 是否有效
     */
    @Column(name = "ISACTIVED", notNull = true)
    boolean mISACTIVED;

    public ACCOUNTBILL()
    {}

    public ACCOUNTBILL(ACCOUNTLIST mAccountList,String mName,int mIndex,Bitmap mPic,boolean mIsActived)
    {
        this.mACCOUNTLIST =mAccountList ;
        this.mNAME =mName ;
        this.mINDEX =mIndex ;
        this.mPIC =mPic ;
        this.mISACTIVED =mIsActived ;
    }

    @Override
    public long _Insert()
    {
        return this.save();
    }

}
