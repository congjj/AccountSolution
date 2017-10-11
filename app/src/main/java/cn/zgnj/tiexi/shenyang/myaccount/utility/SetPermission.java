package cn.zgnj.tiexi.shenyang.myaccount.utility;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by CJJ on 2017/10/11.
 */

public class SetPermission
{
    Context mContext ;
    public void SetPermission(Context context )
    {
        mContext =context;
    }
    public void onCallPermission()
    {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {//判断当前系统的SDK版本是否大于23
//
//            //如果当前申请的权限没有授权
//            if (!(checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED))
//            {
//                //第一次请求权限的时候返回false,第二次shouldShowRequestPermissionRationale返回true
//                //如果用户选择了“不再提醒”永远返回false。
//                if (shouldShowRequestPermissionRationale(android.Manifest.permission.RECORD_AUDIO))
//                {
//                    Toast.makeText(mContext , "Please grant the permission this time", Toast.LENGTH_LONG).show();
//                }
//                //请求权限
//                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},1);
//            }
//            else
//            {//已经授权了就走这条分支
//
//                Log.i("wei", "onClick granted");
//
//            }
//        }
    }

//     @Override
//     public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
//     {
//             if (requestCode==1)
//             {
//                     if (permissions[0].equals(Manifest.permission.RECORD_AUDIO)&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
//                     {
//                           listernUi();//得到权限之后去做的业务
//                     }
//                     else
//                     {//没有获得到权限
//                           Toast.makeText(mContext,"你不给权限我就不好干事了啦",Toast.LENGTH_SHORT).show();
//                     }
//             }
//     }
//
}
