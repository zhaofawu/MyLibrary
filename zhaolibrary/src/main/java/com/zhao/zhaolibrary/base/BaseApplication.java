package com.zhao.zhaolibrary.base;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by zhaofawu on 2017/7/13.
 */
public class BaseApplication extends Application {

    // 存放activity的集合
    private ArrayList<Activity> list = new ArrayList<>();
    private static BaseApplication instance;
    private static Context mContext;
    public BaseApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 利用单例模式获取MyAppalication实例
     *
     * @return
     */
    public static BaseApplication getInstance() {
        if (null == instance) {
            instance = new BaseApplication();
        }
        return instance;
    }

    /**
     * 添加activity到list集合
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        list.add(activity);
    }

    /**
     * 从list集合中移除activity
     * @param activity
     */
    public void removeActivity(Activity activity){
        list.remove(activity);

    }

    /**
     * 退出集合所有的activity
     */
    public void exit() {
        try {
            for (Activity activity : list) {
                if(!activity.isFinishing()){
                    activity.finish();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            NotificationManager manager=(NotificationManager)getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancelAll();
            System.exit(0);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
