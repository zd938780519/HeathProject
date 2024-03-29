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

public class TestTwoResultActivty extends BaseActivity {
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

    private TestTwoResultActivty instance;
    private int userId;
    private String result;

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
        mTvTilte.setVisibility(View.VISIBLE);
        mTvTilte.setText("健康提议");
        mRlBack.setVisibility(View.VISIBLE);

        result = getIntent().getStringExtra("result");
        mTvIndex.setText(result);
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
