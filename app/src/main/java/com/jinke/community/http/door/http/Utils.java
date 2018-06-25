package com.jinke.community.http.door.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * function:
 * author: hank
 * date: 2017/5/15
 */
public class Utils
{
    public static String bytesToHexString(byte[] src, int length){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || length <= 0) {
            return null;
        }
        for (int i = 0; i < length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + " ");
        }
        return stringBuilder.toString();
    }

    public static void loge( String message)
    {
//        Log.e( "hank", message );
    }

    public static void showTextToast( Context context , String message)
    {
        Toast.makeText( context , message , Toast.LENGTH_SHORT ).show();
    }

}
