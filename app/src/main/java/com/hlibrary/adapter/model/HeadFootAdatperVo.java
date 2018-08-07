package com.hlibrary.adapter.model;

/**
 * Created by linwenhui on 2017/2/28.
 */

public class HeadFootAdatperVo extends AdapterVo {
    private int variableId;

    public HeadFootAdatperVo(int layoutResId) {
        super(layoutResId);
    }


    public int getVariableId() {
        return variableId;
    }

    public void setVariableId(int variableId) {
        this.variableId = variableId;
    }


}
