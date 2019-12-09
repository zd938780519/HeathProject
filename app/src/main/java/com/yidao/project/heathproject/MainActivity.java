package com.yidao.project.heathproject;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;
import com.mchsdk.paysdk.retrofitutils.rxjava.observable.SchedulerTransformer;
import com.mchsdk.paysdk.retrofitutils.rxjava.observer.BaseObserver;
import com.yidao.project.heathproject.Activity.BodyShapeActivity;
import com.yidao.project.heathproject.Activity.CardSkillActivity;
import com.yidao.project.heathproject.Activity.GuideActivty;
import com.yidao.project.heathproject.Activity.MySelfActivity;
import com.yidao.project.heathproject.Activity.ScaleActivity;
import com.yidao.project.heathproject.Adapters.MainAdapter;
import com.yidao.project.heathproject.Beans.AnswerBean;
import com.yidao.project.heathproject.Beans.LoginBean;
import com.yidao.project.heathproject.Beans.PlanBean;
import com.yidao.project.heathproject.Beans.SubhumanBean;
import com.yidao.project.heathproject.Listeners.OnRecyclerViewItemClickListener;
import com.yidao.project.heathproject.RxJavaUtils.ApiService;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.EyesUtils;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;
import com.yidao.project.heathproject.Utils.WrappableGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Action;
import kr.co.namee.permissiongen.PermissionGen;

public class MainActivity extends BaseActivity {

    @BindView(R.id.circle_head)
    CircleImageView mCircleHead;
    @BindView(R.id.tv_prepare)
    TextView mTvPrepare;
    @BindView(R.id.tv_history)
    TextView mTvHistory;
    @BindView(R.id.tv_symptoms)
    TextView mTvSymptoms;
    @BindView(R.id.tv_problem)
    TextView mTvProblem;
    @BindView(R.id.tv_painstak)
    TextView mTvPainstak;
    @BindView(R.id.recycle)
    RecyclerView mRecycle;
    @BindView(R.id.nestedScrollView)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.iv_guide)
    ImageView ivGuide;
    @BindView(R.id.tv_over)
    TextView tv_over;
    @BindView(R.id.tv_yunnum)
    TextView tvYunnum;
    private MainActivity instance;
    private final int PERMISSION_REQUESTCODE = 1;
    private int titleId;
    private List<PlanBean.DataBean.InfoListBean> mPlanData = new ArrayList<>();
    private  int userId;
    private List<SubhumanBean.DataBean.InfoListBean> mSubhumanData = new ArrayList<>();
    private int SubPlanNum;
    private int SubTotalNum;
    private int TotalNum;
    private int PlanNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        ButterKnife.bind(this);

        EyesUtils.setImmersionStateMode(instance);  //实现沉浸
        initView();
    }


    private void initView() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            //一次申请多个权限
//            PermissionUtils.requestMultiPermissions(this, mPermissionGrant);
            init();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUESTCODE);
        }

        userId = SharedPreferencesUtility.getUserId(instance);
        String headiamge = SharedPreferencesUtility.getAvatar(instance);
        Glide.with(instance)
                .load(ApiService.SHARE_BASEURL+headiamge)
//                .placeholder(R.mipmap.img_avatar_3)
                .centerCrop()   //圆角
                .into(mCircleHead);
        RiskData(userId);  //活动量表

        getsubhumanData();   //获取类目


        final LinearLayoutManager mHotLinearLayoutManager = new LinearLayoutManager(instance, LinearLayoutManager.VERTICAL, false);
        mRecycle.setLayoutManager(mHotLinearLayoutManager);
//        mMainRecycle.addItemDecoration(new DividerItemDecoration(instance, DividerItemDecoration.VERTICAL));   //添加分割线
////        holder.mRecycleHot.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.divider_mileage)); //自定义分割线样式
        mRecycle.setHasFixedSize(true);
        mRecycle.setNestedScrollingEnabled(false);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        final WrappableGridLayoutManager manager = new WrappableGridLayoutManager(instance, 2);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);     //防止瀑布流图片闪烁
        mRecycle.setLayoutManager(manager);
        //        recycleShop.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

    }


    private OnRecyclerViewItemClickListener mRecyclerViewItemClickListener = new OnRecyclerViewItemClickListener() {
        @Override
        public void onRecyclerViewItemClicked(int position, RecyclerView.ViewHolder viewHolder) {
//            if (position == 0){
                Intent mChatIntent = new Intent(instance, BodyShapeActivity.class);  //身体形态测试
                mChatIntent.putExtra("title",mSubhumanData.get(position).getTitle());
                mChatIntent.putExtra("titleId",mSubhumanData.get(position).getId());
                startActivity(mChatIntent);
//            }
//            else if(position == 1){
//                Intent mCardSkillIntent = new Intent(instance, CardSkillActivity.class);  //心肺技能
//                startActivity(mCardSkillIntent);
//            }

        }

        @Override
        public void onRecyclerViewItemLongClicked(int position, RecyclerView.ViewHolder viewHolder) {
            Toast.makeText(instance, "长按按钮实现方式", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRecyclerViewItemClicked() {

        }
    };



    public void init() {
        PermissionGen.with(this)
                .addRequestCode(100)
                .permissions(
                        //电话通讯录
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.READ_PHONE_STATE,
                        //位置
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        //相机、麦克风
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        //存储空间
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_SETTINGS
                )
                .request();

    }


    @OnClick({R.id.circle_head, R.id.tv_prepare, R.id.tv_history,R.id.iv_guide,R.id.tv_symptoms,R.id.tv_problem,R.id.tv_painstak})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circle_head:   //我的个人页面
                Intent MySelfIntent = new Intent(instance, MySelfActivity.class);
                startActivity(MySelfIntent);
                break;
            case R.id.tv_prepare:  //活动准备
                Intent ScaleIntent = new Intent(instance, ScaleActivity.class);
                ScaleIntent.putExtra("titleId",   mPlanData.get(0).getId());
                startActivity(ScaleIntent);
                break;
            case R.id.tv_history:  //症状
                Intent ScaleIntent1 = new Intent(instance, ScaleActivity.class);
                ScaleIntent1.putExtra("titleId",   mPlanData.get(1).getId());
                startActivity(ScaleIntent1);
                break;
            case R.id.tv_symptoms:   //病史
                Intent ScaleIntent2 = new Intent(instance, ScaleActivity.class);
                ScaleIntent2.putExtra("titleId",   mPlanData.get(2).getId());
                startActivity(ScaleIntent2);
                break;
            case R.id.tv_problem:  //其他健康问题
                Intent ScaleIntent3 = new Intent(instance, ScaleActivity.class);
                ScaleIntent3.putExtra("titleId",   mPlanData.get(3).getId());
                startActivity(ScaleIntent3);
                break;
            case R.id.tv_painstak:   //心血管疾病
                Intent ScaleIntent4 = new Intent(instance, ScaleActivity.class);
                ScaleIntent4.putExtra("titleId",   mPlanData.get(4).getId());
                startActivity(ScaleIntent4);
                break;
            case R.id.iv_guide:    //运动指导
                Intent GuideIntent = new Intent(instance, GuideActivty.class);
                startActivity(GuideIntent);
                break;
        }
    }


    /**
     * 风险量表
     */
    private void RiskData(int userId){
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getRiskData(userId,application)
                .compose(this.<PlanBean>bindToLifecycle())
                .compose(SchedulerTransformer.<PlanBean>transformer())
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
                .subscribe(new BaseObserver<PlanBean>() {
                    @Override
                    protected void onSuccess(PlanBean mPlanBean) {
                        if (mPlanBean != null) {
                            if (mPlanBean.getCode() ==200){
//                                ToastShort(instance,mPlanBean.getMsg());
                                mPlanData = mPlanBean.getData().getInfoList();
                                mTvPrepare.setText(mPlanBean.getData().getInfoList().get(0).getTitle());
                                if (mPlanBean.getData().getInfoList().get(0).getIsPlan() ==0){
                                    mTvPrepare.setTextColor(getResources().getColor(R.color.color_888888));
                                }else {
                                    mTvPrepare.setTextColor(getResources().getColor(R.color.color_24D196));
                                }
                                mTvHistory.setText(mPlanBean.getData().getInfoList().get(1).getTitle());
                                if (mPlanBean.getData().getInfoList().get(1).getIsPlan() == 0){
                                    mTvHistory.setTextColor(getResources().getColor(R.color.color_888888));
                                }else {
                                    mTvHistory.setTextColor(getResources().getColor(R.color.color_24D196));
                                }
                                mTvSymptoms.setText(mPlanBean.getData().getInfoList().get(2).getTitle());
                                if (mPlanBean.getData().getInfoList().get(2).getIsPlan() ==0){
                                    mTvSymptoms.setTextColor(getResources().getColor(R.color.color_888888));
                                }else {
                                    mTvSymptoms.setTextColor(getResources().getColor(R.color.color_24D196));
                                }
                                mTvProblem.setText(mPlanBean.getData().getInfoList().get(3).getTitle());
                                if (mPlanBean.getData().getInfoList().get(3).getIsPlan() ==0){
                                    mTvProblem.setTextColor(getResources().getColor(R.color.color_888888));
                                }else {
                                    mTvProblem.setTextColor(getResources().getColor(R.color.color_24D196));
                                }
                                mTvPainstak.setText(mPlanBean.getData().getInfoList().get(4).getTitle());
                                if (mPlanBean.getData().getInfoList().get(4).getIsPlan() ==0){
                                    mTvPainstak.setTextColor(getResources().getColor(R.color.color_888888));
                                }else {
                                    mTvPainstak.setTextColor(getResources().getColor(R.color.color_24D196));
                                }
                                TotalNum = mPlanBean.getData().getTotalNum();
                                PlanNum = mPlanBean.getData().getPlanNum();
                                if (TotalNum ==  PlanNum){
                                    tv_over.setText("已完成");
                                }else {
                                    tv_over.setText("已完成"+"(" +PlanNum +"/"+ TotalNum+")");
                                }


//                                finishAllActivity();

//                                SharedPreferencesUtility.setUserId(instance,"");
                            }else {
                                ToastShort(instance,mPlanBean.getMsg());
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
     * 获取类目
     */
    private void getsubhumanData(){
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getSubhumanData(userId,application)
                .compose(this.<SubhumanBean>bindToLifecycle())
                .compose(SchedulerTransformer.<SubhumanBean>transformer())
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
                .subscribe(new BaseObserver<SubhumanBean>() {
                    @Override
                    protected void onSuccess(SubhumanBean mSubhumanBean) {
                        if (mSubhumanBean != null) {
                            if (mSubhumanBean.getCode() ==200){
                                mSubhumanData = mSubhumanBean.getData().getInfoList();
                                SubPlanNum = mSubhumanBean.getData().getPlanNum();
                                SubTotalNum = mSubhumanBean.getData().getTotalNum();
                                if (SubPlanNum != SubTotalNum){
                                    tvYunnum.setText("已完成"+"(" +SubPlanNum +"/"+ SubTotalNum+")");
                                }else if (SubPlanNum == SubTotalNum){
                                    tvYunnum.setText("已完成");
                                }

//                                if (SubPlanNum == SubTotalNum && TotalNum ==  PlanNum){
                                    ivGuide.setVisibility(View.VISIBLE);
//                                }

                                MainAdapter mMainAdapter = new MainAdapter(instance, mSubhumanData,mRecyclerViewItemClickListener);
                                mRecycle.setAdapter(mMainAdapter);

                            }else {
                                ToastShort(instance,mSubhumanBean.getMsg());
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


    @Override
    protected void onResume() {
        super.onResume();
        RiskData(userId);  //活动量表
        getsubhumanData();   //获取类目
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




