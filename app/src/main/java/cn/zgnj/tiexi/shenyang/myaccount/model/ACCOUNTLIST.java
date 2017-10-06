package cn.zgnj.tiexi.shenyang.myaccount.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;

import java.io.Serializable;

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
    Double mISCHECKED;
    /**
     * 是否有效
     */
    @Column(name = "ISACTIVED",notNull = true)
    boolean  mISACTIVED;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    String mREMARK;


    @Override
    public long _Insert()
    {
        return 0;
    }
}
