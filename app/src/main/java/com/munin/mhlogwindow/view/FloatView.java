package com.munin.mhlogwindow.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.munin.mhlogwindow.R;


/**
 * Created by Munin on 2016/12/7.
 */
public class FloatView extends RelativeLayout {
    private float mTouchX;
    private float mTouchY;
    private float x;
    private float y;
    private float mStartX;
    private float mStartY;
    private OnClickListener mClickListener;
    private TextView txt;
    private View v;
    private WindowManager windowManager = (WindowManager) getContext()
            .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    // 此windowManagerParams变量为获取的全局变量，用以保存悬浮窗口的属性
    private WindowManager.LayoutParams windowManagerParams;
    private boolean move = false;
    boolean alpha = true;
    ListView lv;

    public FloatView(Context context) {
        super(context);
        v = LayoutInflater.from(context).inflate(R.layout.layout_float_view, null);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lv=v.findViewById(R.id.debug_lv);
        this.addView(v, lp);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取到状态栏的高度
        Rect frame = new Rect();
        getWindowVisibleDisplayFrame(frame);
        // 获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY(); // statusBarHeight是系统状态栏的高度

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                // 获取相对View的坐标，即以此View左上角为原点
                mTouchX = event.getX();
                mTouchY = event.getY();
                mStartX = x;
                mStartY = y;
                move = true;
                break;
            case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                if (alpha)
                    updateViewPosition();
                break;

            case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作

                if ((x - mStartX) < 5 && (y - mStartY) < 5) {
                    Log.e("float", "onTouchEvent: " + alpha);
                    alpha = !alpha;
                    if (alpha)
                        v.setBackgroundColor(Color.argb(255, 0, 0, 0));
                    else
                        v.setBackgroundColor(Color.argb(0, 0, 0, 0));
                    if (mClickListener != null) {
                        mClickListener.onClick(this);
                    }
                } else {
//                    updateViewPosition();
//                    mTouchX = x;
//                    mTouchY = y;
                }
                break;
        }
        return true;
    }


    @Override
    public void setOnClickListener(OnClickListener l) {
        this.mClickListener = l;
    }

    public void setWindowParam(WindowManager.LayoutParams params) {
        windowManagerParams = params;
    }

    private void updateViewPosition() {
        windowManagerParams.x = (int) (mTouchX - x + 100);
        windowManagerParams.y = (int) (y - mTouchY - 200);
        Log.e("float", "updateViewPosition: " + windowManagerParams.x + "   " + windowManagerParams.y);
        // 更新浮动窗口位置参数
        if (!move) {

            windowManager.updateViewLayout(this, windowManagerParams); // 刷新显示
        }
        move = false;
    }

    public int getMyX() {
        return windowManagerParams.x;
    }

    public int getMyY() {
        return windowManagerParams.y;
    }

    public ListView getLv() {
        return lv;
    }
}