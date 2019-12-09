package com.yidao.project.heathproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.ApiService;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SetUpActivity extends BaseActivity {
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.tv_tilte)
    TextView mTvTilte;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.rl_right)
    RelativeLayout mRlRight;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.titleBar)
    LinearLayout mTitleBar;
    @BindView(R.id.circle_head)
    CircleImageView mCircleHead;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.iv_back1)
    ImageView mIvBack1;
    @BindView(R.id.rl_username)
    RelativeLayout mRlUsername;
    @BindView(R.id.setup)
    TextView mSetup;
    @BindView(R.id.iv_back2)
    ImageView mIvBack2;
    @BindView(R.id.rl_setpass)
    RelativeLayout mRlSetpass;
    private SetUpActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        ButterKnife.bind(this);
        instance = this;
        addActivity(instance);
        initView();
    }


    private void initView() {
        mTvTilte.setVisibility(View.VISIBLE);
        mTvTilte.setText("设置");
        mRlBack.setVisibility(View.VISIBLE);
        String headiamge = SharedPreferencesUtility.getAvatar(instance);
        String userName = SharedPreferencesUtility.getUsername(instance);
        mUsername.setText(userName);

        Glide.with(instance)
                .load(ApiService.SHARE_BASEURL+headiamge)
//                .placeholder(R.mipmap.img_avatar_3)
                .centerCrop()   //圆角
                .into(mCircleHead);
    }


    @OnClick({R.id.iv_left, R.id.tv_left, R.id.rl_back, R.id.tv_tilte,R.id.rl_username,R.id.rl_setpass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                break;
            case R.id.tv_left:
                break;
            case R.id.rl_back:
                finishActivity(instance);
                break;
            case R.id.tv_tilte:
                break;
            case R.id.rl_username:   //修改昵称
                Intent NiceNameIntent = new Intent(instance,NiceNameActivity.class);
                NiceNameIntent.putExtra("mUsername",mUsername.getText().toString().trim());
                startActivity(NiceNameIntent);
                break;
            case R.id.rl_setpass:   //设置密码
                Intent PassWordCheckIntent = new Intent(instance,PassWordCheckActivity.class);
                startActivity(PassWordCheckIntent);
                break;
        }
    }
}
