package com.yidao.project.heathproject.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;
import com.mchsdk.paysdk.retrofitutils.rxjava.observable.SchedulerTransformer;
import com.mchsdk.paysdk.retrofitutils.rxjava.observer.BaseObserver;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;
import com.yidao.project.heathproject.Activity.TestResultActivty;
import com.yidao.project.heathproject.Beans.Health;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Action;

public class NovemberDialogs extends RxDialogFragment {
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.et_hight)
    EditText mEtHight;
    @BindView(R.id.tv_unit)
    TextView mTvUnit;
    @BindView(R.id.iv_que)
    ImageView mIvQue;
    Unbinder unbinder;
    private Context instance;
    private int userId;
    private String tv_name;
    private String tv_type;
    private String tv_unit;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        instance = getActivity();
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialogs_november);
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
        Bundle bundle = getArguments();
        if (bundle != null) {
            tv_name = bundle.getString("tv_name");
            tv_type = bundle.getString("tv_type");
            tv_unit = bundle.getString("tv_unit");
        }
        mTvName.setText(tv_name);
        mTvType.setText(tv_type);
        mTvUnit.setText(tv_unit);
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();

    }




    @OnClick({R.id.tv_name, R.id.iv_close, R.id.tv_type,R.id.iv_que})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_name:
                break;
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_type:
                break;
            case R.id.iv_que:
                String mNumber = mEtHight.getText().toString().trim();
                if (!mNumber.equals("")){
                    Intent TestResultIntent = new Intent(instance, TestResultActivty.class);
                    TestResultIntent.putExtra("mNumber",mNumber);
                    TestResultIntent.putExtra("tv_name", tv_name);
                    startActivity(TestResultIntent);
                }else {
                    Toast.makeText(instance, "数据不能为空！", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
