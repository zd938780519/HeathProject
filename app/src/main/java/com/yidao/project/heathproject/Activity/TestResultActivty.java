package com.yidao.project.heathproject.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;
import com.mchsdk.paysdk.retrofitutils.rxjava.observable.SchedulerTransformer;
import com.mchsdk.paysdk.retrofitutils.rxjava.observer.BaseObserver;
import com.yidao.project.heathproject.Beans.FTJBean;
import com.yidao.project.heathproject.Beans.Health;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;

public class TestResultActivty extends BaseActivity {
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
    @BindView(R.id.tv_height)
    TextView mTvHeight;
    @BindView(R.id.tv_weight)
    TextView mTvWeight;
        @BindView(R.id.tv_bmi)
    TextView mTvBmi;
    @BindView(R.id.tv_index)
    TextView mTvIndex;
    @BindView(R.id.iv_measure)
    ImageView mIvMeasure;
    @BindView(R.id.height)
    TextView mHeight;
    @BindView(R.id.ll_height)
    LinearLayout mLlHeight;
    @BindView(R.id.weight)
    TextView mTWeight;
    @BindView(R.id.ll_weight)
    LinearLayout mLlWeight;

    private TestResultActivty instance;
    private String mHight = "";
    private String mWeight = "";
    private String mNumber = "";
    private String data = "";
    private int userId;
    private String tv_name;
    private String xfRunMeter = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testresult);
        ButterKnife.bind(this);
        instance = this;
        addActivity(instance);
        initView();
    }


    private void initView() {
        userId = SharedPreferencesUtility.getUserId(instance);
        mHight = getIntent().getStringExtra("mHight");
        mWeight = getIntent().getStringExtra("mWeight");
        mNumber = getIntent().getStringExtra("mNumber");
        tv_name = getIntent().getStringExtra("tv_name");

        mTvTilte.setVisibility(View.VISIBLE);
        mTvTilte.setText("测试结果");
        mRlBack.setVisibility(View.VISIBLE);
        if (mHight!=null && mWeight!=null) {
            mLlHeight.setVisibility(View.VISIBLE);
            mLlWeight.setVisibility(View.VISIBLE);
            mTvHeight.setText(mHight);
            mTvWeight.setText(mWeight);
            getByTitleData(mHight, mWeight);
        } else if (tv_name.equals("台阶测试")) {

            getByTitleData(mNumber);
        }else if (tv_name.equals("仰卧起坐测试")){
            setNlSitUpData(mNumber);
        }else if (tv_name.equals("俯卧撑测试")){
            setNlPushUpData(mNumber);
        }else if (tv_name.equals("柔韧测试")){
            setRrTestData(mNumber);
        }else if (tv_name.equals("肌肉能力")){
            setJiRouData(mNumber);
        }else if (tv_name.equals("平衡测试")){
            getPhTestData(mNumber);
        }else if (tv_name.equals("跑步测试")){
            data = getIntent().getStringExtra("data");
            xfRunMeter = getIntent().getStringExtra("xfRunMeter");
            mTvBmi.setVisibility(View.VISIBLE);
           String  All=xfRunMeter.substring(0,4);
            mTvBmi.setText("总距离："+All+"m");
            mTvIndex.setText(data);
        }

    }


    /**
     * 形态测试
     */
    private void getByTitleData(String mHight, String mWeight) {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getStTest(userId, mHight, mWeight, application)
                .compose(this.<Health>bindToLifecycle())
                .compose(SchedulerTransformer.<Health>transformer())
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
                .subscribe(new BaseObserver<Health>() {
                    @Override
                    protected void onSuccess(Health mHealth) {
                        if (mHealth != null) {
                            if (mHealth.getCode() == 200) {
                                mTvIndex.setText(mHealth.getData());

                            } else {

                                Toast.makeText(instance, mHealth.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    protected void onFailed(HttpResponseException responseException) {
                        super.onFailed(responseException);
                        Toast.makeText(instance, "error code : " + responseException.getStatus(), Toast.LENGTH_SHORT).show();
//                        ToastShort(instance, "error code : " + responseException.getStatus());
//                        ToastShort(instance, "网络有误！");
                    }
                });
    }


    /**
     * 耐力测试：仰卧起坐（女）
     */
    private void setNlSitUpData(String mNumber) {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getNlSitUp(userId,mNumber, application)
                .compose(this.<FTJBean>bindToLifecycle())
                .compose(SchedulerTransformer.<FTJBean>transformer())
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
                .subscribe(new BaseObserver<FTJBean>() {
                    @Override
                    protected void onSuccess(FTJBean mFTJBean) {
                        if (mFTJBean != null) {
                            if (mFTJBean.getCode() == 200) {
                                mTvIndex.setText(mFTJBean.getData());

                            } else {

                                Toast.makeText(instance, mFTJBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    protected void onFailed(HttpResponseException responseException) {
                        super.onFailed(responseException);
                        Toast.makeText(instance, "error code : " + responseException.getStatus(), Toast.LENGTH_SHORT).show();
//                        ToastShort(instance, "error code : " + responseException.getStatus());
//                        ToastShort(instance, "网络有误！");
                    }
                });
    }



    /**
     * 耐力测试：俯卧撑（男）
     */
    private void setNlPushUpData(String mNumber) {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getNlPushUp(userId,mNumber, application)
                .compose(this.<FTJBean>bindToLifecycle())
                .compose(SchedulerTransformer.<FTJBean>transformer())
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
                .subscribe(new BaseObserver<FTJBean>() {
                    @Override
                    protected void onSuccess(FTJBean mFTJBean) {
                        if (mFTJBean != null) {
                            if (mFTJBean.getCode() == 200) {
                                mTvIndex.setText(mFTJBean.getData());

                            } else {

                                Toast.makeText(instance, mFTJBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    protected void onFailed(HttpResponseException responseException) {
                        super.onFailed(responseException);
                        Toast.makeText(instance, "error code : " + responseException.getStatus(), Toast.LENGTH_SHORT).show();
//                        ToastShort(instance, "error code : " + responseException.getStatus());
//                        ToastShort(instance, "网络有误！");
                    }
                });
    }



    /**
     * 耐力测试：俯卧撑（男）
     */
    private void setRrTestData(String mNumber) {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getRrTest(userId,mNumber, application)
                .compose(this.<FTJBean>bindToLifecycle())
                .compose(SchedulerTransformer.<FTJBean>transformer())
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
                .subscribe(new BaseObserver<FTJBean>() {
                    @Override
                    protected void onSuccess(FTJBean mFTJBean) {
                        if (mFTJBean != null) {
                            if (mFTJBean.getCode() == 200) {
                                mTvIndex.setText(mFTJBean.getData());

                            } else {

                                Toast.makeText(instance, mFTJBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    protected void onFailed(HttpResponseException responseException) {
                        super.onFailed(responseException);
                        Toast.makeText(instance, "error code : " + responseException.getStatus(), Toast.LENGTH_SHORT).show();
//                        ToastShort(instance, "error code : " + responseException.getStatus());
//                        ToastShort(instance, "网络有误！");
                    }
                });
    }




    /**
     * 肌肉能力测试
     */
    private void setJiRouData(String mNumber) {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getJIRouTest(userId,mNumber, application)
                .compose(this.<FTJBean>bindToLifecycle())
                .compose(SchedulerTransformer.<FTJBean>transformer())
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
                .subscribe(new BaseObserver<FTJBean>() {
                    @Override
                    protected void onSuccess(FTJBean mFTJBean) {
                        if (mFTJBean != null) {
                            if (mFTJBean.getCode() == 200) {
                                mTvIndex.setText(mFTJBean.getData());

                            } else {

                                Toast.makeText(instance, mFTJBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    protected void onFailed(HttpResponseException responseException) {
                        super.onFailed(responseException);
                        Toast.makeText(instance, "error code : " + responseException.getStatus(), Toast.LENGTH_SHORT).show();
//                        ToastShort(instance, "error code : " + responseException.getStatus());
//                        ToastShort(instance, "网络有误！");
                    }
                });
    }



    /**
     * 心肺机能测试
     */
    private void getByTitleData(String mNumber) {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getXFTJ(userId, mNumber, application)
                .compose(this.<FTJBean>bindToLifecycle())
                .compose(SchedulerTransformer.<FTJBean>transformer())
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
                .subscribe(new BaseObserver<FTJBean>() {
                    @Override
                    protected void onSuccess(FTJBean mFTJBean) {
                        if (mFTJBean != null) {
                            if (mFTJBean.getCode() == 200) {
                                mTvIndex.setText(mFTJBean.getData());

                            } else {

                                Toast.makeText(instance, mFTJBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    protected void onFailed(HttpResponseException responseException) {
                        super.onFailed(responseException);
                        Toast.makeText(instance, "error code : " + responseException.getStatus(), Toast.LENGTH_SHORT).show();
//                        ToastShort(instance, "error code : " + responseException.getStatus());
//                        ToastShort(instance, "网络有误！");
                    }
                });
    }




    /**
     * 平衡测试
     */
    private void getPhTestData(String mNumber) {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getPhTest(userId, mNumber, application)
                .compose(this.<FTJBean>bindToLifecycle())
                .compose(SchedulerTransformer.<FTJBean>transformer())
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
                .subscribe(new BaseObserver<FTJBean>() {
                    @Override
                    protected void onSuccess(FTJBean mFTJBean) {
                        if (mFTJBean != null) {
                            if (mFTJBean.getCode() == 200) {
                                mTvIndex.setText(mFTJBean.getData());

                            } else {

                                Toast.makeText(instance, mFTJBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    protected void onFailed(HttpResponseException responseException) {
                        super.onFailed(responseException);
                        Toast.makeText(instance, "error code : " + responseException.getStatus(), Toast.LENGTH_SHORT).show();
//                        ToastShort(instance, "error code : " + responseException.getStatus());
//                        ToastShort(instance, "网络有误！");
                    }
                });
    }


    @OnClick({R.id.iv_left, R.id.tv_left, R.id.rl_back,R.id.iv_measure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                break;
            case R.id.tv_left:
                break;
            case R.id.rl_back:
                finishActivity(instance);
                break;
            case R.id.iv_measure:
                finishActivity(instance);
                break;
        }
    }
}
