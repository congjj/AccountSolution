package cn.zgnj.tiexi.shenyang.myaccount.model;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by CJJ on 2017/9/25.
 */

public class USERINFO extends SugarRecord<USERINFO> implements Serializable
{
    long ID;
    String USERCODE;
    String USERTELEPHONE;
    String TELEPHONEISMI;
    String USERNAME;
    String USERPASSWORD;
    String REMARK;


    public USERINFO (long ID,String USERCODE ,String USERTELEPHONE ,String TELEPHONEISMI ,String USERNAME ,String USERPASSWORD,String REMARK)
    {
        this.ID =ID ;
        this.USERCODE =USERCODE ;
        this.USERTELEPHONE =USERTELEPHONE ;
        this.TELEPHONEISMI =TELEPHONEISMI ;
        this.USERNAME  = USERNAME;
        this.USERPASSWORD  =USERPASSWORD;
        this.REMARK = REMARK;
    }






}
