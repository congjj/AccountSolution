package cn.zgnj.tiexi.shenyang.myaccount.model;

import android.widget.Toast;

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

    public USERINFO  getUserInfo()
    {
        return this.userinfo;
    }
    /**
     * 获取名称
     * @return String
     */
    public String getNAME ()
    {
        return this.NAME ;
    }

    /**
     * 获取备注
     * @return String
     */
    public String getREMARK()
    {
        return this.REMARK;
    }


    public static  ACCOUNTBOOK getItSelf(long thisID)
    {
        return ACCOUNTBOOK .findById(ACCOUNTBOOK.class ,thisID);
    }



    @Override
    public long _Insert()
    {
        List<ACCOUNTBOOK>list = ACCOUNTBOOK .find(ACCOUNTBOOK .class ,"USERINFO_ID=? and NAME=?",this.userinfo .getId().toString() ,this.NAME .toUpperCase()) ;
        if(list .size() ==0)
        {
            return this.save() ;
        }
        else
        {

            return -1;//((ACCOUNTBOOK )list.toArray() [0]).getId() ;
        }
    }

    @Override
    public String toString()
    {
        if(this.REMARK .trim() .length() ==0)
        {
            return this.NAME ;
        }
        else
        {
            return this.NAME + "【" + this.REMARK + "】";
        }
    }

}
