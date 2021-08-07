package com.jess.arms.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author IurKwan
 * @date 3/11/21
 */
public class DisplayUtil {

    public static int dp2px(float dp, Resources resources) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

}
