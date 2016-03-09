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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.bean.PlateArrayBean;

public class ListAdapterHolder extends RecyclerView.Adapter<ListAdapterHolder.ViewHolder> {

    private List<PlateArrayBean> mCourseBeanList = new ArrayList<PlateArrayBean>();
    OnItemClickListener mItemClickListener;

    public ListAdapterHolder(List<PlateArrayBean> beans) {
        this.mCourseBeanList = beans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_home_fragment, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder , int position) {
        PlateArrayBean bean = mCourseBeanList.get(position);
        holder.tvPlateName.setText(bean.getPlateName());

        if(bean.getIsFinish() == 1) {
            holder.btStartStudy.setBackgroundResource(R.drawable.button_background2_finish);
        }else {

        }
    }

    @Override
    public int getItemCount() {
        return mCourseBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvPlateName;
        Button btStartStudy;

        public ViewHolder(View view) {
            super(view);
            tvPlateName = (TextView) view.findViewById(R.id.tv_plate_name);
            btStartStudy = (Button) view.findViewById(R.id.bt_start_study);
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
