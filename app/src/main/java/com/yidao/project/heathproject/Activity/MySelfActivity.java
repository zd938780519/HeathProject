package com.yidao.project.heathproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.yidao.project.heathproject.Adapters.CommonTabPagerAdapter;
import com.yidao.project.heathproject.Fragemnts.AnswerFragment;
import com.yidao.project.heathproject.Fragemnts.TestFragment;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.ApiService;
import com.yidao.project.heathproject.Utils.EyesUtils;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;
import com.yidao.project.heathproject.Utils.TabLayoutUtil;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MySelfActivity extends BaseActivity implements CommonTabPagerAdapter.TabPagerListener {
    @BindView(R.id.rl_backIamge)
    RelativeLayout mRlBackIamge;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.iv_modify)
    ImageView mIvModify;
    @BindView(R.id.tab_watch)
    TabLayout mTabWatch;
    @BindView(R.id.vp_watch)
    ViewPager mVpWatch;
    @BindView(R.id.rl_ceshi)
    RelativeLayout mRlCeshi;
    @BindView(R.id.iv_exit)
    ImageView ivExit;
    private CommonTabPagerAdapter adapter;
    private MySelfActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myself);
        ButterKnife.bind(this);
        instance = this;
        EyesUtils.setImmersionStateMode(instance);  //实现沉浸
        addActivity(instance);
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
        String headiamge = SharedPreferencesUtility.getAvatar(instance);
        String userName = SharedPreferencesUtility.getUsername(instance);
        String sex = SharedPreferencesUtility.getSex(instance);
        int age = SharedPreferencesUtility.getAge(instance);
        if (sex.equals("0")){
            mTvSex.setText("男");
        }else {
            mTvSex.setText("女");
        }
        mTvAge.setText(age+"岁");
        mTvName.setText(userName);

        Glide.with(instance)
                .load(ApiService.SHARE_BASEURL+headiamge)
//                .placeholder(R.mipmap.img_avatar_3)
                .centerCrop()   //圆角
                .into(mIvHead);
    }


    @OnClick({R.id.rl_backIamge, R.id.iv_head, R.id.tv_name,R.id.rl_ceshi,R.id.iv_modify,R.id.iv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_backIamge:
                finishActivity(instance);
                break;
            case R.id.iv_head:
                break;
            case R.id.tv_name:
                break;
            case R.id.rl_ceshi:
                Intent RecordIntent = new Intent(instance,RecordActivity.class);
                startActivity(RecordIntent);
                break;
            case R.id.iv_modify:    //设置
                Intent setUpIntent = new Intent(instance,SetUpActivity.class);
                startActivity(setUpIntent);
                break;
            case R.id.iv_exit:  //  退出登录
                SharedPreferencesUtility.clearAge(instance);
                SharedPreferencesUtility.clearUserId(instance);
                SharedPreferencesUtility.clearUsername(instance);
                SharedPreferencesUtility.clearUserPhone(instance);
                SharedPreferencesUtility.clearSex(instance);
                SharedPreferencesUtility.clearAvatar(instance);
                jumpToActivity(LoginActivity.class);
                break;
        }
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
}
