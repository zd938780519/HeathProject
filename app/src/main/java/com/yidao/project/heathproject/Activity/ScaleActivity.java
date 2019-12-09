package com.yidao.project.heathproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.yidao.project.heathproject.Adapters.ScaleAdapter;
import com.yidao.project.heathproject.Beans.AnswerBean;
import com.yidao.project.heathproject.Beans.PlanBean;
import com.yidao.project.heathproject.Beans.QByTitleBean;
import com.yidao.project.heathproject.Listeners.OnRecyclerViewItemClickListener;
import com.yidao.project.heathproject.Listeners.OnTypeRecyclerViewItemClickListener;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;

public class ScaleActivity extends BaseActivity {
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
    @BindView(R.id.recycle_scale)
    RecyclerView mRecycleScale;
    private ScaleActivity instance;
    private int titleId;
    private int userId;
    private String answer12 = "";
    private List<QByTitleBean.DataBean> mQByTitleData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);
        ButterKnife.bind(this);
        instance = this;
        addActivity(instance);
        initView();
    }

    private void initView() {
        mTvTilte.setVisibility(View.VISIBLE);
        mTvTilte.setText("活动量表");
        mRlBack.setVisibility(View.VISIBLE);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText("提交");
        titleId = getIntent().getIntExtra("titleId",0);
        userId = SharedPreferencesUtility.getUserId(instance);
        getProjectData(titleId);

        final LinearLayoutManager mHotLinearLayoutManager = new LinearLayoutManager(instance, LinearLayoutManager.VERTICAL, false);
        mRecycleScale.setLayoutManager(mHotLinearLayoutManager);
//        mRecycleScale.addItemDecoration(new DividerItemDecoration(instance, DividerItemDecoration.VERTICAL));   //添加分割线
//        holder.mRecycleHot.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.divider_mileage)); //自定义分割线样式
        mRecycleScale.setHasFixedSize(true);
        mRecycleScale.setFocusableInTouchMode(false);//不需要焦点


    }


    /**
     * 提交
     */
    private void getTiJiaoData(String answer){
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getAnswerData(userId,answer,application)
                .compose(this.<AnswerBean>bindToLifecycle())
                .compose(SchedulerTransformer.<AnswerBean>transformer())
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
                .subscribe(new BaseObserver<AnswerBean>() {
                    @Override
                    protected void onSuccess(AnswerBean mAnswerBean) {
                        if (mAnswerBean != null) {
                            if (mAnswerBean.getCode() ==200){
//                                ToastShort(instance,mAnswerBean.getMsg());
                                Intent  mTestTwoIntent = new Intent(instance,TestTwoResultActivty.class);
                                mTestTwoIntent.putExtra("result",mAnswerBean.getData());
                                startActivity(mTestTwoIntent);
                                finishActivity(instance);

                            }else {
                                ToastShort(instance,mAnswerBean.getMsg());
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
     * 获得问题接口
     */
    private void getProjectData(int titleId){
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getQByTitleData(titleId,application)
                .compose(this.<QByTitleBean>bindToLifecycle())
                .compose(SchedulerTransformer.<QByTitleBean>transformer())
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
                .subscribe(new BaseObserver<QByTitleBean>() {
                    @Override
                    protected void onSuccess(QByTitleBean mQByTitleBean) {
                        if (mQByTitleBean != null) {
                            if (mQByTitleBean.getCode() ==200){
//                                ToastShort(instance,mQByTitleBean.getMsg());
                                mQByTitleData = mQByTitleBean.getData();

                                ScaleAdapter mScaleAdapter = new ScaleAdapter(instance ,mQByTitleData, mRecyclerViewItemClickListener);
                                mRecycleScale.setAdapter(mScaleAdapter);

                            }else {
                                ToastShort(instance,mQByTitleBean.getMsg());
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
     * 点击事件
     */
    private OnTypeRecyclerViewItemClickListener mRecyclerViewItemClickListener = new OnTypeRecyclerViewItemClickListener() {
        @Override
        public void onRecyclerViewItemClicked(int position,String type, RecyclerView.ViewHolder viewHolder) {
//           int vipPostion = SharedPreferencesUtility.getVipPostion(instance);
//           if (vipPostion != position){
               answer12 = answer12 + position+ ","+type+";";
//           }

            Log.e("answer1233333===",answer12);
//            if (){
//
//            }
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



//
//    /**
//     * 点击事件
//     */
//    private OnTypeRecyclerViewItemClickListener mRecyclerViewItemClickListener1 = new OnTypeRecyclerViewItemClickListener() {
//        @Override
//        public void onRecyclerViewItemClicked(int position,String type, RecyclerView.ViewHolder viewHolder) {
//            int answer = position;
//            answer12 = answer12 + position+ ","+type+";";
//            Log.e("answer12===",answer12);
////            if (){
////
////            }
////            Intent mChatIntent = new Intent(instance, ChatTwoActivity.class);  //跳转聊天页面
////            startActivity(mChatIntent);
//        }
//
//        @Override
//        public void onRecyclerViewItemLongClicked(int position, RecyclerView.ViewHolder viewHolder) {
//            Toast.makeText(instance, "长按按钮实现方式", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onRecyclerViewItemClicked() {
//
//        }
//    };



    @OnClick({R.id.iv_left, R.id.tv_left, R.id.rl_back,R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                break;
            case R.id.tv_left:
                break;
            case R.id.rl_back:
                finishActivity(instance);
                break;
            case R.id.tv_right:   //提交
//                int Id = mQByTitleData.get(0).getId();
                getTiJiaoData(answer12);
                break;
        }
    }
}
