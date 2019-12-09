package com.yidao.project.heathproject.Listeners;


import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/9/19/019.
 */

public interface OnRecyclerViewItemClickListener {
    void onRecyclerViewItemClicked(int position, RecyclerView.ViewHolder viewHolder);

    void onRecyclerViewItemLongClicked(int position, RecyclerView.ViewHolder viewHolder);
    void onRecyclerViewItemClicked();


}
