package com.yidao.project.heathproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidao.project.heathproject.Beans.ByTitleBean;
import com.yidao.project.heathproject.Listeners.OnRecyclerViewItemClickListener;
import com.yidao.project.heathproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BodyShapeAdapter extends RecyclerView.Adapter<BodyShapeAdapter.ViewHolder> {



    private Context mContext;
    //    private List<SubhumanBean.DataBean.InfoListBean> mSubhumanData;
    private List<ByTitleBean.DataBean> mTitleData;
    private OnRecyclerViewItemClickListener mOnRecycleViewItemClickListener;

    public BodyShapeAdapter(Context context, List<ByTitleBean.DataBean> mTitleData, OnRecyclerViewItemClickListener mOnRecycleViewItemClickListener) {
        this.mContext = context;
        this.mTitleData = mTitleData;
        this.mOnRecycleViewItemClickListener = mOnRecycleViewItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_bodyshape, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTvName.setText(mTitleData.get(i).getTitle());
        viewHolder.mTvContext.setText(mTitleData.get(i).getContent());
        viewHolder.mIvRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecycleViewItemClickListener.onRecyclerViewItemClicked(i,viewHolder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitleData != null ? mTitleData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_context)
        TextView mTvContext;
        @BindView(R.id.iv_run)
        ImageView mIvRun;
        @BindView(R.id.ll_start)
        LinearLayout mLlStart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
