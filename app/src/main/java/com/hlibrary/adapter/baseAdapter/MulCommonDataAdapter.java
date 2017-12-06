package com.hlibrary.adapter.baseAdapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.hlibrary.adapter.model.AdapterVo;


/**
 * @author linwenhui
 * @date 2015/9/14.
 */
public abstract class MulCommonDataAdapter<T extends AdapterVo> extends ObjectBaseAdapter<T> {

    public MulCommonDataAdapter(Context context, int variableId) {
        super(context,0, variableId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T vo = getItem(position);
        ViewDataBinding adapterBinding;
        if (convertView == null) {
            adapterBinding = DataBindingUtil.inflate(inflater, vo.getLayoutResId(), parent, false);
            convertView = adapterBinding.getRoot();
            convertView.setTag(adapterBinding);
            convertView.setTag(99, vo);
        } else {
            adapterBinding = (ViewDataBinding) convertView.getTag();
            Object tagPos = convertView.getTag(99);
            try {
                T preVo = (T) tagPos;
                if (preVo != null && preVo.getLayoutResId() != vo.getLayoutResId()) {
                    adapterBinding = DataBindingUtil.inflate(inflater, vo.getLayoutResId(), parent, false);
                    convertView = adapterBinding.getRoot();
                    convertView.setTag(adapterBinding);
                    convertView.setTag(99, vo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        adapterBinding.setVariable(variableId, getItem(position));
        adapterBinding.executePendingBindings();
        return convertView;
    }

}
