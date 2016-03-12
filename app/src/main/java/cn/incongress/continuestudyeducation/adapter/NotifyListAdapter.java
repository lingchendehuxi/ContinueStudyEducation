package cn.incongress.continuestudyeducation.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.base.ListBaseAdapter;
import cn.incongress.continuestudyeducation.bean.NotifyArrayBean;

/**
 * Created by Jacky on 2015/12/24.
 */
public class NotifyListAdapter extends ListBaseAdapter<NotifyArrayBean> {

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null || convertView.getTag() == null) {
            convertView = getLayoutInflater(parent.getContext()).inflate(R.layout.item_notify, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final NotifyArrayBean item = mDatas.get(position);
        holder.tvContent.setText(item.getContent());
        holder.tvTime.setText(item.getDateTime());

        return convertView;
    }


    private class ViewHolder {
        TextView tvContent;
        TextView tvTime;

        public ViewHolder(View itemView) {
            tvContent =  (TextView)itemView.findViewById(R.id.tv_content);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
