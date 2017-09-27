package cn.zgnj.tiexi.shenyang.myaccount.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.io.Serializable;

/**
 * Created by CJJ on 2017/9/26.
 */


public class ACCOUNTSUBJECT extends SugarRecord  implements Serializable
{
    /**
     * 外键 账本信息 ID
     */
    @Column(name = "ACCOUNTBOOK_ID",notNull = true)
    ACCOUNTBOOK  accountbook;
    /**
     * 记账项目名称
     */
    @Column(name = "NAME",unique = true ,notNull = true)
    String NAME;
    /**
     * 条码编号
     */
    @Column(name = "BARCODE")
    String BARCODE;
    /**
     * 默认单价
     */
    @Column(name = "DEFAULTPRICE")
    double DEFAULTPRICE;
    /**
     * 备注
     */
    @Column(name = "REMARK")
    String REMARK;
}
