package com.zhao.zhaolibrary.http;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by zhaofawu on 2017/7/14.
 */
public class Api {
        /**
         * 单例对象实例
        */
       private static Api instance = null;

            public synchronized static Api getInstance() {
                if (instance == null) {
                    instance = new Api();
                 }
             return instance;
            }
    /**
     * 启动页信息获取
     * @return
     */
    public RequestBody star()
    {
        Map<String,String> map=GetMap();
        map.put("service", "Global/start");
        map.put("system","2");
        return GetSign(map);
    }

    //新加接口分割线

    public RequestBody GetSign_file(Map<String,String> map,ArrayList<String> file)
    {
        String MD5String="";
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value=  map.get(key);
            if(isContainChinese(value))
            {
                try {
                    value = URLEncoder.encode(value, "UTF-8");
                }
                catch (Exception e)
                {
                }
            }
            MD5String+=key+"="+value+"&";
        }
        MD5String=MD5String.substring(0,MD5String.length()-1);
        MD5String+="941E56C077576AC099FAD05203B5A705";
        String sign=md5(MD5String);
        map.put("sign",sign);
        return  SetFile(file,map,"avatar");
    }
    private  RequestBody SetFile(ArrayList<String> image,Map<String, String> BodyParams,String fileParams)
    {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/JPEG");
        RequestBody requestBody=null;
        try
        {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            //map的参数加进去
            for (Map.Entry<String, String> entry:BodyParams.entrySet()){
                builder.addFormDataPart(entry.getKey(),entry.getValue());
            }
            //把图片参数加进去
            for(int i=0;i<image.size();i++){
                File file=new File(image.get(i));
                if(file.exists()){
                    Log.e("GetNetBase", "SetFile: " + image.get(i));
                    builder.addFormDataPart(fileParams+i,file.getName(), RequestBody.create(MEDIA_TYPE_PNG,file));
                }
            }
            return builder.build();

        }
        catch ( Exception e)
        {
            Log.e("hxl","错误：--"+e.toString());
        }
        return requestBody;
    }

    /**
     * 加密
     * @param map
     * @return
     */
    public RequestBody GetSign(Map<String,String> map)
    {
        String MD5String="";
        FormBody.Builder formBody = new FormBody.Builder();
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value=  map.get(key);
            if(isContainChinese(value))
            {
               try {
                   formBody.add(key,value);
                   Log.e("hxl","转码前"+value);
                   value = URLEncoder.encode(value, "UTF-8");
                   Log.e("hxl","转码后"+value);
               }
               catch (Exception e)
               {

               }
            }
            else
            {
                formBody.add(key,value);
            }
            MD5String+=key+"="+value+"&";
        }
        MD5String=MD5String.substring(0,MD5String.length()-1);
        MD5String+="941E56C077576AC099FAD05203B5A705";
        String sign=md5(MD5String);
        formBody.add("sign",sign);
        return  formBody.build();

    }

    /**
     * 判断是否中文
     */
    static Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
    public  boolean isContainChinese(String str) {
        Matcher m = p.matcher(str);
        return m.find();
    }

    public RequestBody GetSign_avatar(Map<String,String> map,String avatar)
    {
        String MD5String="";
        FormBody.Builder formBody = new FormBody.Builder();
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value=  map.get(key);
            MD5String+=key+"="+value+"&";
            formBody.add(key,value);
        }
        MD5String=MD5String.substring(0,MD5String.length()-1);
        MD5String+="941E56C077576AC099FAD05203B5A705";
        String sign=md5(MD5String);
        formBody.add("sign",sign);
        formBody.add("avatar",avatar);
        return  formBody.build();
    }
    public Map<String, String>  GetMap()
    {
        Map<String, String> map = new TreeMap<String, String>(
                new Comparator<String>() {
                    @Override
                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj1.compareTo(obj2);
                    }
                });
        map.put("version", "1.0");
        map.put("formvar", "json");
        return map;
    }

    public  String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
