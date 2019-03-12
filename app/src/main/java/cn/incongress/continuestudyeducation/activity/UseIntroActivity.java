package cn.incongress.continuestudyeducation.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.base.BaseActivity;

/**
 * Created by Jacky on 2015/12/18.
 */
public class UseIntroActivity extends BaseActivity {

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_use_intro);
        ((TextView)getViewById(R.id.home_title)).setText(R.string.use_intro);
        ((ImageView)getViewById(R.id.home_title_back)).setVisibility(View.VISIBLE);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
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
    public void back(View view){
        this.finish();
    }
}
