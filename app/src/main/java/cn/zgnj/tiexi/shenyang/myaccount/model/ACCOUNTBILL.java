package cn.zgnj.tiexi.shenyang.myaccount.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import java.io.Serializable;

import cn.zgnj.tiexi.shenyang.myaccount.IModelHelper;

/**
 * Created by CJJ on 2017/10/11.
 */

public class ACCOUNTBILL extends SugarRecord implements Serializable,IModelHelper
{
    /**
     * 外键 花销 ID
     */
    @Column(name = "ACCOUNTLIST_ID",notNull = true)
    ACCOUNTLIST mACCOUNTLIST ;

    /**
     * 票据名称
     */
    @Column(name = "NAME",notNull = true)
    String mNAME;

    /**
     * 序号
     */
    @Column(name = "COUNT",notNull = true)
    int mINDEX;






    @Override
    public long _Insert()
    {
        return 0;
    }
}
