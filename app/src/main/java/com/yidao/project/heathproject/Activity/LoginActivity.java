package com.yidao.project.heathproject.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;
import com.mchsdk.paysdk.retrofitutils.rxjava.observable.SchedulerTransformer;
import com.mchsdk.paysdk.retrofitutils.rxjava.observer.BaseObserver;
import com.yidao.project.heathproject.Beans.LoginBean;
import com.yidao.project.heathproject.MainActivity;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;

import static com.yidao.project.heathproject.Utils.ValidationUntils.checkRegister;

public class LoginActivity extends BaseActivity {
//    @BindView(R.id.rl_backIamge)
//    RelativeLayout mRlBackIamge;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
//    @BindView(R.id.tv_backpass)
//    TextView mTvBackpass;

    @BindView(R.id.ll_login)
    LinearLayout mLlLogin;
    @BindView(R.id.tv_registered)
    TextView mTvRegistered;
    private LoginActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        instance = this;
        addActivity(instance);
        initView();
    }


    private void initView() {

    }


    @OnClick({ R.id.tv_login, R.id.et_phone, R.id.et_password,R.id.tv_registered,R.id.ll_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.rl_backIamge:
////                finishActivity(instance);
//                break;
            case R.id.tv_login:
                break;
            case R.id.et_phone:
                break;
            case R.id.et_password:
                break;
//            case R.id.lr_backpass:   //找回密码
//                jumpToActivity(RetrievePassWordActivity.class);
//                break;
            case R.id.tv_registered:   //注册账号
                jumpToActivity(RegisterActivity.class);
                break;
            case R.id.ll_login:   //登录
                String mPhone = mEtPhone.getText().toString().trim();
                String mPassword = mEtPassword.getText().toString().trim();
                if (checkRegister(instance, mPhone)) {
                    if (!mPhone.equals("") && !mPassword.equals("")) {
//                        getVerificatioCode(mTPhone,mCode);
                        QuickModifyLogin(mPhone,mPassword);
//                        jumpToActivity();
                    }else {
                        ToastLong(instance,"手机号和密码不能为空！");
                    }
                }

                break;
        }
    }



    /**
     * 登录
     */
    private void QuickModifyLogin(String mPhone,String mPassWord){
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getLoginInterface(mPhone,mPassWord,application)
                .compose(this.<LoginBean>bindToLifecycle())
                .compose(SchedulerTransformer.<LoginBean>transformer())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
//                        Log.i(TAG, "--- doFinally ---");
//                        if (isRefresh)
//                            mRefreshLayout.finishRefreshing();
//                        else
//                            mRefreshLayout.finishLoadmore();
                    }
                })
                .subscribe(new BaseObserver<LoginBean>() {
                    @Override
                    protected void onSuccess(LoginBean mLoginBean) {
                        if (mLoginBean != null) {
                            if (mLoginBean.getCode() ==200){
//                                ToastShort(instance,mLoginBean.getMsg());
                                SharedPreferencesUtility.setUserId(instance,mLoginBean.getData().getId());
                                SharedPreferencesUtility.setUsername(instance,mLoginBean.getData().getNickname());
                                SharedPreferencesUtility.setAge(instance,mLoginBean.getData().getAge());
                                SharedPreferencesUtility.setSex(instance, String.valueOf(mLoginBean.getData().getSex()));
                                SharedPreferencesUtility.setAvatar(instance,mLoginBean.getData().getAvatar());
                                SharedPreferencesUtility.setUserPhone(instance,mLoginBean.getData().getPhone());
                                jumpToActivity(MainActivity.class);
//                                finishAllActivity();

//                                SharedPreferencesUtility.setUserId(instance,"");
                            }else {
                                ToastShort(instance,mLoginBean.getMsg());
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




    private long timeMillis;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - timeMillis) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            timeMillis = System.currentTimeMillis();
        } else {
            BaseActivity.finishAllActivity();
            System.exit(0);
        }

    }


}
