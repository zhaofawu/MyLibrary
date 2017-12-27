package com.zhao.zhaolibrary.View.Pop_Gridview.View;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.zhao.zhaolibrary.View.Pop_Gridview.Adapter.AdapterPop_Gridview;
import com.zhao.zhaolibrary.View.Pop_Gridview.Bean.SetuplistBean;
import com.zhao.zhaolibrary.R;
import com.zhao.zhaolibrary.View.Pop_Gridview.inter.PopListview_CallbackInterface;

import java.util.ArrayList;

/**
 * Created by lj on 2017/4/19.
 */
public class Pop_Gridview extends Dialog {

    public Pop_Gridview(Context context) {
        super(context);
    }

    public Pop_Gridview(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private PopListview_CallbackInterface interfaced;
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Pop_Gridview create(ArrayList<SetuplistBean> setuplist,final PopListview_CallbackInterface interfaced) {
//            this.interfaced=interfaced;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final Pop_Gridview dialog = new Pop_Gridview(context,R.style.Dialog);
            View layout = inflater.inflate(R.layout.pop_gridviewlayout, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            LinearLayout ll_gridview=(LinearLayout) layout.findViewById(R.id.ll_gridview);
            ll_gridview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            GridView pop_gridview=(GridView) layout.findViewById(R.id.pop_gridview);
            pop_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dialog.dismiss();
                    interfaced.showCallback(position);
                }
            });
            AdapterPop_Gridview adapter=new AdapterPop_Gridview(setuplist,context);
            pop_gridview.setAdapter(adapter);
            return dialog;
        }
    }
}