package com.zhao.zhaolibrary.http;

import android.util.Log;

import com.zhao.zhaolibrary.base.BaseApplication;
import com.zhao.zhaolibrary.utils.LogUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author zhaofawu
 * @date 2017/11/23 0023.
 */

public class Http {
    /**请求的链接*/
    public static final  String url="http://47.95.249.132/o2o/api.php";
    /**请求客户端*/
    private OkHttpClient mOkHttpClient;
    /**结果回调*/
    OnResultListener onResultListener;
    /**订阅对象，可以用来取消订阅*/
    Disposable disposable;
    /**实例*/
    private static Http instance;
    /**获取实例*/
    public static Http getInstance(){
        if(instance==null){
            synchronized (Http.class){
                if(instance==null){
                    instance=new Http();
                }
            }
        }
        return instance;
    }

    public Http post(final RequestBody formBody) {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                mOkHttpClient=new OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .addInterceptor(new LoggingInterceptor())
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                Response response = mOkHttpClient.newCall(request).execute();
                e.onNext(response.body().string());
            }
        }).subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Observer<String>() {
              @Override
              public void onSubscribe(Disposable d) {
                    disposable=d;
              }

              @Override
              public void onNext(String s) {

                    if(onResultListener!=null){
                        onResultListener.onResult(s);
                    }
              }

              @Override
              public void onError(Throwable e) {
                  if(onResultListener!=null){
                      Log.e("Http", "onError: " + e.getMessage());
                      onResultListener.onError(e);
                  }
              }

              @Override
              public void onComplete() {

              }
          });
        return this;
    }

    public void dispose(){
        if(disposable!=null){
            disposable.dispose();
        }
    }

    public void getResult(OnResultListener onResultListener){
        this.onResultListener=onResultListener;
    }

}
