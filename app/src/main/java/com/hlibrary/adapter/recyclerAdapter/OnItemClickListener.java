package com.hlibrary.adapter.recyclerAdapter;

import android.view.View;

/**
 * @author linwh
 * @since 2015-08-26
 */
public interface OnItemClickListener<T> {
     void onItemClick(View v, int postion, T bean);
}
