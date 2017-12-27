package com.zhao.zhaolibrary.View.MyGridView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by lj on 2017/3/14.
 */
public class MyGridView extends GridView {

    public MyGridView(Context context) {
        super(context);

    }
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}