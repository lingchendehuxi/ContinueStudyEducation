package cn.incongress.continuestudyeducation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.bean.CityBean;

/**
 * Created by Jacky on 2016/1/5.
 */
public class CityAdapter extends BaseAdapter {
    private List<CityBean> mCitys;
    private Context mContext;
    private int mChooseCityId;

    public CityAdapter(List<CityBean> citys, Context ctx , int chooseCityId) {
        this.mCitys = citys;
        this.mContext = ctx;
        this.mChooseCityId = chooseCityId;
    }

    @Override
    public int getCount() {
        return mCitys.size();
    }

    @Override
    public Object getItem(int position) {
        return mCitys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_city, null);
            holder = new ViewHolder();
            holder.tvCityName = (TextView) convertView.findViewById(R.id.tv_province_name);
            holder.ivChecked = (ImageView) convertView.findViewById(R.id.iv_choose);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvCityName.setText(mCitys.get(position).getCityName());
        if(mCitys.get(position).getCityId() == mChooseCityId) {
            holder.ivChecked.setVisibility(View.VISIBLE);
        }else {
            holder.ivChecked.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void setChooseId(int cityId) {
        mChooseCityId = cityId;
    }

    class ViewHolder {
        TextView tvCityName;
        ImageView ivChecked;
    }
}
