package com.hlibrary.adapter.model;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * @author linwh
 * @date 2016-09-06
 */
public class RecylerViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    public RecylerViewHolder(View itemView) {
        super(itemView);
    }


    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }
}
