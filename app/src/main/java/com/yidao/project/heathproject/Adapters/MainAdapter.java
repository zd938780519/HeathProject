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

import com.bumptech.glide.Glide;
import com.yidao.project.heathproject.Beans.SubhumanBean;
import com.yidao.project.heathproject.Listeners.OnRecyclerViewItemClickListener;
import com.yidao.project.heathproject.R;
import com.yidao.project.heathproject.RxJavaUtils.ApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {


    private Context mContext;
    private List<SubhumanBean.DataBean.InfoListBean> mSubhumanData;
    private OnRecyclerViewItemClickListener mOnRecycleViewItemClickListener;

    public MainAdapter(Context context, List<SubhumanBean.DataBean.InfoListBean> mSubhumanData,OnRecyclerViewItemClickListener mOnRecycleViewItemClickListener) {
        this.mContext = context;
        this.mSubhumanData = mSubhumanData;
        this.mOnRecycleViewItemClickListener = mOnRecycleViewItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvShape.setText(mSubhumanData.get(position).getTitle());
        holder.tvWeight.setText(mSubhumanData.get(position).getShow());
//        if (position ==0){
            holder.llShape.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRecycleViewItemClickListener.onRecyclerViewItemClicked(position,holder);
                }
            });
            if (position ==0 ){
                holder.mIvForm.setImageResource(R.mipmap.img_1);
            }else if (position ==1){
                holder.mIvForm.setImageResource(R.mipmap.img_2);
            }else if (position ==2){
                holder.mIvForm.setImageResource(R.mipmap.img_3);
            }else if (position ==3){
                holder.mIvForm.setImageResource(R.mipmap.img_4);
            }else if (position ==4){
                holder.mIvForm.setImageResource(R.mipmap.img_5);
            }else if (position ==5){
                holder.mIvForm.setImageResource(R.mipmap.img_6);
            }

//
//        Glide.with(mContext)
//                .load(ApiService.SHARE_BASEURL+mSubhumanData.get(position).getImage())
//                .placeholder(R.mipmap.img_avatar_3)
//                .centerCrop()   //圆角
//                .into(holder.mIvForm);

//        }
//        else if (position ==1){
//            holder.llShape.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnRecycleViewItemClickListener.onRecyclerViewItemClicked(1,holder);
//                }
//            });
//        }




    }

    @Override
    public int getItemCount() {
//        return 6;
        return mSubhumanData != null ? mSubhumanData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_text)
        LinearLayout mLlText;
        @BindView(R.id.iv_form)
        ImageView mIvForm;
        @BindView(R.id.ll_shape)
        LinearLayout llShape;
        @BindView(R.id.tv_shape)
        TextView tvShape;
        @BindView(R.id.tv_weight)
        TextView tvWeight;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
