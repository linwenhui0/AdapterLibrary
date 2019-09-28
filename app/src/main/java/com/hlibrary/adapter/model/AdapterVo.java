package com.hlibrary.adapter.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by linwenhui on 2016/11/2.
 */

public class AdapterVo implements Serializable {
    private int layoutResId;

    public AdapterVo(int layoutResId) {
        this.layoutResId = layoutResId;
    }

    public int getLayoutResId() {
        return layoutResId;
    }

    @Override
    public boolean equals(@NonNull Object obj) {
        if (obj instanceof AdapterVo){
            AdapterVo otherObj = (AdapterVo) obj;
            return getLayoutResId() == otherObj.getLayoutResId();
        }
        return super.equals(obj);
    }
}
