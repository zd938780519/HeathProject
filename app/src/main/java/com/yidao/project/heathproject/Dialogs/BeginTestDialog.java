package com.yidao.project.heathproject.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;
import com.mchsdk.paysdk.retrofitutils.rxjava.observable.SchedulerTransformer;
import com.mchsdk.paysdk.retrofitutils.rxjava.observer.BaseObserver;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;
import com.yidao.project.heathproject.Activity.TestResultActivty;
import com.yidao.project.heathproject.Adapters.BodyShapeAdapter;
import com.yidao.project.heathproject.Beans.ByTitleBean;
import com.yidao.project.heathproject.Beans.Health;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Action;

public class BeginTestDialog extends RxDialogFragment {
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.et_hight)
    EditText mEtHight;
    @BindView(R.id.et_weight)
    EditText mEtWeight;
    @BindView(R.id.iv_que)
    ImageView mIvQue;
    Unbinder unbinder;
    private Context instance;
    private int userId;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        instance = getActivity();
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_testbegin);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER; // 中部显示
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT; // 宽度持平
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT; // 高度持平
        window.setAttributes(lp);
        ButterKnife.bind(this, dialog); // Dialog即View
        initData();
        return dialog;
    }


    private void initData() {
        userId = SharedPreferencesUtility.getUserId(instance);
    }









    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }

    @OnClick({R.id.iv_close, R.id.et_hight,R.id.et_weight,R.id.iv_que})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.et_hight:
                break;
            case R.id.et_weight:
                break;
            case R.id.iv_que:  //确定
                String mHight = mEtHight.getText().toString().trim();
                String mWeight = mEtWeight.getText().toString().trim();
                if (!mHight.equals("") && !mWeight.equals("")){
                    Intent TestResultIntent = new Intent(instance, TestResultActivty.class);
                    TestResultIntent.putExtra("mHight",mHight);
                    TestResultIntent.putExtra("mWeight",mWeight);
                    startActivity(TestResultIntent);
//                    getByTitleData();
                }else {
                    Toast.makeText(instance, "身高和体重不能为空！" , Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }
}
