package com.yidao.project.heathproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.yidao.project.heathproject.Adapters.AnswerAdapter;
import com.yidao.project.heathproject.Adapters.GuideAdpter;
import com.yidao.project.heathproject.Beans.ActiveBean;
import com.yidao.project.heathproject.Beans.RunBean;
import com.yidao.project.heathproject.Listeners.OnRecyclerViewItemClickListener;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;

public class GuideActivty extends BaseActivity {
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
//    @BindView(R.id.tv_more)
//    TextView mTvMore;
//    @BindView(R.id.tv_more1)
//    TextView mTvMore1;
//    @BindView(R.id.tv_more2)
//    TextView mTvMore2;
    @BindView(R.id.recycle_guide)
    RecyclerView mRecycleGuide;
    private GuideActivty instance;
    private int userId;
    private List<RunBean.DataBean> mRunData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        instance = this;
        initView();
    }


    private void initView() {
        mTvTilte.setVisibility(View.VISIBLE);
        mTvTilte.setText("运动指导");
        mRlBack.setVisibility(View.VISIBLE);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText("注意事项");
        userId = SharedPreferencesUtility.getUserId(instance);
        final LinearLayoutManager mHotLinearLayoutManager = new LinearLayoutManager(instance, LinearLayoutManager.VERTICAL, false);
        mRecycleGuide.setLayoutManager(mHotLinearLayoutManager);
//        mRecycleGuide.addItemDecoration(new DividerItemDecoration(instance, DividerItemDecoration.VERTICAL));   //添加分割线
//        holder.mRecycleHot.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.divider_mileage)); //自定义分割线样式
        mRecycleGuide.setHasFixedSize(true);
        mRecycleGuide.setFocusableInTouchMode(false);//不需要焦点

        setRunData(userId);

    }



    /**
     * 问答记录
     */
    private void setRunData(int userId) {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getRun(userId,application)
                .compose(this.<RunBean>bindToLifecycle())
                .compose(SchedulerTransformer.<RunBean>transformer())
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
                .subscribe(new BaseObserver<RunBean>() {
                    @Override
                    protected void onSuccess(RunBean mRunBean) {
                        if (mRunBean != null) {
                            if (mRunBean.getCode() == 200) {
//                                mTvIndex.setText(mFTJBean.getData());
                                mRunData = mRunBean.getData();
                                GuideAdpter mGuideAdpter = new GuideAdpter(instance , mRunData,mRecyclerViewItemClickListener);
                                mRecycleGuide.setAdapter(mGuideAdpter);
//                                mData = mActiveBean.getData().get活动准备();
//                                AnswerAdapter mAnswerAdapter = new AnswerAdapter(instance ,mData, mHotRecyclerViewItemClickListener);
//                                recycleAnswer.setAdapter(mAnswerAdapter);

                            } else {

                                Toast.makeText(instance, mRunBean.getMsg(), Toast.LENGTH_SHORT).show();
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
     * 点击事件
     */
    private OnRecyclerViewItemClickListener mRecyclerViewItemClickListener = new OnRecyclerViewItemClickListener() {
        @Override
        public void onRecyclerViewItemClicked(int position, RecyclerView.ViewHolder viewHolder) {
//            Intent mChatIntent = new Intent(instance, ChatTwoActivity.class);  //跳转聊天页面
//            startActivity(mChatIntent);
        }

        @Override
        public void onRecyclerViewItemLongClicked(int position, RecyclerView.ViewHolder viewHolder) {
            Toast.makeText(instance, "长按按钮实现方式", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRecyclerViewItemClicked() {

        }
    };


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
            case R.id.rl_right:   //注意事项
                Intent MattersIntent = new Intent(instance,MattersActivity.class);
                startActivity(MattersIntent);
                break;
        }
    }

}
