package com.munin.mhlogwindow;

import android.util.Log;

/**
 * Created by munin on 2018/1/30.
 */

public class MHlog {
    public static void e(String tag, String msg) {
        MHLogData data = new MHLogData(tag + ":" + msg, "#ff0000");
        MHLogManager.newInstance().notify(data);
        Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        MHLogData data = new MHLogData(tag + ":" + msg, "#ffffff");
        MHLogManager.newInstance().notify(data);
        Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        MHLogData data = new MHLogData(tag + ":" + msg, "#ff00ff");
        MHLogManager.newInstance().notify(data);
        Log.d(tag, msg);
    }

    public static void w(String tag, String msg) {
        MHLogData data = new MHLogData(tag + ":" + msg, "#ffff00");
        MHLogManager.newInstance().notify(data);
        Log.w(tag, msg);
    }

}
