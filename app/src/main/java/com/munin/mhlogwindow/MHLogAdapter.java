package com.munin.mhlogwindow;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * Created by munin on 2018/1/29.
 */

public class MHLogAdapter extends BaseAdapter {
    private LayoutInflater inflator;
    private LinkedList<WeakReference<MHLogData>> logData = new LinkedList<WeakReference<MHLogData>>();

    public MHLogAdapter(Context service) {
        inflator = (LayoutInflater) service.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return logData.size();
    }

    @Override
    public WeakReference<MHLogData> getItem(int position) {
        return logData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeakReference<MHLogData> data = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflator.inflate(R.layout.layout_logcat_item, null);
            holder = new ViewHolder();
            holder.msg = convertView.findViewById(R.id.logcat_txt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MHLogData b = data.get();
        if (data.get() != null) {
            holder.msg.setVisibility(View.VISIBLE);
            try {
                holder.msg.setText(b.getCurrentTime() + "    :" + b.getMsg());
                holder.msg.setTextColor(Color.parseColor(b.getColor()));
            } catch (Exception e) {
                holder.msg.setVisibility(View.GONE);
            }
        }else{
            holder.msg.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void add(MHLogData mhLogData) {
        logData.add(new WeakReference<MHLogData>(mhLogData));
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView msg;
    }

}
