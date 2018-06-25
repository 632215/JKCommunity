package com.jinke.community.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */

public class WXUtils {
    public static boolean isWxInstalled(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;

    }
}
