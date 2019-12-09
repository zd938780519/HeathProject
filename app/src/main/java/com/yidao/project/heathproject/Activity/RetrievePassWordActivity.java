package com.yidao.project.heathproject.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.yidao.project.heathproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RetrievePassWordActivity extends BaseActivity {

    @BindView(R.id.rl_backIamge)
    RelativeLayout mRlBackIamge;
    @BindView(R.id.pass_name)
    TextView mPassName;
    @BindView(R.id.tv_bianbao)
    TextView mTvBianbao;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.mCode)
    TextView mMCode;
    @BindView(R.id.ll_phone)
    RelativeLayout mLlPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.next_password)
    EditText mNextPassword;
    @BindView(R.id.ll_nextpass)
    LinearLayout mLlNextpass;
    private RetrievePassWordActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrievepass);
        ButterKnife.bind(this);
        instance = this;
        addActivity(instance);
        initVew();
    }


    private void initVew() {

    }


    @OnClick({R.id.rl_backIamge, R.id.pass_name, R.id.tv_bianbao, R.id.line})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_backIamge:
                finishActivity(instance);
                break;
            case R.id.pass_name:
                break;
            case R.id.tv_bianbao:
                break;
            case R.id.line:
                break;
        }
    }
}
