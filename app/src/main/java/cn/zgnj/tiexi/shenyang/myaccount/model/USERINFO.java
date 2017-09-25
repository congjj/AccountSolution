package cn.zgnj.tiexi.shenyang.myaccount.model;

import com.orm.SugarRecord;

/**
 * Created by CJJ on 2017/9/25.
 */

public class USERINFO extends SugarRecord<USERINFO>
{

    long ID;
    String CODE;
    String NAME;
    String PASSWORD;
    String REMARK;

    public USERINFO ()
    {}

    public USERINFO (long ID,String CODE ,String NAME ,String PASSWORD ,String REMARK  )
    {
        this.ID =ID ;
        this.CODE =CODE ;
        this.NAME = NAME;
        this.PASSWORD =PASSWORD;
        this.REMARK = REMARK;
    }


}
