package cn.incongress.continuestudyeducation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.bean.CoursewareArrayBean;
import cn.incongress.continuestudyeducation.bean.NotifyArrayBean;

/**
 * Created by Jacky on 2015/12/24.
 */
public class NotifyListAdapter extends BaseAdapter {

        private Context mContext;
        private List<NotifyArrayBean> mNotifys;

        public NotifyListAdapter(Context ctx, List<NotifyArrayBean> notifys) {
            this.mContext = ctx;
            this.mNotifys = notifys;
        }

        @Override
        public int getCount() {
            return mNotifys.size();
        }

        @Override
        public Object getItem(int position) {
            return mNotifys.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if(convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_notify, null);
                holder.tvContent =  (TextView)convertView.findViewById(R.id.tv_content);
                holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvContent.setText(mNotifys.get(position).getContent());
            holder.tvTime.setText(mNotifys.get(position).getDateTime());
            return convertView;
        }

        private class ViewHolder {
            TextView tvContent;
            TextView tvTime;
        }
}
