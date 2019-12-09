package com.yidao.project.heathproject.Fragemnts;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;
import com.mchsdk.paysdk.retrofitutils.rxjava.observable.SchedulerTransformer;
import com.mchsdk.paysdk.retrofitutils.rxjava.observer.BaseObserver;
import com.yidao.project.heathproject.Beans.CeShiPlanBean;
import com.yidao.project.heathproject.Beans.ChageNameBean;
import com.yidao.project.heathproject.Beans.FTJBean;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Action;

public class TestFragment extends BaseFragment {
    @BindView(R.id.tv_height)
    TextView mTvHeight;
    @BindView(R.id.tv_weight)
    TextView mTvWeight;
    @BindView(R.id.tv_distance)
    TextView mTvDistance;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.number)
    TextView mNumber;
    @BindView(R.id.tv_bends)
    TextView mTvBends;
    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.tv_regith)
    TextView mTvRegith;
    @BindView(R.id.tv_cishu)
            TextView tvCishu;
    Unbinder unbinder;
    private Context instance;
    private int userId;

    public static TestFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initView() {
        instance = getActivity();

    }

    @Override
    protected void loadData() {
        userId = SharedPreferencesUtility.getUserId(instance);
        Log.e("tag","userId000000====="+userId);
        setRrTestData(userId);
    }





    /**
     * 测试记录
     */
    private void setRrTestData(int userId) {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getCeShiPlan(userId,application)
                .compose(this.<CeShiPlanBean>bindToLifecycle())
                .compose(SchedulerTransformer.<CeShiPlanBean>transformer())
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
                .subscribe(new BaseObserver<CeShiPlanBean>() {
                    @Override
                    protected void onSuccess(CeShiPlanBean mCeShiPlanBean) {
                        if (mCeShiPlanBean != null) {
                            if (mCeShiPlanBean.getCode() == 200) {
//                                mTvIndex.setText(mFTJBean.getData());
                                mTvHeight.setText(mCeShiPlanBean.getData().get(0).getList().get(0).getRight()+"");
                                mTvWeight.setText(mCeShiPlanBean.getData().get(0).getList().get(1).getRight()+"");
                                mTvDistance.setText(mCeShiPlanBean.getData().get(1).getList().get(0).getRight()+"");
                                tvCishu.setText(mCeShiPlanBean.getData().get(1).getList().get(1).getRight()+"");
                                mTvTime.setText(mCeShiPlanBean.getData().get(2).getList().get(0).getRight()+"");
                                mNumber.setText(mCeShiPlanBean.getData().get(3).getList().get(0).getRight()+"");
                                mTvBends.setText(mCeShiPlanBean.getData().get(4).getList().get(0).getRight()+"");
                                mTvLeft.setText(mCeShiPlanBean.getData().get(5).getList().get(0).getRight()+"");
                                mTvRegith.setText(mCeShiPlanBean.getData().get(5).getList().get(0).getRight()+"");

                            } else {

                                Toast.makeText(instance, mCeShiPlanBean.getMsg(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.tv_height, R.id.tv_weight, R.id.tv_distance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_height:
                break;
            case R.id.tv_weight:
                break;
            case R.id.tv_distance:
                break;
        }
    }
}
