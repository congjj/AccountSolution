package cn.zgnj.tiexi.shenyang.myaccount.model;

import com.orm.SugarRecord;
import com.orm.dsl.* ;
import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import cn.zgnj.tiexi.shenyang.myaccount.IModelHelper;

/**
 * Created by CJJ on 2017/9/25.
 */

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
     * 注册日期
     */
    @Column(name = "LOGINTIME",unique = true ,notNull =true )
    String LOGINTIME;
    /**
     * 最后登录日期
     */
    @Column(name = "LASTLOGINTIME",unique = true ,notNull =true )
    String  LASTLOGINTIME;
    /**
     * 备注
     */
    @Column(name = "REMARK")
    String REMARK;

    public USERINFO (){}

    public USERINFO (String USERCODE ,String USERNAME ,String USERTEL_NO ,String SIM_ISMI ,String USERPASSWORD,
                     String LOGINTIME  ,String LASTLOGINTIME ,String REMARK)
    {
        this.USERCODE =USERCODE ;
        this.USERTEL_NO =USERTEL_NO ;
        this.USERNAME  =USERNAME  ;
        this.SIM_ISMI  =SIM_ISMI ;
        this.USERNAME  = USERNAME;
        this.USERPASSWORD  =USERPASSWORD;
        this.LOGINTIME =LOGINTIME ;
        this.LASTLOGINTIME =LASTLOGINTIME ;
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
            //更新 登录时间
            long userID = ((USERINFO)list.toArray() [0]).getId();
            USERINFO userinfo =USERINFO .findById(USERINFO .class ,userID );
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
            userinfo .LASTLOGINTIME =dateFormat .format(new Date()) ;
            userinfo .save() ;

            return userID;
        }

    }

}
