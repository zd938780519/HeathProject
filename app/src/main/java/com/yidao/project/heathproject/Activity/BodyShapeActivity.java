package com.yidao.project.heathproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
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
import com.yidao.project.heathproject.Adapters.BodyShapeAdapter;
import com.yidao.project.heathproject.Adapters.MainAdapter;
import com.yidao.project.heathproject.Beans.ByTitleBean;
import com.yidao.project.heathproject.Beans.SubhumanBean;
import com.yidao.project.heathproject.Dialogs.BeginTestDialog;
import com.yidao.project.heathproject.Dialogs.NovemberDialogs;
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

public class BodyShapeActivity extends BaseActivity {

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
    @BindView(R.id.recycle_body)
    RecyclerView mRecycleBody;
    private String title;
    private int titleId;
    private BodyShapeActivity instance;
    private int userId;
    private String sex = "0";
    private List<ByTitleBean.DataBean> mTitleData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodyshape);
        ButterKnife.bind(this);
        instance = this;
        addActivity(instance);
        initView();
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        titleId = getIntent().getIntExtra("titleId",0);
        userId = SharedPreferencesUtility.getUserId(instance);
        sex = SharedPreferencesUtility.getSex(instance);
        mTvTilte.setVisibility(View.VISIBLE);
        mTvTilte.setText(title);
        mRlBack.setVisibility(View.VISIBLE);
        getByTitleData(titleId);
        final LinearLayoutManager mHotLinearLayoutManager = new LinearLayoutManager(instance, LinearLayoutManager.VERTICAL, false);
        mRecycleBody.setLayoutManager(mHotLinearLayoutManager);
//        mRecycleScale.addItemDecoration(new DividerItemDecoration(instance, DividerItemDecoration.VERTICAL));   //添加分割线
//        holder.mRecycleHot.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.divider_mileage)); //自定义分割线样式
        mRecycleBody.setHasFixedSize(true);
        mRecycleBody.setFocusableInTouchMode(false);//不需要焦点


    }




    /**
     * 获取要测试内容
     */
    private void getByTitleData(int titleId){
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getByTitle(userId,titleId,application)
                .compose(this.<ByTitleBean>bindToLifecycle())
                .compose(SchedulerTransformer.<ByTitleBean>transformer())
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
                .subscribe(new BaseObserver<ByTitleBean>() {
                    @Override
                    protected void onSuccess(ByTitleBean mByTitleBean) {
                        if (mByTitleBean != null) {
                            if (mByTitleBean.getCode() ==200){
                                mTitleData = mByTitleBean.getData();
//                                mByTitleBean = mByTitleBean.getData().getInfoList();
                                BodyShapeAdapter mBodyShapeAdapter = new BodyShapeAdapter(instance,mTitleData,mRecyclerViewItemClickListener);
                                mRecycleBody.setAdapter(mBodyShapeAdapter);

                            }else {
                                ToastShort(instance,mByTitleBean.getMsg());
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




    private OnRecyclerViewItemClickListener mRecyclerViewItemClickListener = new OnRecyclerViewItemClickListener() {
        @Override
        public void onRecyclerViewItemClicked(int position, RecyclerView.ViewHolder viewHolder) {
            if (titleId ==1){
                FragmentManager cardfacefm = getSupportFragmentManager();
                BeginTestDialog mBeginTestDialog = new BeginTestDialog();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("account_id", accountId);  //发帖人id
//                    bundle.putString("note_id", mNoteId);   //探索id
//                    mChooseDialogFragment.setArguments(bundle);
                mBeginTestDialog.show(cardfacefm, "mBeginTestDialog");

            }else if (titleId ==2){
                if (position==0){
                    Intent mBaiDuMapIntent = new Intent(instance,BaiDuMapActivity.class);
                    startActivity(mBaiDuMapIntent);
                }else if (position==1){   //台阶测试
                    FragmentManager cardfacefm = getSupportFragmentManager();
                    NovemberDialogs mNovemberDialogs = new NovemberDialogs();
                    Bundle bundle = new Bundle();
                    bundle.putString("tv_name", "台阶测试");
                    bundle.putString("tv_type", "台阶数");
                    bundle.putString("tv_unit", "个");   //探索id
                    mNovemberDialogs.setArguments(bundle);
                    mNovemberDialogs.show(cardfacefm, "mBeginTestDialog");

//                    ToastShort(instance,"哈佛台阶实验");
                }
            }else if (titleId ==3){
                FragmentManager cardfacefm = getSupportFragmentManager();
                NovemberDialogs mNovemberDialogs = new NovemberDialogs();
                Bundle bundle = new Bundle();
                bundle.putString("tv_name", "平衡测试");
                bundle.putString("tv_type", "站立时间");
                bundle.putString("tv_unit", "秒");
                mNovemberDialogs.setArguments(bundle);
                mNovemberDialogs.show(cardfacefm, "mBeginTestDialog");
                ToastShort(instance,"平衡能力测试");
            }else if (titleId ==4){
                if(sex.equals("1")){
                    FragmentManager cardfacefm = getSupportFragmentManager();
                    NovemberDialogs mNovemberDialogs = new NovemberDialogs();
                    Bundle bundle = new Bundle();
                    bundle.putString("tv_name", "仰卧起坐测试");
                    bundle.putString("tv_type", "仰卧起坐");
                    bundle.putString("tv_unit", "个");
                    mNovemberDialogs.setArguments(bundle);
                    mNovemberDialogs.show(cardfacefm, "mBeginTestDialog");
                }else if (sex.equals("0")){
                    FragmentManager cardfacefm = getSupportFragmentManager();
                    NovemberDialogs mNovemberDialogs = new NovemberDialogs();
                    Bundle bundle = new Bundle();
                    bundle.putString("tv_name", "俯卧撑测试");
                    bundle.putString("tv_type", "俯卧撑");
                    bundle.putString("tv_unit", "个");
                    mNovemberDialogs.setArguments(bundle);
                    mNovemberDialogs.show(cardfacefm, "mBeginTestDialog");
                }

                ToastShort(instance,"肌肉耐力测试");
            }else if (titleId ==5){
                FragmentManager cardfacefm = getSupportFragmentManager();
                NovemberDialogs mNovemberDialogs = new NovemberDialogs();
                Bundle bundle = new Bundle();
                bundle.putString("tv_name", "柔韧测试");
                bundle.putString("tv_type", "柔韧度");
                bundle.putString("tv_unit", "cm");
                mNovemberDialogs.setArguments(bundle);
                mNovemberDialogs.show(cardfacefm, "mBeginTestDialog");
                ToastShort(instance,"柔韧性");
            }else if (titleId ==6){
                FragmentManager cardfacefm = getSupportFragmentManager();
                NovemberDialogs mNovemberDialogs = new NovemberDialogs();
                Bundle bundle = new Bundle();
                bundle.putString("tv_name", "肌肉力量");
                bundle.putString("tv_type", "力度");
                bundle.putString("tv_unit", "kg");
                mNovemberDialogs.setArguments(bundle);
                mNovemberDialogs.show(cardfacefm, "mBeginTestDialog");
                ToastShort(instance,"肌肉能力");
            }
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
//            case R.id.tv_begin:  //开始测试
//                FragmentManager cardfacefm = getSupportFragmentManager();
//                BeginTestDialog mBeginTestDialog = new BeginTestDialog();
////                    Bundle bundle = new Bundle();
////                    bundle.putInt("account_id", accountId);  //发帖人id
////                    bundle.putString("note_id", mNoteId);   //探索id
////                    mChooseDialogFragment.setArguments(bundle);
//                mBeginTestDialog.show(cardfacefm, "mBeginTestDialog");
//
//                break;
        }
    }
}
