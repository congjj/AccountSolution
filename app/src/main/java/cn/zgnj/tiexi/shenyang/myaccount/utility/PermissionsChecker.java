package cn.zgnj.tiexi.shenyang.myaccount.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by CJJ on 2017/10/12.
 */

public class PermissionsChecker
{
    private final int REQUEST_CODE=1000;
    private final Context mContext;

    public PermissionsChecker(Context context)
    {
        mContext = context.getApplicationContext();
    }

    /**
     * 判断权限集合
     * @param permissions
     * @return
     */
    public  boolean verifyPermissions(String... permissions)
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


    /**
     * 申请权限
     * @param permissions
     * @return
     */
    public boolean apllyPermissions(String... permissions)
    {
        if(verifyPermissions(permissions))
        {
            ActivityCompat.requestPermissions(
                    (AppCompatActivity) mContext,
                    permissions,
                    REQUEST_CODE
            );
            if( verifyPermissions(permissions))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return true;
        }

    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission)
    {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }







}
