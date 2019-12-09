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
import android.widget.Toast;

import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;
import com.mchsdk.paysdk.retrofitutils.rxjava.observable.SchedulerTransformer;
import com.mchsdk.paysdk.retrofitutils.rxjava.observer.BaseObserver;
import com.yidao.project.heathproject.Beans.CheckCodeBean;
import com.yidao.project.heathproject.Beans.RegisterBean;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;

public class SetPassWordActivity extends BaseActivity {
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
    @BindView(R.id.et_new)
    EditText mEtNew;
    @BindView(R.id.et_nextnew)
    EditText mEtNextnew;
    @BindView(R.id.iv_next)
    ImageView mIvNext;
    private SetPassWordActivity instance;
    private String mOld;
    private String mPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpassword);
        ButterKnife.bind(this);
        instance = this;
        addActivity(instance);
        initView();
    }

    private void initView() {
        mTvTilte.setVisibility(View.VISIBLE);
        mTvTilte.setText("密码设置");
        mRlBack.setVisibility(View.VISIBLE);
        mOld = getIntent().getStringExtra("mOld");
        mPhone = SharedPreferencesUtility.getUserPhone(instance);
    }





    /**
     * 修改密码
     */
    private void getRegisterData(String newPassword,String oldPassword) {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getupdatePassword(mPhone, newPassword,  oldPassword, application)
                .compose(this.<CheckCodeBean>bindToLifecycle())
                .compose(SchedulerTransformer.<CheckCodeBean>transformer())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                })
                .subscribe(new BaseObserver<CheckCodeBean>() {
                    @Override
                    protected void onSuccess(CheckCodeBean mCheckCodeBean) {
                        if (mCheckCodeBean != null) {
                            if (mCheckCodeBean.getCode() == 200) {
//                                ToastShort(instance,mCheckCodeBean.getMsg());
                                jumpToActivity(SetUpActivity.class);
                                finishActivity(instance);


                            } else {
                                Toast.makeText(instance, mCheckCodeBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    }

                    @Override
                    protected void onFailed(HttpResponseException responseException) {
                        super.onFailed(responseException);
//                        ToastShort.showShortToast("网络错误！");

                        Toast.makeText(instance, "error code : " + responseException.getStatus(), Toast.LENGTH_SHORT).show();
//                        ToastShort(instance, "网络有误！");
                    }
                });

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
            case R.id.iv_next:   //确认修改密码
                String etNewPass = mEtNew.getText().toString().trim();
                String mEtNextnewPass = mEtNextnew.getText().toString().trim();
                if (!etNewPass.equals("") && !mEtNextnewPass.equals("")){
                    if (etNewPass.equals(mEtNextnewPass)){
                        getRegisterData(mEtNextnewPass,mOld);
                    }else {
                        ToastLong(instance,"确认密码是否相同！");
                    }
                }else {
                    ToastLong(instance,"密码不能为空！");
                }

                break;
        }
    }
}
