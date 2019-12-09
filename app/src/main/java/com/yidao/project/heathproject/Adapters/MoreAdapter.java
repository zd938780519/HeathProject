package com.yidao.project.heathproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidao.project.heathproject.Beans.RunBean;
import com.yidao.project.heathproject.Listeners.OnRecyclerViewItemClickListener;
import com.yidao.project.heathproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.ViewHolder>{


    private Context mContext;
    //    private  List<RecentMsgListData> mSaveData;
    private OnRecyclerViewItemClickListener mOnRecycleViewItemClickListener;
    private List<RunBean.DataBean> mRunData;

    public MoreAdapter(Context context,List<RunBean.DataBean> mRunData, OnRecyclerViewItemClickListener mOnRecycleViewItemClickListener) {
        this.mContext = context;
        this.mRunData = mRunData;
        this.mOnRecycleViewItemClickListener = mOnRecycleViewItemClickListener;
    }
    @NonNull
    @Override
    public MoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_more, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreAdapter.ViewHolder viewHolder, int i) {
        viewHolder.neirong.setText(mRunData.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return mRunData != null ? mRunData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.neirong)
        TextView neirong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
