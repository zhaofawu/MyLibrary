package com.zhao.zhaolibrary.snapview.util;

import android.util.Log;

/**
 * Created by axing on 16/12/30.
 */

public class LogUtil {
    private static final boolean DEBUG = false;

    public static void e(String string) {
        if (DEBUG) {
            Log.e("TAG", string);
        }
    }
}
