package com.zhao.zhaolibrary.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.io.File;
import java.io.IOException;



import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @author zhaofawu
 * @date 2017/11/23 0023.
 */
public class OkHttpEngine {
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static OkHttpEngine mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    public static OkHttpEngine getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpEngine.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpEngine();
                }
            }
        }
        return mInstance;
    }

    private OkHttpEngine() {
        mHandler = new Handler(Looper.getMainLooper());
    }
    private void dealResult(Call call, final ResultCallback callback,final String name) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedCallback(e, callback,name);
            }

            @Override
            public void onResponse(Call call, final Response response1) throws IOException {
                sendSuccessCallback(response1, callback,name);
            }

        });
    }

    private void sendSuccessCallback(final Response object,final   ResultCallback callback,final String name) {

        try {
            final String  msg=   object.body().string();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (callback != null) {
                        try
                        {
                            callback.onResponse(name,msg);
                        }
                        catch (Exception e)
                        {
                            Log.e("hxl","错误"+e.toString());
                            callback.onResponse(name,"");
                        }
                    }
                }
            });
        }
        catch (Exception e)
        {
            callback.onResponse(name,"");
        }
    }

    private void sendFailedCallback( final Exception e, final ResultCallback callback,final String name) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onError(name, e);
                }
            }
        });
    }

    /**
     * 异步get请求
     * @param callback
     */
    public void getAsynHttp( ResultCallback callback,String name) {

        Request request = new Request.Builder()
                .url(Uri.coopApi)
                .build();
        Call call = mOkHttpClient.newCall(request);
        dealResult(call, callback,name);

    }

    /**
     * 异步Post 请求
     * @param callback
     */
    public void postAsynHttp( ResultCallback callback,String name,RequestBody formBody) {
        //记录请求的数据，用于调试
        if(formBody instanceof FormBody){
            FormBody body=(FormBody)formBody;
            String bodyTemp="";
            for (int i=0;i<body.size();i++){
                if(i==body.size()-1){
                    bodyTemp+= (body.name(i)+"="+body.value(i));
                }else {
                    bodyTemp+= (body.name(i)+"="+body.value(i)+"&");
                }
            }
            Log.e("请求的链接：", Uri.coopApi);
            Log.e("请求的数据：", bodyTemp);

        }
        if(formBody instanceof MultipartBody){
            MultipartBody body=(MultipartBody)formBody;
            String bodyTemp="";
            for (int i=0;i<body.size();i++){
                MultipartBody.Part park=body.part(i);
                if(i==body.size()-1){
                    bodyTemp+= (park.toString());
                }else {
                    bodyTemp+= (body.part(i)+"&");
                }
            }
            Log.e("请求的链接：", Uri.coopApi);
        }


        mOkHttpClient=new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(Uri.coopApi)
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        dealResult(call, callback,name);

    }


    /**
     * 异步上传单个文
     * @param callback
     * @param file
     */
    public void postAsynFile( ResultCallback callback, File file,String name) {
        Request request = new Request.Builder()
                .url(Uri.coopApi)

                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();

        Call call = mOkHttpClient.newCall(request);
        dealResult(call, callback,name);
    }


    public void sendMultipart( ResultCallback callback,String name){
        mOkHttpClient = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "wangshu")
                .addFormDataPart("image", "wangshu.jpg",
                        RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/wangshu.jpg")))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url(Uri.coopApi)
                .post(requestBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        dealResult(call, callback,name);
    }
}
