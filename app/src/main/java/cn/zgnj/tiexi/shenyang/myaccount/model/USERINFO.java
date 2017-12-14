package cn.zgnj.tiexi.shenyang.myaccount.model;

import com.orm.SugarRecord;
import com.orm.dsl.* ;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
     * 服务器URL
     */
    @Column(name = "SERVER_URL",unique = true ,notNull =true )
    String  SERVERURL;

    /**
     * 服务器名称
     */
    @Column(name = "SERVER_NAME",unique = true ,notNull =true )
    String  SERVERNAME;

    /**
     * 最后登录时间
     */
    @Column(name = "LASTLOGINTIME",unique = true ,notNull =true )
    String  LASTLOGINTIME;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    String REMARK;

    /**
     * 获取用户名
     * @return
     */
    public String getUSERNAME ()
    {
        return this.USERNAME ;
    }

    /**
     * 获取webservice地址
     * @return
     */
    public String getSERVERURL ()
    {
        return this.SERVERURL ;
    }

    public String getSIM_ISMI ()
    {
        return this.SIM_ISMI ;
    }


    public USERINFO (){}

    public USERINFO (String USERCODE ,String USERNAME ,String USERTEL_NO ,String SIM_ISMI ,String USERPASSWORD,
                     String LOGINTIME  ,String LASTLOGINTIME ,String SERVERURL ,String SERVERNAME ,String REMARK)
    {
        this.USERCODE =USERCODE ;
        this.USERTEL_NO =USERTEL_NO ;
        this.USERNAME  =USERNAME  ;
        this.SIM_ISMI  =SIM_ISMI ;
        this.USERNAME  = USERNAME;
        this.USERPASSWORD  =USERPASSWORD;
        this.LOGINTIME =LOGINTIME ;
        this.LASTLOGINTIME =LASTLOGINTIME ;
        this.SERVERURL =SERVERURL ;
        this.SERVERNAME =SERVERNAME;
        this.REMARK = REMARK;
    }

    /**
     * 获取一个USERINFO
     * @param ID USERINFO ID
     * @return     USERINFO
     */
    public static USERINFO getOne(long ID)
    {
        return USERINFO .findById(USERINFO .class ,ID) ;
    }

    /**
     * 获取账户下的记账簿
     * @return List<ACCOUNTBOOK>
     */
    public List<ACCOUNTBOOK>getACCOUNTBOOKList()
    {
        List<ACCOUNTBOOK> list=ACCOUNTBOOK.find(ACCOUNTBOOK.class ,"USERINFO_ID=?",this.getId() .toString());
        return list;
    }

    public void setSERVERURL (String serverurl)
    {
        this.SERVERURL =serverurl ;
    }

    public void setSERVERNAME (String servername )
    {
        this.SERVERNAME =servername ;
    }


    /**
     * 获取用户
     * @param SIM_Code 手机卡物理地址
     * @return
     */
    public static  USERINFO getOne(String SIM_Code)
    {
        List<USERINFO>temp = USERINFO.find(USERINFO .class ,"SIM_ISMI=?",SIM_Code);
        return temp.size() ==0 ? null : temp .get(0) ;
    }

    @Override
    public long _Insert()
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
