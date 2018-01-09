package cn.zgnj.tiexi.shenyang.myaccount.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.List;

import cn.zgnj.tiexi.shenyang.myaccount.IModelHelper;

/**
 * Created by CJJ on 2018/1/9.
 */

public class SYSCONFIG extends SugarRecord implements Serializable,IModelHelper
{
    @Ignore
    private static final String WEBURL="WEBURL";

    @Column(name = "FLAG", notNull = true)
    private  String Flag;

    @Column(name = "VALUES", notNull = true)
    private  String Values;

    @Column(name = "Remark", notNull = true)
    private  String Remark;


    public SYSCONFIG () {}
    public SYSCONFIG (String flag  ,String values ,String remark)
    {
        this.Flag  =flag;
        this.Values  =values ;
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
            return ((SYSCONFIG )sysconfiglist .toArray() [0]).Values;
        }
    }



    @Override
    public long _Insert()
    {
        return 0;
    }
}
