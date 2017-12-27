package com.zhao.zhaolibrary.http;

/**
 * Created by zhaofawu on 2017/7/14.
 */
public abstract class ResultCallback<T>
{
     public abstract void onError(String name, Exception e);
     public abstract void onResponse(String name,String response);
}