package com.hlibrary.adapter;

import android.support.annotation.NonNull;

import java.util.Collection;

/**
 * @author linwenhui
 * @date 2017/8/16.
 */
public interface IListInterface<T> {
    IListInterface<T> addObject(@NonNull T t);

    IListInterface<T> addObject(int pos, T t);

    T removeObject(int pos);

    IListInterface<T> removeObject(@NonNull T t);

    IListInterface<T> addAll(@NonNull Collection<? extends T> ts);

    IListInterface<T> addAll(int index, @NonNull Collection<? extends T> ts);

    IListInterface<T> removeAll();

    IListInterface<T> notifyDataSetChanged() throws NoSuchMethodException;

    IListInterface<T> notifyDataSetInvalidated() throws NoSuchMethodException;

    Object reflectMethod(String methodName) throws NoSuchMethodException;

    Object reflectMethod(String methodName, Class[] params, Object[] paramValues) throws NoSuchMethodException;
}