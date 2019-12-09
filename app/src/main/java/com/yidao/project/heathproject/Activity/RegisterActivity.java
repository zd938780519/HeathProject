package com.yidao.project.heathproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;
import com.mchsdk.paysdk.retrofitutils.rxjava.observable.SchedulerTransformer;
import com.mchsdk.paysdk.retrofitutils.rxjava.observer.BaseObserver;
import com.yidao.project.heathproject.Beans.CheckCodeBean;
import com.yidao.project.heathproject.Beans.CodeBean;
import com.yidao.project.heathproject.Beans.LoginBean;
import com.yidao.project.heathproject.MainActivity;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;
import com.yidao.project.heathproject.Utils.TimeCountUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;

import static com.yidao.project.heathproject.Utils.ValidationUntils.checkRegister;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.rl_backIamge)
    RelativeLayout mRlBackIamge;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_bianbao)
    TextView mTvBianbao;
    @BindView(R.id.ll_bianhao)
    LinearLayout mLlBianhao;
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
    @BindView(R.id.ll_login)
    LinearLayout mLlLogin;
    @BindView(R.id.tv_registered)
    TextView mTvRegistered;
    private  RegisterActivity instance;
    private String mCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        instance = this;
        addActivity(instance);
        initView();
    }


    private void initView() {

    }



    /**
     * 获取验证码
     */
    private void getCode(String mPhone){
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getVerificationCode(mPhone,application)
                .compose(this.<CodeBean>bindToLifecycle())
                .compose(SchedulerTransformer.<CodeBean>transformer())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                })
                .subscribe(new BaseObserver<CodeBean>() {
                    @Override
                    protected void onSuccess(CodeBean mCodeBean) {
                        if (mCodeBean != null) {
                            if (mCodeBean.getCode() ==200){
//                                ToastShort(instance,mCodeBean.getMsg());
                                mCode = mCodeBean.getData().getCode();
//                                finishAllActivity();

//                                SharedPreferencesUtility.setUserId(instance,"");
                            }else {
                                ToastShort(instance,mCodeBean.getMsg());
                            }

                        }
                    }

                    @Override
                    protected void onFailed(HttpResponseException responseException) {
                        super.onFailed(responseException);
//                        ToastShort.showShortToast("网络错误！");
                        ToastShort(instance, "error code : " + responseException.getStatus());
//                        ToastShort(instance, "网络有误！");
                    }
                });
    }



    /**
     * 验证验证码
     */
    private void getChecCodeData(String mPhone,String mCode){
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getCheckCode(mPhone,mCode,application)
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
                            if (mCheckCodeBean.getCode() ==200){
//                                ToastShort(instance,mCheckCodeBean.getMsg());
                                Intent PassWordIntent = new Intent(instance,PassWordActivity.class);
                                PassWordIntent.putExtra("mPhone",mPhone);
                                startActivity(PassWordIntent);

                            }else {
                                ToastShort(instance,mCheckCodeBean.getMsg());
                            }

                        }
                    }

                    @Override
                    protected void onFailed(HttpResponseException responseException) {
                        super.onFailed(responseException);
//                        ToastShort.showShortToast("网络错误！");
                        ToastShort(instance, "error code : " + responseException.getStatus());
//                        ToastShort(instance, "网络有误！");
                    }
                });
    }

    @OnClick({R.id.rl_backIamge, R.id.tv_login, R.id.tv_bianbao,R.id.ll_login,R.id.tv_registered,R.id.mCode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_backIamge:
                finishActivity(instance);
                break;
            case R.id.tv_login:  //验证验证码成功，跳转

                break;
            case R.id.tv_bianbao:
                break;
            case R.id.mCode:   //获取验证码
                String mPhone = mEtPhone.getText().toString().trim();
                if (checkRegister(instance, mPhone)) {
                    if (!mPhone.equals("")) {
//                        Log.e("TAG", "mPhone===" + mPhone);
                        getCode(mPhone);
                        TimeCountUtil timeCount = new TimeCountUtil(instance, 60000, 1000, mMCode);
                        timeCount.start();
                    }
                }
                break;
            case R.id.ll_login:   //下一步输入密码

                String Phone = mEtPhone.getText().toString().trim();
                String mECode = mEtCode.getText().toString().trim();
                if (checkRegister(instance, Phone)) {
                    if (!Phone.equals("") && !mECode.equals("")) {
//                        Log.i(TAG, "mPhone===" + mPhone);
                        getChecCodeData(Phone,mECode);
                    }else {
                        ToastLong(instance,"验证码或手机号不能为空！");
                    }
                }

                break;
            case R.id.tv_registered:
                finishActivity(instance);
                break;
        }
    }
}
