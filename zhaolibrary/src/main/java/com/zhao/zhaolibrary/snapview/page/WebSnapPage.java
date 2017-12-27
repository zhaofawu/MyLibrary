package com.zhao.zhaolibrary.snapview.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.zhao.zhaolibrary.R;
import com.zhao.zhaolibrary.snapview.view.MyWebView;
import com.zhao.zhaolibrary.snapview.widget.SnapPageLayout;


/**
 * Created by axing on 16/12/30.
 */

public class WebSnapPage implements SnapPageLayout.SnapPage {

    private MyWebView myWebView;
    private LayoutInflater inflater;

    public WebSnapPage(Context context) {
        this(context, R.layout.web_snap_layout);
    }

    public WebSnapPage(Context context, int layoutId) {
        this(context, layoutId, null);
    }

    public WebSnapPage(Context context, String webUrl) {
        this(context, R.layout.web_snap_layout, webUrl);
    }

    public WebSnapPage(Context context, int layoutId, String webUrl) {
        inflater = LayoutInflater.from(context);

        myWebView = ((MyWebView) inflater.inflate(layoutId, null));

        if (webUrl != null) {
            myWebView.loadUrl(webUrl);
        }
    }

    @Override
    public View getRootView() {
        return myWebView;
    }

    @Override
    public boolean isAtTop() {
        int scrollY = myWebView.getScrollY();

        if (scrollY == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isAtBottom() {
        int scrollY = myWebView.getScrollY();
        int height = myWebView.getHeight();
        int webViewMeasuredHeight = myWebView.getContentHeight();

        if ((scrollY + height) >= webViewMeasuredHeight) {
            return true;
        }
        return false;
    }
}
