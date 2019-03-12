package cn.incongress.continuestudyeducation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.bean.MyCertificateBean;
import cn.incongress.continuestudyeducation.bean.PlateArrayBean;

/**
 * Created by Admin on 2017/6/8.
 */

public class MyCertificateAdapter extends RecyclerView.Adapter<MyCertificateAdapter.ViewHolder> {


    private List<MyCertificateBean.CertificateArrayBean> mCourseBeanList = new ArrayList<MyCertificateBean.CertificateArrayBean>();
    MyCertificateAdapter.OnItemClickListener mItemClickListener;
    private Context mContext;

    public MyCertificateAdapter(List<MyCertificateBean.CertificateArrayBean> beans,Context context) {
        this.mCourseBeanList = beans;
        this.mContext = context;
    }

    @Override
    public MyCertificateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_certificate, parent, false);
        return new MyCertificateAdapter.ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(MyCertificateAdapter.ViewHolder holder , int position) {
        MyCertificateBean.CertificateArrayBean bean = mCourseBeanList.get(position);
        holder.title.setText(bean.getCourseName());
        holder.teacher.setText(mContext.getResources().getString(R.string.next_teacher_name,bean.getTeacherName()));
        holder.remark.setText("简介："+bean.getCourseRemark());
        //证书审核状态，1待审核，2审核通过，3不通过
        if(bean.getZsState() == 1){
            holder.state.setText("证书正在审核中...");
        }else if(bean.getZsState() == 2){
            holder.state.setText("证书审核通过，点击查看");
            holder.state.setTextColor(mContext.getResources().getColor(R.color.button_background1));
        }else if(bean.getZsState() == 3){
            holder.state.setText("证书审核未通过!");
            holder.state.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return mCourseBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title,teacher,remark,state;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.certifi_title);
            teacher = (TextView) view.findViewById(R.id.certifi_teacher);
            remark = (TextView) view.findViewById(R.id.certifi_remark);
            state = (TextView) view.findViewById(R.id.certifi_state);
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

    public void SetOnItemClickListener(final MyCertificateAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
