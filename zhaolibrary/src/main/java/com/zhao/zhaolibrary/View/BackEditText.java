package com.zhao.zhaolibrary.View;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;


/**
 * Created by 赵法武 on 2017/11/15 0015.
 * 自定义EditText,监听返回键事件，实现在点击EditText，软键盘弹出的情况下，按下返回键的时候，做一些其他事情
 */

public class BackEditText extends AppCompatEditText {
    public BackEditText (Context context) {
        super(context);
    }

    public BackEditText (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackEditText (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface BackListener {
        void back(BackEditText editText);
    }



    private BackListener listener;

    public void setBackListener(BackListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (listener != null) {
                listener.back(this);
            }
        }
        return false;
    }
}