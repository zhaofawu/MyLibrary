package com.zhao.zhaolibrary.View.MyGridView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhao.zhaolibrary.R;
import java.util.ArrayList;

/**
 * Created by lj on 2017/3/23.
 */
public class Adapter_MyGridView extends BaseAdapter {
    private ArrayList<MyGridViewBean> G_list;
    private Context context;
    private LayoutInflater inflater;

    public Adapter_MyGridView(ArrayList<MyGridViewBean> g_list,Context context) {
        G_list = g_list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return G_list == null ? 0 : G_list.size();
    }

    @Override
    public Object getItem(int position) {
        return G_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder cc=null;
        if(convertView==null)
        {
            convertView = inflater.inflate(R.layout.mygridview_item, null);
            cc=new ViewHolder(convertView);
            convertView.setTag(cc);
        }else
        {
            cc=(ViewHolder) convertView.getTag();
        }
        cc.tvTitle.setText(G_list.get(position).getTitle());
        cc.imgTitle.setImageResource(G_list.get(position).getImg());
        return convertView;
    }

    static class ViewHolder {

        ImageView imgTitle;
        TextView tvTitle;
        ViewHolder(View view) {
            imgTitle=(ImageView) view.findViewById(R.id.img_title);
            tvTitle=(TextView) view.findViewById(R.id.tv_title);
        }
    }
}
