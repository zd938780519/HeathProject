package com.yidao.project.heathproject.Activity;

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
import com.yidao.project.heathproject.Beans.ChageNameBean;
import com.yidao.project.heathproject.Beans.FTJBean;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;

public class NiceNameActivity extends BaseActivity {
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
    @BindView(R.id.et_username)
    EditText mEtUsername;
    private NiceNameActivity instance;
    private int userId;
    private String mUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nicename);
        ButterKnife.bind(this);
        instance = this;
        addActivity(instance);
        initView();
    }


    private void initView() {
        mTvTilte.setVisibility(View.VISIBLE);
        mTvTilte.setText("昵称");
        mRlBack.setVisibility(View.VISIBLE);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText("保存");
        mUsername = getIntent().getStringExtra("mUsername");
        mEtUsername.setText(mUsername);
        userId = SharedPreferencesUtility.getUserId(instance);
    }





    /**
     * 修改昵称
     */
    private void getChanageNameData(String userName) {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getChangeUserName(userId, userName, application)
                .compose(this.<ChageNameBean>bindToLifecycle())
                .compose(SchedulerTransformer.<ChageNameBean>transformer())
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
                .subscribe(new BaseObserver<ChageNameBean>() {
                    @Override
                    protected void onSuccess(ChageNameBean mChageNameBean) {
                        if (mChageNameBean != null) {
                            if (mChageNameBean.getCode() == 200) {
                                SharedPreferencesUtility.setUsername(instance,userName);
                                finishActivity(instance);
//                                mTvIndex.setText(mChageNameBean.getData());

                            } else {

                                Toast.makeText(instance, mChageNameBean.getMsg(), Toast.LENGTH_SHORT).show();
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



    @OnClick({R.id.iv_left, R.id.tv_left, R.id.rl_back,R.id.rl_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                break;
            case R.id.tv_left:
                break;
            case R.id.rl_back:
                finishActivity(instance);
                break;
            case R.id.rl_right:   //保存
                String name = mEtUsername.getText().toString().trim();
                if (!name.equals("")){
                    getChanageNameData(name);
                }

                break;
        }
    }
}
