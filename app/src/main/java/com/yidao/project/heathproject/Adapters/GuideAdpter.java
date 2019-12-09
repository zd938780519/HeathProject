package com.yidao.project.heathproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidao.project.heathproject.Activity.MoreActivity;
import com.yidao.project.heathproject.Beans.RunBean;
import com.yidao.project.heathproject.Listeners.OnRecyclerViewItemClickListener;
import com.yidao.project.heathproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideAdpter extends RecyclerView.Adapter<GuideAdpter.ViewHolder> {


    private Context mContext;
    //    private  List<RecentMsgListData> mSaveData;
    private OnRecyclerViewItemClickListener mOnRecycleViewItemClickListener;
    private List<RunBean.DataBean> mRunData;

    public GuideAdpter(Context context,List<RunBean.DataBean> mRunData, OnRecyclerViewItemClickListener mOnRecycleViewItemClickListener) {
        this.mContext = context;
        this.mRunData = mRunData;
        this.mOnRecycleViewItemClickListener = mOnRecycleViewItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_guide, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvGuidetype.setText(mRunData.get(i).getTitle());
        viewHolder.tvContext.setText(mRunData.get(i).getContent());
        viewHolder.mTvMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moreIntent = new Intent(mContext, MoreActivity.class);
                moreIntent.putExtra("Title",mRunData.get(i).getTitle());
                mContext.startActivity(moreIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRunData != null ? mRunData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_more1)
        TextView mTvMore1;
        @BindView(R.id.tv_guidetype)
        TextView tvGuidetype;
        @BindView(R.id.tv_context)
        TextView tvContext;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
