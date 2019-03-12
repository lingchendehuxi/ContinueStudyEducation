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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.bean.AttachStudyBean;

public class ListAdapterHolder extends RecyclerView.Adapter<ListAdapterHolder.ViewHolder> {

    private Context mContext;
    private List<AttachStudyBean.CwArrayBean> mCourseBeanList = new ArrayList<AttachStudyBean.CwArrayBean>();
    OnItemClickListener mItemClickListener;

    public ListAdapterHolder(List<AttachStudyBean.CwArrayBean> beans, Context context) {
        this.mCourseBeanList = beans;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_attach_study, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder , int position) {
        AttachStudyBean.CwArrayBean bean = mCourseBeanList.get(position);
        holder.title.setText(bean.getCwName());
        holder.time.setText(mContext.getResources().getString(R.string.study_time, bean.getCwTime()));
        holder.teacher.setText(mContext.getResources().getString(R.string.next_teacher_name,bean.getTeachName()));
        if(position == 0){
            holder.dotted_state_top.setVisibility(View.GONE);
        }else if(position == mCourseBeanList.size()-1){
            holder.dotted_state_btm.setVisibility(View.GONE);
        }if(bean.getIsFinish() == 1 && position+1 != mCourseBeanList.size()){
            holder.line_state_btm.setVisibility(View.VISIBLE);
            holder.dotted_state_btm.setVisibility(View.GONE);
        }else if(bean.getIsFinish() == 0  && position+1 != mCourseBeanList.size()){
            holder.line_state_btm.setVisibility(View.GONE);
            holder.dotted_state_btm.setVisibility(View.VISIBLE);
        }
        if ( position != 0 && mCourseBeanList.get(position-1).getIsFinish() == 1){
            holder.line_state_top.setVisibility(View.VISIBLE);
            holder.dotted_state_top.setVisibility(View.GONE);
        }else if( position != 0 && mCourseBeanList.get(position-1).getIsFinish() == 0){
            holder.line_state_top.setVisibility(View.GONE);
            holder.dotted_state_top.setVisibility(View.VISIBLE);
        }
        if(bean.getIsFinish() == 1){
            if(bean.getType() == 1){
                holder.img_state_look.setImageResource(R.mipmap.play3);
                holder.title.setTextColor(mContext.getResources().getColor(R.color.button_background2));
            }else{
                holder.img_state_look.setImageResource(R.mipmap.play1);
                holder.title.setTextColor(mContext.getResources().getColor(R.color.button_background2));
            }
        }else{
            if(bean.getType() == 1){
                holder.img_state_look.setImageResource(R.mipmap.play3);
                holder.title.setTextColor(mContext.getResources().getColor(R.color.button_background2));
            }else{
                if(bean.getCanStudy() == 1){
                    holder.title.setTextColor(mContext.getResources().getColor(R.color.button_background1));
                }else{
                    holder.title.setTextColor(mContext.getResources().getColor(R.color.black_overlay));
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return mCourseBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title,teacher,time;
        ImageView dotted_state_top,line_state_btm,dotted_state_btm,line_state_top,img_state_look;
        //Button btStartStudy;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.study_item_title);
            teacher = (TextView) view.findViewById(R.id.study_item_teacher);
            time = (TextView) view.findViewById(R.id.study_item_time);
            line_state_top = (ImageView) view.findViewById(R.id.line_state_top);
            line_state_btm = (ImageView) view.findViewById(R.id.line_state_btm);
            dotted_state_top = (ImageView) view.findViewById(R.id.dotted_state_top);
            dotted_state_btm = (ImageView) view.findViewById(R.id.dotted_state_btm);
            img_state_look = (ImageView) view.findViewById(R.id.img_state_look);
            //btStartStudy = (Button) view.findViewById(R.id.bt_start_study);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
