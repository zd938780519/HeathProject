package com.yidao.project.heathproject.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.yidao.project.heathproject.Adapters.CommonTabPagerAdapter;
import com.yidao.project.heathproject.Fragemnts.AnswerFragment;
import com.yidao.project.heathproject.Fragemnts.TestFragment;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.Utils.TabLayoutUtil;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordActivity extends BaseActivity implements CommonTabPagerAdapter.TabPagerListener {
    @BindView(R.id.tab_watch)
    TabLayout mTabWatch;
    @BindView(R.id.vp_watch)
    ViewPager mVpWatch;
    @BindView(R.id.ll_back)
    LinearLayout mLlBack;
    private CommonTabPagerAdapter adapter;
    private RecordActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        instance = this;
        addActivity(instance);
        ButterKnife.bind(this);

        initView();
    }


    private void initView() {
        adapter = new CommonTabPagerAdapter(getSupportFragmentManager()
                , 2, Arrays.asList("测试记录", "问答记录"), instance);
        adapter.setListener(this);
        mVpWatch.setAdapter(adapter);
        mTabWatch.setupWithViewPager(mVpWatch);
        mTabWatch.setTabMode(TabLayout.MODE_FIXED);
        TabLayoutUtil.reflex(mTabWatch);   //设置下划线长度
    }

    @Override
    public Fragment getFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
//                Bundle bundle=new Bundle();
//                bundle.putString("TestFragment",circle_name);
                fragment = new TestFragment().newInstance(position);//测试记录
//                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new AnswerFragment().newInstance(position);//问答记录
                break;

        }
        return fragment;
    }

    @OnClick({R.id.ll_back, R.id.tab_watch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finishActivity(instance);
                break;
            case R.id.tab_watch:
                break;
        }
    }
}
