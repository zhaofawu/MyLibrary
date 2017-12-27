package com.zhao.zhaolibrary.base;


import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import com.zhao.zhaolibrary.R;

/**
 * Created by zhaofawu on 2017/7/13
 */
public   class BaseActivity extends AppCompatActivity{
    protected Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.e("活动", getClass().getSimpleName()+"创建");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        BaseApplication.getInstance().addActivity(this);

    }
    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().removeActivity(this);
        Log.e("活动", getClass().getSimpleName()+"移除");
    }

    public void showPopDialog() {
        if ((!this.isFinishing()) && (dialog == null || !dialog.isShowing())) {
            dialog = new Dialog(this, R.style.MyDialogStyle);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.core_center_loading);
            dialog.setCancelable(false);
            dialog.show();
        }
    }
    public void endLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
