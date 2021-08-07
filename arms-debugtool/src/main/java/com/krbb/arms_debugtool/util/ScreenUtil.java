package com.krbb.arms_debugtool.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * @author IurKwan
 * @date 3/11/21
 */
public class ScreenUtil {

    public static int getDisplayWidthInPx(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return 0;

    }

}
