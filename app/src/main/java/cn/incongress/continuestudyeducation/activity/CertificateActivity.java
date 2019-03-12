package cn.incongress.continuestudyeducation.activity;

import android.os.Bundle;
import android.app.Activity;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.base.BaseActivity;

public class CertificateActivity extends BaseActivity {


    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_certificate);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        ((TextView)getViewById(R.id.home_title)).setText("证书详情");
        ((ImageView)getViewById(R.id.home_title_back)).setVisibility(View.VISIBLE);
        TextView mCertificate = getViewById(R.id.certifi_title);
        mCertificate.setText(getIntent().getStringExtra("title"));
        ImageView certificateImg = getViewById(R.id.certifi_img);
        ImageLoader.getInstance().displayImage(getIntent().getStringExtra("url"), certificateImg);
    }

    @Override
    protected void initializeEvents() {

    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {

    }

    @Override
    protected void handleDetailMsg(Message msg) {

    }

    public void back(View v){
        finish();
    }
}
