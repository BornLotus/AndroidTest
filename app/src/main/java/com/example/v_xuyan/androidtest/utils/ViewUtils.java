package com.example.v_xuyan.androidtest.utils;

import android.content.Context;

/**
 * Created by v-xuyan on 2017/12/14.
 */

public class ViewUtils {

    public static float dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (density * dp + 0.5f);
    }

    public static float px2dp(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (px / density + 0.5f);
    }

}
