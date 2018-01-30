package com.munin.mhlogwindow;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.munin.mhlogwindow.manager.WindowFloatView;
import com.munin.mhlogwindow.view.FloatView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                Toast.makeText(MainActivity.this, "当前无权限，请授权！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 123);
            } else {
                WindowFloatView.getInstance(this, new FloatView(this));
            }

        } else {
            WindowFloatView.getInstance(this, new FloatView(this));
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < 100) {
                    i++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i % 2 == 0)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MHlog.i("fun", "this is a test");
                            }
                        });

                    else
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MHlog.d("fun!", "this is a test!!!!!!!");
                            }
                        });

                }
            }
        }).start();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123) {
            if (Build.VERSION.SDK_INT >= 23)
                if (!Settings.canDrawOverlays(this)) {
                    Toast.makeText(MainActivity.this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "权限授予成功！", Toast.LENGTH_SHORT).show();
                    //启动FxService
                    WindowFloatView.getInstance(this, new FloatView(this));
                }

        }
    }

}
