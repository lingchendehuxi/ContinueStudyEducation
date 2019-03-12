package cn.incongress.continuestudyeducation.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.activity.AttachStudyActivity;
import cn.incongress.continuestudyeducation.activity.MyCertificateActivity;
import cn.incongress.continuestudyeducation.activity.PlateDetailActivity;
import cn.incongress.continuestudyeducation.bean.StudyCenterArrayBean;
import cn.incongress.continuestudyeducation.bean.StudyCenterBean;

/**
 * Created by Jacky on 2017/4/5.
 */

public class StudyCenterAdapter extends RecyclerView.Adapter<StudyCenterAdapter.ViewHolder> {

    private Context mContext;
    private List<StudyCenterArrayBean> mCourseBean = new ArrayList<>();
    private StudyCenterAdapter.OnItemClickListener mItemClickListener;

    public StudyCenterAdapter(List<StudyCenterArrayBean> beans, Context c) {
        this.mCourseBean = beans;
        this.mContext = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_studycenter, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder , final int position) {

        final StudyCenterArrayBean studyBean = mCourseBean.get(position);
        if(studyBean.getCourseType() == 2){
            //已结束
            holder.item_bg.setBackgroundColor(mContext.getResources().getColor(R.color.item_bg));
            holder.pass_img.setVisibility(View.VISIBLE);
            holder.alreadylearning_top.setVisibility(View.GONE);
            holder.study_layout.setVisibility(View.GONE);;

            holder.pass_img.setImageResource(R.mipmap.pass_img);
            holder.top_btn.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.study_btn2));
            holder.separate_img.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.study_item_title.setTextColor(mContext.getResources().getColor(R.color.textcolor));
            holder.study_item_title.setText(studyBean.getCourseName());
            holder.cnt_teacher.setText(studyBean.getTeacherName());
            holder.study_overday.setText(studyBean.getHasTime());
            holder.not_cnt.setText(studyBean.getStudyJd());
            holder.cnt_remark.setText(studyBean.getRemark());

            if (studyBean.getStateType() == 2) {
                if(studyBean.getIsPass() == 1){
                    holder.item_bg.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    holder.separate_img.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                    holder.top_btn.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.study_btn1));
                    holder.study_item_title.setTextColor(mContext.getResources().getColor(R.color.button_background2));
                    holder.pass_img.setImageResource(R.mipmap.pass_adopt);
                    holder.top_btn.setText("查看证书▷");
                    holder.top_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext.startActivity(new Intent(mContext, MyCertificateActivity.class));
                        }
                    });
                }else{
                    holder.top_btn.setText("课程回顾▷");
                    holder.top_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PlateDetailActivity.startPlateDetailActivity(mContext, studyBean.getPuuId(), studyBean.getCuuId(), studyBean.getCourseName(),studyBean.getCourseType());
                        }
                    });
                }
            }else{
                holder.top_btn.setText("课程回顾▷");
                holder.top_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlateDetailActivity.startPlateDetailActivity(mContext, studyBean.getPuuId(), studyBean.getCuuId(), studyBean.getCourseName(),studyBean.getCourseType());
                    }
                });
            }
        }else {
            holder.item_bg.setBackgroundColor(mContext.getResources().getColor(R.color.day_layout_bg_pressedtwo));
            if (studyBean.getStateType() == 1) {
                //正在学习的情况
                holder.notlearning_top.setVisibility(View.GONE);
                holder.not_layout.setVisibility(View.GONE);
                holder.study_item_title.setText(studyBean.getCourseName());
                holder.bk_name.setText(studyBean.getPlateName());
                holder.bk_title.setText(mContext.getString(R.string.studycenter_title, studyBean.getCourseWareName()));
                holder.study_overday.setText(studyBean.getHasTime());
                holder.study_cnt.setText(studyBean.getStudyJd());
                holder.top_btn.setText("继续学习▷");

                holder.top_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AttachStudyActivity.startAttachStudyActivity(mContext, studyBean.getPuuId(), studyBean.getCwuuId(), 0,studyBean.getCourseType());
                    }
                });
            } else if (studyBean.getStateType() == 0) {
                //未学习的情况

                holder.alreadylearning_top.setVisibility(View.GONE);
                holder.study_layout.setVisibility(View.GONE);

                holder.study_item_title.setText(studyBean.getCourseName());
                holder.cnt_teacher.setText(studyBean.getTeacherName());
                holder.study_overday.setText(studyBean.getHasTime());
                holder.not_cnt.setText(studyBean.getStudyJd());
                holder.cnt_remark.setText(studyBean.getRemark());
                holder.top_btn.setText("查看详情▷");
                holder.top_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlateDetailActivity.startPlateDetailActivity(mContext, studyBean.getPuuId(), studyBean.getCuuId(), studyBean.getCourseName(),studyBean.getCourseType());

                    }
                });
            } else {
                //已完成学习
                holder.pass_img.setVisibility(View.VISIBLE);
                holder.alreadylearning_top.setVisibility(View.GONE);
                holder.study_layout.setVisibility(View.GONE);

                holder.study_item_title.setText(studyBean.getCourseName());
                holder.cnt_teacher.setText(studyBean.getTeacherName());
                holder.study_overday.setText(studyBean.getHasTime());
                holder.not_cnt.setText(studyBean.getStudyJd());
                holder.cnt_remark.setText(studyBean.getRemark());
                if(studyBean.getIsPass() == 0){
                    holder.pass_img.setImageResource(R.mipmap.pass_over);
                    holder.top_btn.setText("课程回顾▷");
                    holder.top_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PlateDetailActivity.startPlateDetailActivity(mContext, studyBean.getPuuId(), studyBean.getCuuId(), studyBean.getCourseName(),studyBean.getCourseType());
                        }
                    });
                }else {
                    holder.pass_img.setImageResource(R.mipmap.pass_adopt);
                    holder.top_btn.setText("查看证书▷");
                    holder.top_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                             mContext.startActivity(new Intent(mContext, MyCertificateActivity.class));
                        }
                    });
                }
            }
        }
    }



    @Override
    public int getItemCount() {

        return mCourseBean.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout alreadylearning_top,notlearning_top,not_layout,over_layout,study_layout;
        CardView item_bg;
        TextView study_item_title,cnt_teacher,cnt_remark,
                study_overday,not_cnt,study_cnt,bk_title,
                bk_name;
        Button top_btn;
        ImageView pass_img,separate_img;

        public ViewHolder(View view) {
            super(view);
            item_bg = (CardView) view.findViewById(R.id.itme_studycenter_bg);
            alreadylearning_top = (LinearLayout) view.findViewById(R.id.alreadylearning_top);
            notlearning_top = (LinearLayout) view.findViewById(R.id.notlearning_top);
            not_layout = (LinearLayout) view.findViewById(R.id.not_layout);
            over_layout = (LinearLayout) view.findViewById(R.id.over_layout);
            study_layout = (LinearLayout) view.findViewById(R.id.study_layout);

            study_item_title = (TextView) view.findViewById(R.id.study_item_title);
            separate_img = (ImageView) view.findViewById(R.id.separate_img);
            pass_img = (ImageView) view.findViewById(R.id.pass_img);
            top_btn = (Button) view.findViewById(R.id.top_btn);
            cnt_teacher = (TextView) view.findViewById(R.id.cnt_teacher);
            cnt_remark = (TextView) view.findViewById(R.id.cnt_remark);
            not_cnt = (TextView) view.findViewById(R.id.not_cnt);
            bk_title = (TextView) view.findViewById(R.id.bk_title);
            bk_name = (TextView) view.findViewById(R.id.bk_name);
            study_overday = (TextView) view.findViewById(R.id.study_overday);
            study_cnt = (TextView) view.findViewById(R.id.study_cnt);
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
