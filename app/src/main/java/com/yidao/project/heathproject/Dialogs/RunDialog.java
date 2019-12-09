package com.yidao.project.heathproject.Dialogs;

import android.app.Dialog;
import android.content.Context;
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

import com.trello.rxlifecycle2.components.support.RxDialogFragment;
import com.yidao.project.heathproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RunDialog extends RxDialogFragment {

    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.et_weight)
    EditText mEtWeight;
    @BindView(R.id.iv_que)
    ImageView mIvQue;
    Unbinder unbinder;
    private Context instance;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        instance = getActivity();
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_run);
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

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();


    }


    @OnClick({R.id.iv_close, R.id.et_weight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.et_weight:
                break;
        }
    }


}
