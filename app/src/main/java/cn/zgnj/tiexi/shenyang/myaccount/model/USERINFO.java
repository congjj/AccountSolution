package cn.zgnj.tiexi.shenyang.myaccount.model;

import com.orm.SugarRecord;
import com.orm.dsl.* ;


import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import cn.zgnj.tiexi.shenyang.myaccount.IModelHelper;

/**
 * Created by CJJ on 2017/9/25.
 */
@Table
public class USERINFO extends SugarRecord implements Serializable ,IModelHelper
{

    /**
     * 用户编码
     */
    @Column(name = "USERCODE")
    String USERCODE;
    /**
     * 用户的手机号
     */
    @Column(name = "USERTEL_NO",unique = true )
    String USERTEL_NO;
    /**
     * sim卡 ID
     */
    @Column(name = "SIM_ISMI",unique = true ,notNull = true)
    String SIM_ISMI;
    /**
     * 用户姓名
     */
    @Column(name = "USERNAME")
    String USERNAME;
    /**
     * 密码
     */
    @Column(name = "USERPASSWORD")
    String USERPASSWORD;
    /**
     * 备注
     */
    @Column(name = "REMARK")
    String REMARK;

    public USERINFO (){}

    public USERINFO (String USERCODE ,String USERNAME ,String USERTEL_NO ,String SIM_ISMI ,String USERPASSWORD,String REMARK)
    {
        this.USERCODE =USERCODE ;
        this.USERTEL_NO =USERTEL_NO ;
        this.USERNAME  =USERNAME  ;
        this.SIM_ISMI  =SIM_ISMI ;
        this.USERNAME  = USERNAME;
        this.USERPASSWORD  =USERPASSWORD;
        this.REMARK = REMARK;
    }



    @Override
    public long Insert()
    {
        List<USERINFO> list=USERINFO.find(USERINFO .class ,"SIM_ISMI=?",this.SIM_ISMI  );
        //或者// findWithQuery(USERINFO .class ,"select * from USERINFO where USERTEL_NO ?",this.USERTEL_NO) ;
        if( list .size() ==0)
        {
            return this.save() ;
        }
        else
        {
            return ((USERINFO)list.toArray() [0]).getId();
        }

    }

}
