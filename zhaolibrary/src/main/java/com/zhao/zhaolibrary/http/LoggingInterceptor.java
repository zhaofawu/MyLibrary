package com.zhao.zhaolibrary.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.zhao.zhaolibrary.base.BaseApplication;
import com.zhao.zhaolibrary.utils.LogUtil;

import java.io.IOException;
import java.util.Locale;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author zhaofawu
 * @date 2017/11/30 0030.
 */
public class LoggingInterceptor implements Interceptor {

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {

        Request request = chain.request();
        //记录请求的数据，用于调试
        RequestBody formBody=request.body();
        if(formBody instanceof FormBody){
            FormBody body=(FormBody)formBody;
            String bodyTemp="";
            String apiName="";
            for (int i=0;i<body.size();i++){
                if(i==body.size()-1){
                    bodyTemp+= (body.name(i)+"="+body.value(i));
                }else {
                    bodyTemp+= (body.name(i)+"="+body.value(i)+"&");
                }
                if(body.name(i)=="service"){
                    apiName=body.value(i);
                }
            }
            Log.e("请求链接",String.format("%s",request.url()));
            Log.e("请求数据", bodyTemp);
            Log.e("接口名称", apiName);

        }
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Log.e("请求时间：",String.format("%.1fms",(t2 - t1) / 1e6d));

        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LogUtil.log_f("接口数据",content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }


}