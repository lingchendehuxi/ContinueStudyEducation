package cn.incongress.continuestudyeducation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.bean.NotifyArrayBean;
import cn.incongress.continuestudyeducation.bean.QuestionBean;

/**
 * Created by Jacky on 2015/12/30.
 */
public class QuestionAdapter extends BaseAdapter {
    private Context mContext;
    private List<QuestionBean> mQuestions;

    public QuestionAdapter(Context ctx, List<QuestionBean> questions) {
        this.mContext = ctx;
        this.mQuestions = questions;
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }

    @Override
    public Object getItem(int position) {
        return mQuestions.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_question, null);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tvContent =  (TextView)convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvContent.setText(mQuestions.get(position).getContent());
        holder.tvTime.setText(mQuestions.get(position).getCreateTime());
        return convertView;
    }

    private class ViewHolder {
        TextView tvTime;
        TextView tvContent;
    }
}
