package com.munin.mhlogwindow.manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ListView;

import com.munin.mhlogwindow.ILogListener;
import com.munin.mhlogwindow.MHLogAdapter;
import com.munin.mhlogwindow.MHLogData;
import com.munin.mhlogwindow.MHLogManager;
import com.munin.mhlogwindow.view.FloatView;

/**
 * Created by Munin on 2016/12/7.
 */
public class WindowFloatView implements ILogListener {
    private static FloatView floatView;
    private static WindowManager windowManager = null;
    private static WindowManager.LayoutParams windowManagerParams = null;
    private static WindowFloatView windowFloatView;
    MHLogAdapter adapter;
    ListView listView;

    private WindowFloatView(Activity activity, FloatView view) {
        if (floatView != null) {
            reomveView();
        }
        adapter = new MHLogAdapter(activity);
        floatView = view;
        listView = floatView.getLv();
        if (null != listView)
            listView.setAdapter(adapter);
        MHLogManager.newInstance().add(this);
        // 获取WindowManager
        windowManager = (WindowManager) activity.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        // 设置LayoutParams(全局变量）相关参数
        windowManagerParams = new WindowManager.LayoutParams();

        windowManagerParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG; // 设置window type
        IBinder windowToken =    activity.getWindow().getDecorView().getWindowToken();
        windowManagerParams.token = windowToken;
        windowManagerParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        // 设置Window flag
        windowManagerParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        /*
         * 注意，flag的值可以为：
		 * 下面的flags属性的效果形同“锁定”。
		 * 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
		 * LayoutParams.FLAG_NOT_TOUCH_MODAL 不影响后面的事件
		 * LayoutParams.FLAG_NOT_FOCUSABLE  不可聚焦
		 * LayoutParams.FLAG_NOT_TOUCHABLE 不可触摸
		 */
        // 调整悬浮窗口至左上角，便于调整坐标
        windowManagerParams.gravity = Gravity.RIGHT | Gravity.TOP;
//        // 以屏幕左上角为原点，设置x、y初始值
        windowManagerParams.x = 0;
        windowManagerParams.y = 0;
        // 设置悬浮窗口长宽数据
        windowManagerParams.width = 400;
        windowManagerParams.height = 800;
        // 显示myFloatView图像
        floatView.setWindowParam(windowManagerParams);
        windowManager.addView(floatView, windowManagerParams);

    }


    public static synchronized WindowFloatView getInstance(Activity activity, FloatView view) {
        windowFloatView = new WindowFloatView(activity, view);
        return windowFloatView;
    }

    private OnFloatViewClickListener mlistener;

    public void setFloatViewClick(OnFloatViewClickListener listener) {
        mlistener = listener;
    }

    public static void reomveView() {

        if (floatView != null) {
            if (windowManager != null) {
                windowManager.removeView(floatView);
                floatView = null;
            }
        }
    }

    public static int getX() {
        return windowManagerParams.x;
    }

    public static int getY() {
        return windowManagerParams.y;
    }

    @Override
    public void notifyLog(MHLogData data) {
        adapter.add(data);
        listView.smoothScrollToPosition(adapter.getCount() - 1);
    }

    public interface OnFloatViewClickListener {
        void OnClick();
    }

}
