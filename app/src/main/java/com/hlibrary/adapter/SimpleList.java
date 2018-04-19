package com.hlibrary.adapter;

/**
 * @author linwenhui
 * @date 2017/8/16.
 */

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.List;

public class SimpleList<T> implements IListInterface<T> {


    protected List<T> datas;

    public SimpleList(@NonNull List<T> datas) {
        this.datas = datas;
    }

    @Override
    public IListInterface<T> addObject(@NonNull T t) {
        if (!this.datas.contains(t))
            datas.add(t);
        return this;
    }

    @Override
    public IListInterface<T> addObject(int pos, T t) {
        if (!this.datas.contains(t))
            datas.add(pos, t);
        return this;
    }

    @Override
    public T removeObject(int pos) {
        return datas.remove(pos);
    }

    @Override
    public IListInterface<T> removeObject(@NonNull T t) {
        datas.remove(t);
        return this;
    }

    @Override
    public IListInterface<T> addAll(@NonNull Collection<? extends T> ts) {
        for (T t : ts) {
            if (!datas.contains(t)) {
                this.datas.add(t);
            }
        }
        return this;
    }

    @Override
    public IListInterface<T> addAll(int index, @NonNull Collection<? extends T> ts) {
        datas.addAll(index, ts);
        return this;
    }

    @Override
    public IListInterface<T> removeAll() {
        datas.clear();
        return this;
    }

}
