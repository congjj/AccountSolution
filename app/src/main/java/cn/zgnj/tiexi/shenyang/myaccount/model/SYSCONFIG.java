package cn.zgnj.tiexi.shenyang.myaccount.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

import java.io.Serializable;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.IModelHelper;

/**
 * Created by CJJ on 2018/1/9.
 */

@Table
public class SYSCONFIG extends SugarRecord implements Serializable,IModelHelper
{


    @Column(name = "FLAG", notNull = true)
    private  String Flag;

    @Column(name = "CONFIGVALUES", notNull = true)
    private  String ConfigValues;

    @Column(name = "REMARK", notNull = true)
    private  String Remark;

    @Ignore
    private static final String WEBURL="WEBURL";

    public SYSCONFIG () {}
    public SYSCONFIG (String flag  ,String configValues  ,String remark)
    {
        this.Flag  =flag;
        this.ConfigValues   =configValues  ;
        this.Remark =remark;
    }


    /**
     * 设置 webservice
     * @param weburl
     * @return
     */
    public static boolean addWebUrlConfig(String weburl)
    {
        List<SYSCONFIG> sysconfiglist =SYSCONFIG.find(SYSCONFIG.class, "FLAG = ?", WEBURL );
        if(sysconfiglist .size() ==0)
        {
            try
            {
                SYSCONFIG sysconfig = new SYSCONFIG(WEBURL ,weburl ,"WebService 的地址");
                return sysconfig.save() > 0;
            }
            catch (Exception ex)
            {
                return false;
            }
        }
        else
        {
            return true ;
        }
    }

    public static String getWEBURL()
    {
        List<SYSCONFIG> sysconfiglist =SYSCONFIG.find(SYSCONFIG.class, "FLAG = ?", WEBURL );
        if(sysconfiglist .size() ==0)
        {
            return "";
        }
        else
        {
            return ((SYSCONFIG )sysconfiglist .toArray() [0]).ConfigValues;
        }
    }



    @Override
    public long _Insert()
    {
        return 0;
    }
}
