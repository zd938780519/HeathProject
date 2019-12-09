package com.yidao.project.heathproject.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.yidao.project.heathproject.Dialogs.BeginTestDialog;
import com.yidao.project.heathproject.Dialogs.SexDialog;
import com.yidao.project.heathproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PassWordActivity extends BaseActivity {
    @BindView(R.id.rl_backIamge)
    RelativeLayout mRlBackIamge;
    @BindView(R.id.pass_name)
    TextView mPassName;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.next_password)
    EditText mNextPassword;
    @BindView(R.id.ll_nextpass)
    LinearLayout mLlNextpass;
    private PassWordActivity instance;
    private String mPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);
        instance = this;
        addActivity(instance);
        initView();
    }


    private void initView() {
        mPhone = getIntent().getStringExtra("mPhone");
    }

    @OnClick({R.id.rl_backIamge, R.id.pass_name, R.id.et_password,R.id.ll_nextpass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_backIamge:
                finishActivity(instance);
                break;
            case R.id.pass_name:
                break;
            case R.id.et_password:

                break;
            case R.id.ll_nextpass:
                String mPassword = mEtPassword.getText().toString().trim();
                String mNPassword = mNextPassword.getText().toString().trim();
                if (!mPassword.equals("") && !mNPassword.equals("")){
                    if (mPassword.equals(mNPassword)){
                        FragmentManager cardfacefm = getSupportFragmentManager();
                        SexDialog mSexDialog = new SexDialog();
                        Bundle bundle = new Bundle();
                        bundle.putString("mPhone", mPhone);
                        bundle.putString("password", mNPassword);   //探索id
                        mSexDialog.setArguments(bundle);
                        mSexDialog.show(cardfacefm, "mBeginTestDialog");
                    }else {
                        ToastLong(instance,"请确保两次密码相同！");
                    }

                }

                break;
        }
    }
}
