package cn.incongress.continuestudyeducation.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cn.incongress.continuestudyeducation.R;
import cn.incongress.continuestudyeducation.base.BaseActivity;
import cn.incongress.continuestudyeducation.bean.Constant;
import cn.incongress.continuestudyeducation.fragment.HomeFragment;
import cn.incongress.continuestudyeducation.fragment.MeFragment;
import cn.incongress.continuestudyeducation.fragment.NotificationFragment;
import cn.incongress.continuestudyeducation.fragment.StudyCenterFragment;
import cn.incongress.continuestudyeducation.service.CMEHttpClientUsage;
import cn.incongress.continuestudyeducation.utils.LogUtils;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Jacky on 2015/12/17.
 */
public class HomeActivity extends BaseActivity {
    private static final String TAG = "HomeActivity";

    private RadioGroup mRgBottomBar;
    private RadioButton mRbMyStudys;
    private Fragment mCurrentFragment;

    private static NotificationFragment mNotificationFragment;
    private static MeFragment mMeFragment;
    private static StudyCenterFragment mStudyCenterFragment;

    private FragmentManager mFragmentManager;
    private TextView mTvToolbarTitle;

    private LinearLayout mIvIntro;
    private boolean isMenuShow = true;

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mRgBottomBar = getViewById(R.id.rg_home_bottom);
        mRbMyStudys = getViewById(R.id.rb_studycenter);

        mTvToolbarTitle = getViewById(R.id.home_title);
        mIvIntro = getViewById(R.id.home_use_intro);

        mFragmentManager = getSupportFragmentManager();
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                String action = intent.getAction();
                //亮屏
                 if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    String uuid = getSharedPreferences(Constant.DEFAULT_SP_NAME,0 ).getString(Constant.SP_USER_UUID,"");
                    if(uuid.length()>0){
                        CMEHttpClientUsage.getInstanse().doLoginOut(uuid,new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                Log.d("sgqTest", "onSuccess: 退出了");
                            }
                        });
                    }
                     Constant.ISCUT = true;
                }
            }
        };
        registerReceiver(mBatInfoReceiver, filter);
    }

    @Override
    protected void initializeEvents() {
        mRgBottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
//                    case R.id.rb_home:
//                        isMenuShow = true;
//                        mTvToolbarTitle.setText(R.string.home_bottom_title);

//                        if (mHomeFragment == null)
//                            mHomeFragment = HomeFragment.getInstance();
//                            mTvToolbarTitle.setText(R.string.home_title);
//                            switchContent(mCurrentFragment, mHomeFragment);
//                        break;
                    case R.id.rb_studycenter:
                        mIvIntro.setVisibility(View.VISIBLE);
                       mTvToolbarTitle.setText(R.string.home_bottom_studycenter);

                      if(mStudyCenterFragment == null)
                           mStudyCenterFragment = StudyCenterFragment.getInstance();
                      switchContent(mCurrentFragment, mStudyCenterFragment);

                                                /*isMenuShow = true;
                        mTvToolbarTitle.setText(R.string.home_bottom_title);

                        if (mHomeFragment == null)
                            mHomeFragment = HomeFragment.getInstance();
                            mTvToolbarTitle.setText(R.string.home_title);
                            switchContent(mCurrentFragment, mHomeFragment);
*/
                        break;
                    case R.id.rb_notification:
                        mIvIntro.setVisibility(View.GONE);
                        mTvToolbarTitle.setText(R.string.home_bottom_notification);
                        if (mNotificationFragment == null)
                            mNotificationFragment = NotificationFragment.getInstance();
                        switchContent(mCurrentFragment, mNotificationFragment);
                        break;
                    case R.id.rb_me:
                        mIvIntro.setVisibility(View.GONE);
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
    protected void initializeData(Bundle savedInstanceState) {
        mMeFragment = MeFragment.getInstance();
//        mHomeFragment = HomeFragment.getInstance();
        mNotificationFragment = NotificationFragment.getInstance();
        mStudyCenterFragment = StudyCenterFragment.getInstance();

        mCurrentFragment = mStudyCenterFragment;
        mFragmentManager.beginTransaction().add(R.id.fl_container, mCurrentFragment).commit();
        mIvIntro.setVisibility(View.VISIBLE);
        mRbMyStudys.setChecked(true);
        mTvToolbarTitle.setText(R.string.home_bottom_studycenter);
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
    /*@Override
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
    }*/
}
