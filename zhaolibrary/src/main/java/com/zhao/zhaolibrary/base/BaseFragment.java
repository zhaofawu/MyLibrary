package com.zhao.zhaolibrary.base;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.zhao.zhaolibrary.R;

/**
 * Created by zhaofawu on 2017/7/13
 */
public class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();

                if (dialog != null) {
            dialog.cancel();
        }
    }

    public void showPopDialog() {
        if ((getActivity()!=null) && (dialog == null || !dialog.isShowing())) {
            dialog = new Dialog(getActivity(), R.style.MyDialogStyle);
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
