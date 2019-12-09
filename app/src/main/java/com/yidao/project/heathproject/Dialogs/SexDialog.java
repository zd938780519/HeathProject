package com.yidao.project.heathproject.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.mchsdk.paysdk.retrofitutils.result.HttpResponseException;
import com.mchsdk.paysdk.retrofitutils.rxjava.observable.SchedulerTransformer;
import com.mchsdk.paysdk.retrofitutils.rxjava.observer.BaseObserver;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;
import com.yidao.project.heathproject.Activity.LoginActivity;
import com.yidao.project.heathproject.Activity.PassWordActivity;
import com.yidao.project.heathproject.Beans.CheckCodeBean;
import com.yidao.project.heathproject.Beans.RegisterBean;
import com.yidao.project.heathproject.MainActivity;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.RetrofitHttpUtil;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.functions.Action;

public class SexDialog extends RxDialogFragment {
    @BindView(R.id.tv_man)
    TextView mTvMan;
    @BindView(R.id.tv_woman)
    TextView mTvWoman;
    @BindView(R.id.et_weight)
    EditText mEtWeight;
    @BindView(R.id.iv_que)
    ImageView mIvQue;
    Unbinder unbinder;
    private Context instance;
    private String sex = "";
    private String mPhone;
    private String password;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        instance = getActivity();
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_sex);
        dialog.setCanceledOnTouchOutside(false); // 外部点击取消
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
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPhone = bundle.getString("mPhone");
            password = bundle.getString("password");
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.tv_man, R.id.tv_woman, R.id.et_weight, R.id.iv_que})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_man:
                sex = "0";
                SharedPreferencesUtility.setSex(instance,"0");  //男
                Drawable drawable = getResources().getDrawable(R.mipmap.img_8);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mTvMan.setCompoundDrawables(drawable, null, null, null);
                Drawable drawable1 = getResources().getDrawable(R.mipmap.img_7);
                drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                mTvWoman.setCompoundDrawables(drawable1, null, null, null);
                break;
            case R.id.tv_woman:
                sex = "1";
                SharedPreferencesUtility.setSex(instance,"1"); //女
                Drawable drawable2 = getResources().getDrawable(R.mipmap.img_8);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                mTvWoman.setCompoundDrawables(drawable2, null, null, null);

                Drawable drawable3 = getResources().getDrawable(R.mipmap.img_7);
                drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
                mTvMan.setCompoundDrawables(drawable3, null, null, null);

                break;
            case R.id.et_weight:

                break;
            case R.id.iv_que:
                String year = mEtWeight.getText().toString().trim();
                if (!year.equals("") && !sex.equals("") ){
                    getRegisterData(year,sex);

                }else {
                    Toast.makeText(instance, "年龄和性别不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }




    /**
     * 注册账号
     */
    private void getRegisterData(String year,String sex) {
        String application = "application/json";
        RetrofitHttpUtil.getApiService()
                .getRegisterCode(mPhone, password,  year, sex, application)
                .compose(this.<RegisterBean>bindToLifecycle())
                .compose(SchedulerTransformer.<RegisterBean>transformer())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                })
                .subscribe(new BaseObserver<RegisterBean>() {
                    @Override
                    protected void onSuccess(RegisterBean mRegisterBean) {
                        if (mRegisterBean != null) {
                            if (mRegisterBean.getCode() == 200) {
//                                ToastShort(instance,mCheckCodeBean.getMsg());
                                Intent mainIntent = new Intent(instance, LoginActivity.class);
                                startActivity(mainIntent);

                            } else {
                                Toast.makeText(instance, mRegisterBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    }

                    @Override
                    protected void onFailed(HttpResponseException responseException) {
                        super.onFailed(responseException);
//                        ToastShort.showShortToast("网络错误！");

                        Toast.makeText(instance, "error code : " + responseException.getStatus(), Toast.LENGTH_SHORT).show();
//                        ToastShort(instance, "网络有误！");
                    }
                });
        }

    }
