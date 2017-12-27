package com.zhao.zhaolibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 *   isLogin 是否登录
 *   isRegister 是否显示引导页
 *   isTourist 是否设置过小区
 *   union_id  用户编号
 *   tourist_name 小区名称
 *   tourist_id 小区编号
 *   account 账号名
 *
 *   uid 用户编号 未加密
 *
 *   GizWifiSDK 是否需要授权 true 执行初始化
 *
 *   Time 时间判断，用于实现 登录过期功能。
 * Created by zhaofawu on 2017/7/14.
 */
public class SPUtil {
    private static SPUtil sSingleton = null;
    private Context context;

    public static synchronized SPUtil getInstance() {
        if (sSingleton == null) {
            sSingleton = new SPUtil();
        }
        return sSingleton;
    }

    /**
     * 清空数据
     */
    public void clear(){
        SharedPreferences userSettings = context.getSharedPreferences("winplesmart", 0);
        SharedPreferences.Editor editor=userSettings.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 存储单String个值
     * @param context
     * @param key
     * @param value
     * @return
     */
    public Boolean setSP_Stringdata(Context context,String key ,String value)
    {
        if(context==null)
            return  false;
        if(key==null||key.equals("")||value==null)
        {
            return  false;
        }else
        {
           try
           {
               SharedPreferences userSettings = context.getSharedPreferences("winplesmart", 0);
               SharedPreferences.Editor editor = userSettings.edit();
               editor.putString(key,value);
               editor.commit();

           }catch (Exception e)
           {
               return false;
           }
            return  true;
        }
    }

    /**
     * 存储单个Int值
     * @param context
     * @param key
     * @param value
     * @return
     */
    public Boolean setSP_Intdata(Context context,String key ,int value)
    {
        if(context==null)
            return  false;
        if(key==null||key.equals(""))
        {
            return  false;
        }else
        {
            try
            {
                SharedPreferences userSettings = context.getSharedPreferences("winplesmart", 0);
                SharedPreferences.Editor editor = userSettings.edit();
                editor.putInt(key,value);
                editor.commit();
            }catch (Exception e)
            {
                return false;
            }
            return  true;
        }
    }

    /**
     * 存储单个Boolean值
     * @param context
     * @param key
     * @param value
     * @return
     */
    public Boolean setSPBooleandata(Context context,String key ,Boolean value)
    {
        if(context==null)
            return  false;
        if(key==null||key.equals(""))
        {
            return  false;
        }else
        {
            try
            {
                SharedPreferences userSettings = context.getSharedPreferences("winplesmart", 0);
                SharedPreferences.Editor editor = userSettings.edit();
                editor.putBoolean(key,value);
                editor.commit();
            }catch (Exception e)
            {
                return false;
            }
            return  true;
        }
    }

    /**
     * 去单个String值
     * @param context
     * @param key
     * @return
     */
    public  String GetSPStringdata(Context context,String key)
    {
       try {
           SharedPreferences userSettings = context.getSharedPreferences("winplesmart", 0);
           String s=userSettings.getString(key,"");
           return s;
       }
       catch (Exception e)
       {
           return  "";
       }
    }

    /**
     * 去单个int值
     * @param context
     * @param key
     * @return
     */
    public  int GetSPintdata(Context context,String key)
    {
        try {
            SharedPreferences userSettings = context.getSharedPreferences("winplesmart", 0);
            int s=userSettings.getInt(key,0);
            return s;
        }
        catch (Exception e)
        {
            return  0;
        }
    }

    /**
     * 去单个Boolean值
     * @param context
     * @param key
     * @return
     */
    public  Boolean GetSPBooleandata(Context context,String key)
    {
        try {
            SharedPreferences userSettings = context.getSharedPreferences("winplesmart", 0);
            Boolean s=userSettings.getBoolean(key,false);
            return s;
        }
        catch (Exception e)
        {
            return  false;
        }
    }

    public boolean setList(Context context,String key, List<String> list){
        if(context==null)
            return  false;
        if(key==null||key.equals("")||list==null)
        {
            return  false;
        }else {
            try {
                SharedPreferences sp = context.getSharedPreferences("winplesmart", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt(key + "_size", list.size());

                for (int i = 0; i < list.size(); i++) {
                    editor.remove(key + i);
                    editor.putString(key + i, list.get(i));
                }
                return editor.commit();
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }

    public List<String> getList(Context context,String key){
        try {
            List<String> list=new ArrayList<>();
            SharedPreferences preferences = context.getSharedPreferences("winplesmart", 0);
            int size = preferences.getInt(key+"_size", 0);
            for(int i=0;i<size;i++) {
                list.add(preferences.getString(key + i, ""));
            }
            return list;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public void clearList(Context context,String key){

        try {
            SharedPreferences preferences = context.getSharedPreferences("winplesmart", 0);
            SharedPreferences.Editor editor = preferences.edit();
            int size = preferences.getInt(key+"_size", 0);
            for(int i=0;i<size;i++) {
                editor.remove(key+i);
            }
            editor.remove(key+"_size");
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
