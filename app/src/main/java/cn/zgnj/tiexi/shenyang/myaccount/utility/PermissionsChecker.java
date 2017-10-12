package cn.zgnj.tiexi.shenyang.myaccount.utility;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by CJJ on 2017/10/12.
 */

public class PermissionsChecker
{
    private final Context mContext;

    public PermissionsChecker(Context context)
    {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions)
    {
        for (String perm : permissions)
        {
            if (lacksPermission(perm))
            {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission)
    {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }







}
