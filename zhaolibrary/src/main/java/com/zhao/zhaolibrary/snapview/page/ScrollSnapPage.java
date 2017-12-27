package com.zhao.zhaolibrary.snapview.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.zhao.zhaolibrary.R;
import com.zhao.zhaolibrary.snapview.view.MyScrollView;
import com.zhao.zhaolibrary.snapview.widget.SnapPageLayout;
/**
 * Created by axing on 16/12/29.
 */

public class ScrollSnapPage implements SnapPageLayout.SnapPage {

    private LayoutInflater inflater;

    private MyScrollView myScrollView;
    private LinearLayout linearLayout;

    public ScrollSnapPage(Context context) {
        this(context, R.layout.scroll_snap_layout, -1);
    }

    public ScrollSnapPage(Context context, int childLayoutId) {
        this(context, R.layout.scroll_snap_layout, childLayoutId);
    }

    public ScrollSnapPage(Context context, int parentLayoutId, int childLayoutId) {
        inflater = LayoutInflater.from(context);

        myScrollView = ((MyScrollView) inflater.inflate(parentLayoutId, null));

        linearLayout = ((LinearLayout) myScrollView.findViewById(R.id.linearLayout));

        if (childLayoutId != -1)
            addChild(childLayoutId);
    }

    public void addChild(View childView) {
        if (linearLayout != null) {
            linearLayout.addView(childView);
        }
    }

    public void addChild(View childView, LinearLayout.LayoutParams params) {
        if (linearLayout != null) {
            linearLayout.addView(childView, params);
        }
    }

    public void addChild(int childLayoutId) {
        addChild(inflater.inflate(childLayoutId, null));
    }

    @Override
    public View getRootView() {
        return myScrollView;
    }

    @Override
    public boolean isAtTop() {
        int scrollY = myScrollView.getScrollY();

        if (scrollY == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isAtBottom() {
        int scrollY = myScrollView.getScrollY();
        int height = myScrollView.getHeight();
        int scrollViewMeasuredHeight = myScrollView.getChildAt(0).getMeasuredHeight();

        if ((scrollY + height) >= scrollViewMeasuredHeight) {
            return true;
        }
        return false;
    }


}
