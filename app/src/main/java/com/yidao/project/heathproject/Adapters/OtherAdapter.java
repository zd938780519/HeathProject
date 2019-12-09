package com.yidao.project.heathproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.ViewHolder> {


    private Context mContext;
    private OnRecyclerViewItemClickListener OnRecyclerViewItemDeClickListener;
    private List<ActiveBean.DataBean.ValueBean> mValueBean;

    public OtherAdapter(Context mContext,List<ActiveBean.DataBean.ValueBean> mValueBean, OnRecyclerViewItemClickListener OnRecyclerViewItemDeClickListener) {
        this.mContext = mContext;
        this.mValueBean = mValueBean;
        this.OnRecyclerViewItemDeClickListener = OnRecyclerViewItemDeClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_other, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTvAnswer.setText("问:" + (i+ 1 )+mValueBean.get(i).getQ());
        if (mValueBean.get(i).getA()==0){
            viewHolder.mTvTure.setText("否");
        }else if (mValueBean.get(i).getA()==1){
            viewHolder.mTvTure.setText("是");
        }
//        viewHolder.mTvTure.setText(mData.get(i).getQ());
    }

    @Override
    public int getItemCount() {
//        return 6;
        return mValueBean != null ? mValueBean.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_answer)
        TextView mTvAnswer;
        @BindView(R.id.tv_ture)
        TextView mTvTure;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
