package cn.incongress.continuestudyeducation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.bean.TeacherArrayBean;
import cn.incongress.continuestudyeducation.uis.CircleImageView;

public class LessonTeacherAdapter extends BaseAdapter {
    private Context mContext;
    private List<TeacherArrayBean> mTeachers;

    public LessonTeacherAdapter(Context ctx, List<TeacherArrayBean> beans) {
        this.mContext = ctx;
        this.mTeachers= beans;
    }

    @Override
    public int getCount() {
        return mTeachers.size();
    }

    @Override
    public Object getItem(int position) {
        return mTeachers.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lesson_teacher, null);
            holder.tvName =  (TextView)convertView.findViewById(R.id.tv_name);
            holder.civTeacher = (CircleImageView) convertView.findViewById(R.id.civ_teacher);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(mTeachers.get(position).getTeacherName());
        if("".equals(mTeachers.get(position).getTeacherUrl())) {
            holder.civTeacher.setImageResource(R.mipmap.teacher_info_default);
        }else {
            ImageLoader.getInstance().displayImage(mTeachers.get(position).getTeacherUrl(), holder.civTeacher, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    ((CircleImageView)view).setImageResource(R.mipmap.teacher_info_circle_default);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvName;
        CircleImageView civTeacher;
    }
}
