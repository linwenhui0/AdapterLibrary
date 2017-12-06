package com.hlibrary.adapter;

import android.support.annotation.NonNull;

import com.hlibrary.adapter.model.HeadFootAdatperVo;

import java.util.Collection;
import java.util.List;

/**
 * Created by linwenhui on 2017/8/16.
 */

public class ExpandSimpleList<T, K, HVo extends HeadFootAdatperVo, FVo extends HeadFootAdatperVo> extends SimpleList<T, K> {

    private List<HVo> headLayoutIds;
    private List<FVo> footLayoutIds;

    public ExpandSimpleList(@NonNull List<T> datas, @NonNull K adapter, Class<K> kClass, List<HVo> headLayoutIds, List<FVo> footLayoutIds) {
        super(datas, adapter, kClass);
        this.headLayoutIds = headLayoutIds;
        this.footLayoutIds = footLayoutIds;
    }

    public ExpandSimpleList<T, K, HVo, FVo> addHeadView(@NonNull HVo headLayoutId) {
        if (!headLayoutIds.contains(headLayoutId)) {
            headLayoutIds.add(headLayoutId);
        }
        return this;
    }

    public ExpandSimpleList<T, K, HVo, FVo> addFootView(@NonNull FVo footLayoutId) {
        if (!footLayoutIds.contains(footLayoutId)) {
            footLayoutIds.add(footLayoutId);
        }
        return this;
    }

    private int getItemCount() {
        return datas.size() + headLayoutIds.size() + footLayoutIds.size();
    }

    @Override
    public ExpandSimpleList<T, K, HVo, FVo> addObject(T t) {
        int size = getItemCount();
        int footNum = footLayoutIds.size();
        if (footNum > 0)
            super.addObject(size - footNum, t);
        else super.addObject(t);
        return this;
    }

    @Override
    public ExpandSimpleList<T, K, HVo, FVo> addAll(Collection<? extends T> ts) {
        int size = getItemCount();
        int footNum = footLayoutIds.size();
        if (footNum > 0)
            super.addAll(size - footNum, ts);
        else super.addAll(ts);
        return this;
    }

}
