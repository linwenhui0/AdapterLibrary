package com.hlibrary.adapter;

/**
 * @author linwenhui
 * @date 2017/8/16.
 */

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class SimpleList<T, K> implements IListInterface<T> {


    protected List<T> datas;
    protected WeakReference<K> adapter;
    private Class<K> kClass;

    public SimpleList(@NonNull List<T> datas, @NonNull K adapter, Class<K> kClass) {
        this.datas = datas;
        this.adapter = new WeakReference<>(adapter);
        this.kClass = kClass;
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

    @Override
    public IListInterface<T> notifyDataSetChanged() throws NoSuchMethodException {
        reflectMethod("notifyDataSetChanged");
        return this;
    }

    @Override
    public IListInterface<T> notifyDataSetInvalidated() throws NoSuchMethodException {
        reflectMethod("notifyDataSetInvalidated");
        return this;
    }

    public Object reflectMethod(String methodName) throws NoSuchMethodException {
        if (adapter != null) {
            K objectBaseAdapter = adapter.get();
            if (objectBaseAdapter != null) {
                try {
                    Method method = kClass.getDeclaredMethod(methodName);
                    return method.invoke(objectBaseAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NoSuchMethodException("No Such Method");
                }
            }
        }
        return null;
    }

    @Override
    public Object reflectMethod(String methodName, Class[] params, Object[] paramValues) throws NoSuchMethodException {
        if (adapter != null) {
            K objectBaseAdapter = adapter.get();
            if (objectBaseAdapter != null) {
                try {
                    Method method = kClass.getDeclaredMethod(methodName, params);
                    return method.invoke(objectBaseAdapter, paramValues);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NoSuchMethodException("No Such Method");
                }
            }
        }
        return null;
    }

}
