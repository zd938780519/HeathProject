package com.yidao.project.heathproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidao.project.heathproject.Listeners.OnRecyclerViewItemClickListener;
import com.yidao.project.heathproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MattersAdapter extends RecyclerView.Adapter<MattersAdapter.ViewHolder> {


    private Context mContext;
    //    private  List<RecentMsgListData> mSaveData;
    private OnRecyclerViewItemClickListener mOnRecycleViewItemClickListener;

    public MattersAdapter(Context context, OnRecyclerViewItemClickListener mOnRecycleViewItemClickListener) {
        this.mContext = context;
//        this.mSaveData = mSaveData;
        this.mOnRecycleViewItemClickListener = mOnRecycleViewItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_mattera, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 6;
//        return mSaveData != null ? mSaveData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_context)
        TextView mTvContext;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
