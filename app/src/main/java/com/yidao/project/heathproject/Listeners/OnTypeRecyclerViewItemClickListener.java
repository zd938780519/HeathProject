package com.yidao.project.heathproject.Listeners;


import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/9/19/019.
 */

public interface OnTypeRecyclerViewItemClickListener {
    void onRecyclerViewItemClicked(int position,String type, RecyclerView.ViewHolder viewHolder);

    void onRecyclerViewItemLongClicked(int position, RecyclerView.ViewHolder viewHolder);
    void onRecyclerViewItemClicked();


}
