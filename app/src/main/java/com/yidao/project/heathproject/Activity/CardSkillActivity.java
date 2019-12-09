package com.yidao.project.heathproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.yidao.project.heathproject.Dialogs.BeginTestDialog;
import com.yidao.project.heathproject.Dialogs.RunDialog;
import com.yidao.project.heathproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardSkillActivity extends BaseActivity {

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
    @BindView(R.id.iv_run)
    ImageView mIvRun;
    @BindView(R.id.tv_hafo)
    TextView mTvHafo;
    @BindView(R.id.iv_hafo)
    ImageView mIvHafo;
    private CardSkillActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardskills);
        ButterKnife.bind(this);
        instance = this;
        addActivity(instance);
        initView();
    }

    private void initView() {
        mTvTilte.setVisibility(View.VISIBLE);
        mTvTilte.setText("心肺技能");
        mRlBack.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.iv_left, R.id.tv_left, R.id.rl_back,R.id.iv_run,R.id.iv_hafo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:

                break;
            case R.id.tv_left:
                break;
            case R.id.rl_back:
                finishActivity(instance);
                break;
            case R.id.iv_run:   //12分钟跑步测试
                Intent mBaiDuMapIntent = new Intent(instance,BaiDuMapActivity.class);
                startActivity(mBaiDuMapIntent);
//                FragmentManager cardfacefm = getSupportFragmentManager();
//                RunDialog mRunDialog = new RunDialog();
////                    Bundle bundle = new Bundle();
////                    bundle.putInt("account_id", accountId);  //发帖人id
////                    bundle.putString("note_id", mNoteId);   //探索id
////                    mChooseDialogFragment.setArguments(bundle);
//                mRunDialog.show(cardfacefm, "mRunDialog");

                break;
            case R.id.iv_hafo:

                break;
        }
    }
}
