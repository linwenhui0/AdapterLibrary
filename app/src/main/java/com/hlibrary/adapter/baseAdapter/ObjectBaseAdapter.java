package com.hlibrary.adapter.baseAdapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hlibrary.adapter.IListInterface;
import com.hlibrary.adapter.SimpleList;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 * @updatedate 2017-8-16
 */
public class ObjectBaseAdapter<T> extends BaseAdapter {

    protected List<T> data = new ArrayList<>();
    private IListInterface<T> mListInterface;
    protected final int mItemLayoutId, variableId;
    protected LayoutInflater inflater;

    public ObjectBaseAdapter(Context context, int mItemLayoutId, int variableId) {
        this.mItemLayoutId = mItemLayoutId;
        this.variableId = variableId;
        inflater = LayoutInflater.from(context.getApplicationContext());
        mListInterface = new SimpleList<>(data);
    }

    public IListInterface<T> getListInterface() {
        return mListInterface;
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public T getItem(int position) {
        if (position < getCount()) {
            return this.data.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding adapterBinding = null;
        if (convertView == null) {
            adapterBinding = DataBindingUtil.inflate(inflater, mItemLayoutId, parent, false);
            if (adapterBinding != null) {
                convertView = adapterBinding.getRoot();
                convertView.setTag(adapterBinding);
            } else {
                convertView = inflater.inflate(mItemLayoutId, parent, false);
            }
        } else {
            Object tag = convertView.getTag();
            if (tag instanceof ViewDataBinding)
                adapterBinding = (ViewDataBinding) convertView.getTag();
        }
        T vo = getItem(position);
        if (adapterBinding != null) {
            adapterBinding.setVariable(variableId, vo);
            adapterBinding.executePendingBindings();
        }
        return convertView;
    }

}
