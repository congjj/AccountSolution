package cn.zgnj.tiexi.shenyang.myaccount.model;


import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.io.Serializable;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.IModelHelper;

/**
 * Created by CJJ on 2017/9/26.
 */


public class ACCOUNTSUBJECT extends SugarRecord  implements Serializable,IModelHelper
{
    /**
     * 外键 账本信息 ID
     */
    @Column(name = "ACCOUNTBOOK_ID",notNull = true)
    ACCOUNTBOOK  accountbook;

    public String getNAME()
    {
        return NAME;
    }

    /**
     * 记账项目名称
     */
    @Column(name = "NAME",unique = true ,notNull = true)
    String NAME;

    public boolean getISOUT()
    {
        return ISOUT;
    }

    /**
     * 是否支出 “True”支出 “False”收入
     */
    @Column(name = "ISOUT" ,notNull = true)
    boolean ISOUT;

    public String getREMARK()
    {
        return REMARK;
    }

    /**
     * 备注
     */
    @Column(name = "REMARK")
    String REMARK;

    public ACCOUNTSUBJECT ()
    {}
    public ACCOUNTSUBJECT (ACCOUNTBOOK accountbook ,String NAME ,String REMARK,boolean ISOUT  )
    {
        this.accountbook =accountbook ;
        this.NAME =NAME;
        this.REMARK =REMARK;
        this.ISOUT =ISOUT;
    }


    /**
     * 返回账簿下的所有 记账科目
     * @param bookID ACCOUNTBOOK 的ID
     * @return List <ACCOUNTSUBJECT >
     */
    public static List <ACCOUNTSUBJECT >getList4Book(long bookID)
    {
        return ACCOUNTSUBJECT.find(ACCOUNTSUBJECT .class ,"ACCOUNTBOOK_ID=?"
                , String.valueOf(bookID));
    }

    @Override
    public long _Insert()
    {
        List<ACCOUNTSUBJECT> list=ACCOUNTSUBJECT.find(ACCOUNTSUBJECT .class ,"NAME=? and ACCOUNTBOOK_ID=?"
                ,this.NAME ,this.accountbook .getId().toString());
        if(list.size() ==0)
        {
            return  this.save() ;
        }
        else
        {
            return -1;
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
