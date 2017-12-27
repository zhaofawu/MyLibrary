package com.zhao.zhaolibrary.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author zhaofawu
 * @date 2017/11/23 0023.
 */
public class ShowDataUtil {
    public static void  ShowToast(Context context,String msg)
    {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
