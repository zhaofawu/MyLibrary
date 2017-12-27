package com.zhao.zhaolibrary.utils;

import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

/**
 * Created by zhaofawu on 2017/7/14.
 */
public class DeviceUtil {

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return  version;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0";
        }
    }
    /**
     * 获取当前设置的电话号码
     */
    public static String getNativePhoneNumber(Context context) {
        String nativePhoneNumber = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            nativePhoneNumber = telephonyManager.getLine1Number();
        } catch (Exception e) {
            nativePhoneNumber = "";
        }
        return nativePhoneNumber;
    }

    /**
     * 获取设备序列号
     */
    public static String getDeviceId(Context context) {
        String deviceId = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = telephonyManager.getDeviceId();
        if (deviceId == null || "".equals(deviceId)) {
            deviceId = "0000000000";
        }
        return deviceId;
    }

    /**
     * @description：获取设备唯一标识
     */
    public static String getDeviceUni(Context context) {
        String deviceUni = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceUni = tm.getDeviceId();
        deviceUni = "";
        if (TextUtils.isEmpty(deviceUni)) {
            deviceUni = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        }
        return deviceUni;
    }

    /**
     * @description：获取wifimac地址
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String wifiMac = info.getMacAddress();
        return wifiMac;
    }

    /**
     * 获得一个去掉"-"符号的UUID
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        // 去掉"-"符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * 判断是否已连接到网络.
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) { return true; }
            }
        }
        return false;
    }

//    /**
//     * 检查网络接连类型.
//     *
//     * @param context
//     * @return SysConstants.NETWORK_TYPE_NONE: 无网络连接;
//     *         SysConstants.NETWORK_TYPE_WIFI: 通过WIFI连接网络;
//     *         SysConstants.NETWORK_TYPE_WAP: 通过GPRS连接网络.
//     */
//    public static int checkNetWorkType(Context context) {
//        if (isAirplaneModeOn(context)) { return Constant.NETWORK_TYPE_NONE; }
//
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) { return Constant.NETWORK_TYPE_NET; }
//
//        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) {
//            String type = connectivityManager.getActiveNetworkInfo().getExtraInfo();
//            if ("wap".equalsIgnoreCase(type.substring(type.length() - 3))) {
//                return Constant.NETWORK_TYPE_WAP;
//            } else {
//                return Constant.NETWORK_TYPE_NET;
//            }
//        }
//
//        return Constant.NETWORK_TYPE_NONE;
//    }

    /**
     * 判断手机是否处于飞行模式.
     *
     * @param context
     * @return
     */
    public static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    /**
     * 判断手机SDCard是否已安装并可读写.
     *
     * @return
     */
    public static boolean isSDCardUsable() {
        return Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState());
    }

    /**
     * 获取指定的SDCard中图片缓存目录.
     *
     * @param defaultImageFolderName
     * @return
     */
    public static File getImgCacheDir(String defaultImageFolderName) {
        if (isSDCardUsable()) {
            File dir = new File(Environment.getExternalStorageDirectory(), defaultImageFolderName);
            if (!dir.exists())
                dir.mkdirs();

            return dir;
        }
        return null;
    }

    /**
     * 隐藏某焦点控件弹出的软件键盘.
     *
     * @param context
     * @param view
     */
    public static void hideSoftKeyboardFromView(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        IBinder binder = view.getWindowToken();
        inputMethodManager.hideSoftInputFromWindow(binder, 0);
    }

    /**
     * 获取SDcard根目录
     * @description：
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        } else {
            sdDir = new File("/mnt/sdcard/");
            if (!sdDir.exists() || !sdDir.canRead() || !sdDir.canWrite()) { return ""; }
        }
        return sdDir.getAbsolutePath();
    }

}
