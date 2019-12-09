package com.yidao.project.heathproject.Fragemnts;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;
import com.mchsdk.paysdk.retrofitutils.rxjava.observable.SchedulerTransformer;
import com.mchsdk.paysdk.retrofitutils.rxjava.observer.BaseObserver;
import com.yidao.project.heathproject.Adapters.AnswerAdapter;
import com.yidao.project.heathproject.Beans.ActiveBean;
import com.yidao.project.heathproject.Beans.CeShiPlanBean;
import com.yidao.project.heathproject.Listeners.OnRecyclerViewItemClickListener;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Action;

public class AnswerFragment extends BaseFragment {

    @BindView(R.id.recycle_answer)
    RecyclerView recycleAnswer;
    Unbinder unbinder;
    private Context instance;
    private int userId;
    private List<ActiveBean.DataBean> mData = new ArrayList<>();

    public static AnswerFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        AnswerFragment fragment = new AnswerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_answer;
    }

    @Override
    protected void initView() {
        instance = getActivity();
        userId = SharedPreferencesUtility.getUserId(instance);
        final LinearLayoutManager mHotLinearLayoutManager = new LinearLayoutManager(instance, LinearLayoutManager.VERTICAL, false);
        recycleAnswer.setLayoutManager(mHotLinearLayoutManager);
        recycleAnswer.addItemDecoration(new DividerItemDecoration(instance, DividerItemDecoration.VERTICAL));   //添加分割线
//        holder.mRecycleHot.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.divider_mileage)); //自定义分割线样式
        recycleAnswer.setHasFixedSize(true);
        recycleAnswer.setFocusableInTouchMode(false);//不需要焦点
        setQaPlanData(userId);
    }




    /**
     * 问答记录
     */
    private void setQaPlanData(int userId) {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getQaPlan(userId,application)
                .compose(this.<ActiveBean>bindToLifecycle())
                .compose(SchedulerTransformer.<ActiveBean>transformer())
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
                .subscribe(new BaseObserver<ActiveBean>() {
                    @Override
                    protected void onSuccess(ActiveBean mActiveBean) {
                        if (mActiveBean != null) {
                            if (mActiveBean.getCode() == 200) {
//                                mTvIndex.setText(mFTJBean.getData());
                                mData = mActiveBean.getData();
                                AnswerAdapter mAnswerAdapter = new AnswerAdapter(instance ,mData, mHotRecyclerViewItemClickListener);
                                recycleAnswer.setAdapter(mAnswerAdapter);

                            } else {

                                Toast.makeText(instance, mActiveBean.getMsg(), Toast.LENGTH_SHORT).show();
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



    private OnRecyclerViewItemClickListener mHotRecyclerViewItemClickListener = new OnRecyclerViewItemClickListener() {
        @Override
        public void onRecyclerViewItemClicked(int position, RecyclerView.ViewHolder viewHolder) {
            //点击跳转到简介和行情详情界面
//
        }

        @Override
        public void onRecyclerViewItemLongClicked(int position, RecyclerView.ViewHolder viewHolder) {

        }

        @Override
        public void onRecyclerViewItemClicked() {

        }

    };

    @Override
    protected void loadData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
