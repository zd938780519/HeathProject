package com.yidao.project.heathproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.yidao.project.heathproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PassWordCheckActivity extends BaseActivity {
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
    @BindView(R.id.et_old)
    EditText mEtOld;
    @BindView(R.id.iv_next)
    ImageView mIvNext;
    private PassWordCheckActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordcheck);
        ButterKnife.bind(this);
        instance = this;
        addActivity(instance);
        initView();
    }

    private void initView() {
        mTvTilte.setVisibility(View.VISIBLE);
        mTvTilte.setText("密码验证");
        mRlBack.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.iv_left, R.id.tv_left, R.id.rl_back,R.id.iv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                break;
            case R.id.tv_left:
                break;
            case R.id.rl_back:
                finishActivity(instance);
                break;
            case R.id.iv_next:   //下一步设置密码
                String mOld = mEtOld.getText().toString().trim();
                if (!mOld.equals("")){
                    Intent SetPassWordIntent  = new Intent(instance, SetPassWordActivity.class);
                    SetPassWordIntent.putExtra("mOld",mOld);
                    startActivity(SetPassWordIntent);
                }

                break;
        }
    }
}
