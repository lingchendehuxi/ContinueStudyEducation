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
import android.database.DataSetObserver;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.bean.CoursewareArrayBean;
import cn.incongress.continuestudyeducation.bean.PlateArrayBean;
import cn.incongress.continuestudyeducation.bean.PlateDetailBean;

public class CourseListAdapter implements ExpandableListAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<PlateDetailBean.PlateArrayBean> list;

    public CourseListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setData(List<PlateDetailBean.PlateArrayBean> items) {
        this.list = items;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return list.get(groupPosition).getCwArray().size();
    }

    @Override
    public PlateDetailBean.PlateArrayBean getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public PlateDetailBean.PlateArrayBean.CwArrayBean getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getCwArray().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CurrentHolder holder;
        //currentBean  = getGroup(groupPosition);
        PlateDetailBean.PlateArrayBean plateArrayBean = getGroup(groupPosition);
        if (convertView == null) {
            holder = new CurrentHolder();
            convertView = inflater.inflate(R.layout.item_plate_current, parent, false);
            holder.current_title = (TextView) convertView.findViewById(R.id.current_title);
            holder.current_study = (TextView) convertView.findViewById(R.id.current_study);
            holder.current_direction = (ImageView) convertView.findViewById(R.id.current_direction);
            convertView.setTag(holder);
        } else {
            holder = (CurrentHolder) convertView.getTag();
        }
        if(isExpanded){
            holder.current_direction.setImageResource(R.mipmap.up);
        }else{
            holder.current_direction.setImageResource(R.mipmap.down); }
        holder.current_title.setText(plateArrayBean.getPlateName());
        holder.current_study.setText("已学习："+plateArrayBean.getStudyJd());
        return convertView;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChooseHolder holder;
        PlateDetailBean.PlateArrayBean.CwArrayBean cwArrayBean =  getChild(groupPosition, childPosition);
        if (convertView == null) {
            holder = new ChooseHolder();
            convertView = inflater.inflate(R.layout.item_plate_group, parent, false);
            convertView.setTag(holder);
        } else {
            holder = (ChooseHolder) convertView.getTag();
        }
        holder.group_title = (TextView) convertView.findViewById(R.id.group_title);
        holder.group_btn = (TextView) convertView.findViewById(R.id.group_btn);
        holder.group_img = (ImageView) convertView.findViewById(R.id.plate_group_img);

        if(cwArrayBean.getType() == 1){
            holder.group_btn.setText("回顾");
            holder.group_btn.setVisibility(View.VISIBLE);
            holder.group_img.setImageResource(R.mipmap.study_play);
            holder.group_title.setTextColor(context.getResources().getColor(R.color.button_background1));
        }else{
            if(cwArrayBean.getCanStudy() == 1){
                holder.group_img.setImageResource(R.mipmap.study_play);
                holder.group_btn.setVisibility(View.VISIBLE);
                holder.group_btn.setText("开始学习");
                holder.group_title.setTextColor(context.getResources().getColor(R.color.button_background1));
            }else{
                holder.group_btn.setVisibility(View.GONE);
                holder.group_title.setTextColor(context.getResources().getColor(R.color.dark_gray));
                holder.group_img.setImageResource(R.mipmap.study_play1);
            }
        }
        holder.group_title.setText(cwArrayBean.getCwName());
        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }


    private static class ChooseHolder {
        TextView group_title;
        TextView group_btn;
        ImageView group_img;
    }

    private static class CurrentHolder {
        TextView current_title,current_study;ImageView current_direction;
    }

}
