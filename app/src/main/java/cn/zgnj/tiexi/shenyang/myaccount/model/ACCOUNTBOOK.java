package cn.zgnj.tiexi.shenyang.myaccount.model;

import android.widget.BaseAdapter;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.io.Serializable;

/**
 * Created by CJJ on 2017/9/27.
 */


public class ACCOUNTBOOK extends SugarRecord implements Serializable
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

}
