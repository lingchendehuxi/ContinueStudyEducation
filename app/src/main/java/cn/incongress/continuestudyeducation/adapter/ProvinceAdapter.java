package cn.incongress.continuestudyeducation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.bean.ProvinceBean;

/**
 * Created by Jacky on 2016/1/4.
 */
public class ProvinceAdapter extends BaseAdapter {
    private List<ProvinceBean> mProvinces;
    private Context mContext;

    public ProvinceAdapter(List<ProvinceBean> mProvinces, Context mContext) {
        this.mProvinces = mProvinces;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mProvinces.size();
    }

    @Override
    public Object getItem(int position) {
        return mProvinces.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_province, null);
            holder = new ViewHolder();
            holder.tvProvinceName = (TextView) convertView.findViewById(R.id.tv_province_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvProvinceName.setText(mProvinces.get(position).getProvinceName());
        return convertView;
    }

    class ViewHolder {
        TextView tvProvinceName;
    }
}
