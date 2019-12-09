package com.yidao.project.heathproject.Activity;

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
import com.yidao.project.heathproject.Adapters.GuideAdpter;
import com.yidao.project.heathproject.Adapters.MattersAdapter;
import com.yidao.project.heathproject.Beans.OverBean;
import com.yidao.project.heathproject.Beans.RunBean;
import com.yidao.project.heathproject.Listeners.OnRecyclerViewItemClickListener;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;

public class MattersActivity extends BaseActivity {
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
    @BindView(R.id.tv_context)
    TextView tvContext;
//    @BindView(R.id.recycle_matter)
//    RecyclerView mRecycleMatter;
    private MattersActivity instance;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matter);
        ButterKnife.bind(this);
        instance = this;
        addActivity(instance);
        initView();
    }

    private void initView() {
        mTvTilte.setVisibility(View.VISIBLE);
        mTvTilte.setText("注意事项");
        mRlBack.setVisibility(View.VISIBLE);

//        final LinearLayoutManager mHotLinearLayoutManager = new LinearLayoutManager(instance, LinearLayoutManager.VERTICAL, false);
//        mRecycleMatter.setLayoutManager(mHotLinearLayoutManager);
////        mRecycleMatter.addItemDecoration(new DividerItemDecoration(instance, DividerItemDecoration.VERTICAL));   //添加分割线
////        holder.mRecycleHot.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.divider_mileage)); //自定义分割线样式
//        mRecycleMatter.setHasFixedSize(true);
//        mRecycleMatter.setFocusableInTouchMode(false);//不需要焦点
        setRunData();
//        MattersAdapter mMattersAdapter = new MattersAdapter(instance , mRecyclerViewItemClickListener);
//        mRecycleMatter.setAdapter(mMattersAdapter);
    }




    /**
     * 问答记录
     */
    private void setRunData() {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getMatte(application)
                .compose(this.<OverBean>bindToLifecycle())
                .compose(SchedulerTransformer.<OverBean>transformer())
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
                .subscribe(new BaseObserver<OverBean>() {
                    @Override
                    protected void onSuccess(OverBean mOverBean) {
                        if (mOverBean != null) {
                            if (mOverBean.getCode() == 200) {
//                                mTvIndex.setText(mFTJBean.getData());
                                tvContext.setText(mOverBean.getData().getContent());

                            } else {

                                Toast.makeText(instance, mOverBean.getMsg(), Toast.LENGTH_SHORT).show();
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


    @OnClick({R.id.iv_left, R.id.tv_left, R.id.rl_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                break;
            case R.id.tv_left:
                break;
            case R.id.rl_back:
                finishActivity(instance);
                break;
        }
    }
}
