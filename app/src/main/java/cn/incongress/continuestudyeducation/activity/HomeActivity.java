package cn.incongress.continuestudyeducation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.fragment.HomeFragment;
import cn.incongress.continuestudyeducation.fragment.MeFragment;
import cn.incongress.continuestudyeducation.fragment.NotificationFragment;
import cn.incongress.continuestudyeducation.utils.LogUtils;

/**
 * Created by Jacky on 2015/12/17.
 */
public class HomeActivity extends BaseActivity {
    private static final String TAG = "HomeActivity";

    private RadioGroup mRgBottomBar;
    private RadioButton mRbHome, mRbNotification, mRbMe;
    private Fragment mCurrentFragment;
    private static HomeFragment mHomeFragment;
    private static NotificationFragment mNotificationFragment;
    private static MeFragment mMeFragment;
    private FragmentManager mFragmentManager;
    private TextView mTvToolbarTitle;
    private Toolbar mToolbar;
    private ImageView mIvIntro;
    private boolean isMenuShow = true;

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mRgBottomBar = getViewById(R.id.rg_home_bottom);
        mRbHome = getViewById(R.id.rb_home);
        mRbNotification = getViewById(R.id.rb_me);
        mRbMe = getViewById(R.id.rb_notification);
        mTvToolbarTitle = getViewById(R.id.tv_title);
        mIvIntro = getViewById(R.id.iv_use_intro);
        mToolbar = getViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void initializeEvents() {
        mRgBottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        isMenuShow = true;

                        if (mHomeFragment == null)
                            mHomeFragment = HomeFragment.getInstance();
                        mTvToolbarTitle.setText(R.string.home_title);
                        switchContent(mCurrentFragment, mHomeFragment);
                        break;
                    case R.id.rb_notification:
                        isMenuShow = false;

                        mTvToolbarTitle.setText(R.string.home_bottom_notification);
                        if (mNotificationFragment == null)
                            mNotificationFragment = NotificationFragment.getInstance();
                        switchContent(mCurrentFragment, mNotificationFragment);
                        break;
                    case R.id.rb_me:
                        isMenuShow = false;


                        mTvToolbarTitle.setText(R.string.person_info);
                        if (mMeFragment == null) {
                            mMeFragment = MeFragment.getInstance();
                        }
                        switchContent(mCurrentFragment, mMeFragment);

                        break;
                }
                invalidateOptionsMenu();
            }
        });

        mIvIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, UseIntroActivity.class));
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!isMenuShow) {
            menu.findItem(R.id.lession_introduction).setVisible(false);
            mIvIntro.setVisibility(View.GONE);
        }else {
            menu.findItem(R.id.lession_introduction).setVisible(true);
            mIvIntro.setVisibility(View.VISIBLE);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        mMeFragment = MeFragment.getInstance();
        mHomeFragment = HomeFragment.getInstance();
        mNotificationFragment = NotificationFragment.getInstance();

        mCurrentFragment = mHomeFragment;
        mFragmentManager.beginTransaction().add(R.id.fl_container, mCurrentFragment).commit();
        mRbHome.setChecked(true);
    }

    @Override
    protected void handleDetailMsg(Message msg) {
    }

    public void switchContent(Fragment from, Fragment to) {
        if (mCurrentFragment != to) {
            mCurrentFragment = to;
            FragmentTransaction transaction = mFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (!to.isAdded()) {
                transaction.hide(from).add(R.id.fl_container, to).commit();
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lession_introduction:
                startActivity(new Intent(mContext, LessonIntroActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
