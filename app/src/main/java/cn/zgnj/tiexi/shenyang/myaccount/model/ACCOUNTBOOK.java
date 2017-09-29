package cn.zgnj.tiexi.shenyang.myaccount.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;

import java.io.Serializable;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.IModelHelper;

/**
 * Created by CJJ on 2017/9/27.
 */


public class ACCOUNTBOOK extends SugarRecord implements Serializable,IModelHelper
{
    /**
     * 外键 用户信息 ID
     */
    @Column(name = "USERINFO_ID",notNull = true)
    USERINFO userinfo ;
    /**
     * 账本名字
     */
    @Column(name = "NAME",unique = true ,notNull = true)
    String NAME;
    /**
     * 备注
     */
    @Column(name = "REMARK")
    String REMARK;

    public ACCOUNTBOOK () {}
    public ACCOUNTBOOK (USERINFO userinfo ,String NAME ,String REMARK )
    {
        this.userinfo =userinfo;
        this.NAME =NAME;
        this.REMARK =REMARK;
    }

    @Override
    public long _Insert()
    {
        return this.save() ;
    }

    @Override
    public String toString()
    {
        return this.NAME +"【"+this.REMARK +"】";
    }
}
