package com.haijun.opentool.util;

import android.util.Log;


public class LogUtil {
    private static final boolean isShow = true;

    public static void v(String tag, String msg){
        if (isShow){
            Log.v(tag,msg);
        }
    }

    public static void d(String tag, String msg){
        if (isShow){
            Log.d(tag,msg);
        }
    }

    public static void i(String tag, String msg){
        if (isShow){
            Log.i(tag,msg);
        }
    }

    public static void w(String tag, String msg){
        if (isShow){
            Log.w(tag,msg);
        }
    }

    public static void e(String tag, String msg){
        if (isShow){
            Log.e(tag,msg);
        }
    }

    public static void e(Class classz, String msg){
        if (isShow){
            Log.e(classz.getSimpleName(),msg);
        }
    }

}
