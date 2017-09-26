package cn.zgnj.tiexi.shenyang.myaccount.model;

import com.orm.SugarRecord;
import com.orm.dsl.* ;


import java.io.Serializable;

/**
 * Created by CJJ on 2017/9/25.
 */

@Table
public class USERINFO extends SugarRecord
{
    /**
     * ID 主键
     */
    @Column(name = "ID",unique = true ,notNull = true)
    long ID;
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

    public USERINFO (long ID,String USERCODE ,String USERNAME ,String SIM_ISMI ,String USERPASSWORD,String REMARK)
    {
        this.ID =ID ;
        this.USERCODE =USERCODE ;
        this.USERNAME  =USERNAME  ;
        this.SIM_ISMI  =SIM_ISMI ;
        this.USERNAME  = USERNAME;
        this.USERPASSWORD  =USERPASSWORD;
        this.REMARK = REMARK;
    }






}
