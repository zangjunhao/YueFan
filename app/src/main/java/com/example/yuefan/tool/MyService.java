package com.example.yuefan.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by 67698 on 2018/8/20.
 */

public class MyService {

    public static int getDisplayWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    public static int dip2px(Context mContext, int dp)
    {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp*density+0.5);
    }

    public static void setStatusBar(Activity activity){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#ff555d82"));
        }
    }
}
