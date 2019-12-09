package com.yidao.project.heathproject.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidao.project.heathproject.Beans.QByTitleBean;
import com.yidao.project.heathproject.Listeners.OnRecyclerViewItemClickListener;
import com.yidao.project.heathproject.Listeners.OnTypeRecyclerViewItemClickListener;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.Utils.SharedPreferencesUtility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScaleAdapter extends RecyclerView.Adapter<ScaleAdapter.ViewHolder> {

    private Context mContext;
    //    private  List<RecentMsgListData> mSaveData;
    private OnTypeRecyclerViewItemClickListener mOnRecycleViewItemClickListener;
//    private OnTypeRecyclerViewItemClickListener mOnRecycleViewItemClickListener1;
    private List<QByTitleBean.DataBean> mQByTitleData;
    private int id;
    private  int id2;
    private String type;

    public ScaleAdapter(Context context, List<QByTitleBean.DataBean> mQByTitleData, OnTypeRecyclerViewItemClickListener mOnRecycleViewItemClickListener) {
        this.mContext = context;
        this.mQByTitleData = mQByTitleData;
        this.mOnRecycleViewItemClickListener = mOnRecycleViewItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_scale, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvProbalm.setText((i+1)+"."+mQByTitleData.get(i).getContent());
//       int postion =  viewHolder.getLayoutPosition();
        viewHolder.mLlTure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id =  mQByTitleData.get(i).getId();
//                Log.e("id====", String.valueOf(id));
//
                type = "1";
//                if (id == id2){
//
//                }else {
                    mOnRecycleViewItemClickListener.onRecyclerViewItemClicked(id,type,viewHolder);
//                }

//                SharedPreferencesUtility.setVipPostion(mContext,id);
                viewHolder.mLlFlase.setBackgroundResource(R.drawable.shape_btn_black);
                viewHolder.mLlTure.setBackgroundResource(R.drawable.shape_btn_blue);
            }
        });
        viewHolder.mLlFlase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              viewHolder.mLlFlase.setBackgroundColor();
                id2 =  mQByTitleData.get(i).getId();
                type = "0";
//               if (id == id2){
//
//               }else {
                   mOnRecycleViewItemClickListener.onRecyclerViewItemClicked(id2,type,viewHolder);
//               }
                Log.e("id77777====", String.valueOf(id));


//                SharedPreferencesUtility.setVipPostion(mContext,id);
                viewHolder.mLlFlase.setBackgroundResource(R.drawable.shape_btn_blue);
                viewHolder.mLlTure.setBackgroundResource(R.drawable.shape_btn_black);
            }
        });


    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mQByTitleData != null ? mQByTitleData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_flase)
        TextView mTvFlase;
        @BindView(R.id.ll_flase)
        LinearLayout mLlFlase;
        @BindView(R.id.tv_ture)
        TextView mTvTure;
        @BindView(R.id.ll_ture)
        LinearLayout mLlTure;
        @BindView(R.id.tv_probalm)
        TextView tvProbalm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
