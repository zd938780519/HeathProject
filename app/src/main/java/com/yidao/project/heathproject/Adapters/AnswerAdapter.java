package com.yidao.project.heathproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidao.project.heathproject.Beans.ActiveBean;
import com.yidao.project.heathproject.Listeners.OnRecyclerViewItemClickListener;
import com.yidao.project.heathproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {



    private Context mContext;
    private OnRecyclerViewItemClickListener OnRecyclerViewItemDeClickListener;
    private List<ActiveBean.DataBean> mData;

    public AnswerAdapter(Context mContext, List<ActiveBean.DataBean> mData,OnRecyclerViewItemClickListener OnRecyclerViewItemDeClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.OnRecyclerViewItemDeClickListener = OnRecyclerViewItemDeClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_answer, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final LinearLayoutManager mHotLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        viewHolder.mRecycleOther.setLayoutManager(mHotLinearLayoutManager);
        viewHolder.mRecycleOther.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));   //添加分割线
//        holder.mRecycleHot.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.divider_mileage)); //自定义分割线样式
        viewHolder.mRecycleOther.setHasFixedSize(true);
        viewHolder.mRecycleOther.setFocusableInTouchMode(false);//不需要焦点
        viewHolder.tvType.setText(mData.get(i).getTitle());
        OtherAdapter mOtherAdapter = new OtherAdapter(mContext ,mData.get(i).getValue(), mHotRecyclerViewItemClickListener);
        viewHolder.mRecycleOther.setAdapter(mOtherAdapter);
    }



    private OnRecyclerViewItemClickListener mHotRecyclerViewItemClickListener = new OnRecyclerViewItemClickListener() {
        @Override
        public void onRecyclerViewItemClicked(int position, RecyclerView.ViewHolder viewHolder) {
            //点击跳转到简介和行情详情界面
//
        }

        @Override
        public void onRecyclerViewItemLongClicked(int position, RecyclerView.ViewHolder viewHolder) {

        }

        @Override
        public void onRecyclerViewItemClicked() {

        }

    };

    @Override
    public int getItemCount() {
//        return 6;
        return mData != null ? mData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recycle_other)
        RecyclerView mRecycleOther;
        @BindView(R.id.tv_type)
        TextView tvType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
