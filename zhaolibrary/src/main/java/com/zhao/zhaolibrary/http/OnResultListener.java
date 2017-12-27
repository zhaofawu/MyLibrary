package com.zhao.zhaolibrary.http;


/**
 * @author zhaofawu
 * @date 2017/11/23 0023.
 */

public interface OnResultListener {
    /**
     * 错误回调
     * @param e
     */
    void onError(Throwable e);

    /**
     * 成功回调
     * @param s
     */
    void onResult(String s);
}
