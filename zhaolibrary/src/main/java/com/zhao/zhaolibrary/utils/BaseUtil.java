package com.zhao.zhaolibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhaofawu on 2017/7/14.
 */
public class BaseUtil {

    private static BaseUtil instance = null;
    /**
     * 单例对象实例
     */
    public synchronized static BaseUtil getInstance() {
        if (instance == null) {
            instance = new BaseUtil();
        }
        return instance;
    }
    /**
     * 判断手机格式是否正确
     * @param mobiles 手机号
     * @return
     */
    static Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
    public boolean isMobileNO(String mobiles) {
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断email格式是否正确
     * @param email
     * @return
     */
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断是否是整数
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否是浮点数
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 将字符串转为时间戳
     * @param user_time
     * @return
     */

    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        }catch (ParseException e) {

        }
        return re_time;
    }

    /**
     *  将时间戳转为字符串
     * @param cc_time
     * @return
     */

    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 例如：
//        cc_time=1291778220 ;
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }
    /**
     *  将时间戳转为字符串
     * @param cc_time
     * @return
     */

    public static String getYMDTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    public static String toDateDistance(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date endDate = new Date(System.currentTimeMillis());// 获取当前时间
            Date startDate = format.parse(time);
            if (startDate == null || endDate == null) {
                return null;
            } else {
                long timeLong = endDate.getTime() - startDate.getTime();
                if (timeLong < 60 * 1000) {
                    return "刚刚";

                } else if (timeLong < 60 * 60 * 1000) {
                    timeLong = timeLong / 1000 / 60;
                    return timeLong + "分钟前";

                } else if (timeLong < 60 * 60 * 24 * 1000) {
                    timeLong = timeLong / 60 / 60 / 1000;
                    return timeLong + "小时前";

                } else {
                    timeLong = timeLong / 1000 / 60 / 60 / 24;
                    return timeLong + "天前";
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * dp转成px
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转成dp
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context,float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 把手机号中间四位用星号代替
     * @param pNumber  手机号码
     * @return
     */
    public static String phoneTostarPhone(String pNumber){
        if(!TextUtils.isEmpty(pNumber) && pNumber.length() > 6 ){
            StringBuilder sb  =new StringBuilder();
            for (int i = 0; i < pNumber.length(); i++) {
                char c = pNumber.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }else {
            return "";
        }
    }

    /**
     * 获取指定两个字符串之间的字符串
     *
     * @param data
     * @param start
     * @param end
     * @return
     */
    public static String getSubString(String data, String start, String end) {
        int s = data.indexOf(start);
        if (s == -1) {
            return "";
        }
        data = data.substring(s+start.length());
        int e = data.indexOf(end);
        if (e == -1) {
            return "";
        }
        return data.substring(0, e);
    }


    /**
     * 获取wifi的MAC
     *
     * @param context
     * @return
     */
    public static String getWifiMac(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return getSubString(wifiInfo.toString(), "BSSID:", ",").trim();
    }

    public static String getSpanTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
            Date date = format.parse("2017-10-20 14:19:07");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * @description：获取设备唯一标识 1、正常情况下可以通过((TelephonyManager) s_instance.getSystemService( Context.TELEPHONY_SERVICE )).getDeviceId();  来获取，但是某些平板电脑此函数会返回空
     * 2、通过 Secure.getString(s_instance.getContentResolver(), Secure.ANDROID_ID);   也可以获取到一个id，但是android2.2或者是某些山寨手机使用这个也是有问题的，它会返回一个固定的值 9774d56d682e549c
     * 3、如果前两个都没有获取到udid，那么就在程序启动的时候创建一个随机的uuid，然后保存起来。这个算是兼容方案，当然这样的设备并不会很多。
     */
    public static String getDeviceUUID(Context context) {
        String deviceUni = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceUni = tm.getDeviceId();
        if (TextUtils.isEmpty(deviceUni)) {
            deviceUni = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUni;
    }

    /**
     * double转String,保留小数点后两位
     * @param num
     * @return
     */
    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public static void setBackgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().setAttributes(lp);
    }

    public static List<String> cutString(String content,String flag){
        if(content==null){
            return new ArrayList<>();
        }
        String[] strings=content.split(flag);
        return new ArrayList<>(Arrays.asList(strings));
    }

    /**
     * 深度拷贝，不过需要T实现序列化Serializable或者其他序列化
     * @param src
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> ArrayList<T> deepCopy(ArrayList<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        ArrayList<T> dest = (ArrayList<T>) in.readObject();
        return dest;
    }

    public static String  test(){
        return "测试";
    }
}
