/*
 * Copyright (C) 2014 VenomVendor <info@VenomVendor.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cn.incongress.continuestudyeducation.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.bean.CoursewareArrayBean;
import cn.incongress.continuestudyeducation.bean.PlateArrayBean;

public class CourseListAdapter extends BaseAdapter {
    private Context mContext;
    private List<CoursewareArrayBean> mCoursesName;

    public CourseListAdapter(Context ctx, List<CoursewareArrayBean> coursesName) {
        this.mContext = ctx;
        this.mCoursesName = coursesName;
    }

    @Override
    public int getCount() {
        return mCoursesName.size();
    }

    @Override
    public Object getItem(int position) {
        return mCoursesName.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_plate_course, null);
            holder.tvCourseName =  (TextView)convertView.findViewById(R.id.tv_course_name);
            holder.ivVideoInfo = (ImageView) convertView.findViewById(R.id.iv_video_pic);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvCourseName.setText(mCoursesName.get(position).getCoursewareTitle());
        if(!mCoursesName.get(position).getPicUrl().equals(holder.ivVideoInfo.getTag())) {
            holder.ivVideoInfo.setTag(mCoursesName.get(position).getPicUrl());
            ImageLoader.getInstance().displayImage(mCoursesName.get(position).getPicUrl(), holder.ivVideoInfo);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tvCourseName;
        ImageView ivVideoInfo;
    }
}
