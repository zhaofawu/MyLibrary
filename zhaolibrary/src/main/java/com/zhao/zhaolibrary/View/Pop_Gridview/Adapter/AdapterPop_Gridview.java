package com.zhao.zhaolibrary.View.Pop_Gridview.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.zhao.zhaolibrary.View.Pop_Gridview.Bean.SetuplistBean;
import com.zhao.zhaolibrary.R;
import java.util.ArrayList;


/**
 * Created by lj on 2017/4/19.
 */
public class AdapterPop_Gridview extends BaseAdapter {
    private ArrayList<SetuplistBean> list;
    private Context context;
    LayoutInflater inflater;

    public AdapterPop_Gridview(ArrayList<SetuplistBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            convertView = inflater.inflate(R.layout.pop_gridviewitem, null);
            cc=new ViewHolder(convertView);
            convertView.setTag(cc);
        }
        else
        {
            cc=(ViewHolder)convertView.getTag();
        }
        //cc.img.setImageResource(list.get(position).getImg());
        Picasso.with(context).load(list.get(position).getImg()).into(cc.img);
        cc.tvTitle.setText(list.get(position).getAddress());
        return convertView;
    }

    static class ViewHolder {
        ImageView img;
        TextView tvTitle;
        ViewHolder(View view) {
            img=(ImageView) view.findViewById(R.id.img);
            tvTitle=(TextView)view.findViewById(R.id.tv_title);
        }
    }
}
