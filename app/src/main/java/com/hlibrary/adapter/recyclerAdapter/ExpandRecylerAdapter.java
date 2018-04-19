package com.hlibrary.adapter.recyclerAdapter;

import android.content.Context;
import android.view.View;

import com.hlibrary.adapter.ExpandSimpleList;
import com.hlibrary.adapter.model.HeadFootAdatperVo;
import com.hlibrary.adapter.model.RecylerViewHolder;
import com.hlibrary.util.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by linwenhui on 2015/9/29.
 */
public abstract class ExpandRecylerAdapter<T, HVo extends HeadFootAdatperVo, FVo extends HeadFootAdatperVo> extends ObjectBaseRecyclerAdapter<T> {

    final static String TAG = "ExpandRecylerAdapter";

    private List<HVo> headLayoutIds;
    private List<FVo> footLayoutIds;
    private OnItemClickListener<HVo> onHeadItemClickListener;
    private OnItemClickListener<FVo> onFootItemClickListener;
    private ExpandEventImp<HVo> headEventImp;
    private ExpandEventImp<FVo> footEventImp;

    public ExpandRecylerAdapter(Context context, int layoutId) {
        super(context, layoutId);
        headLayoutIds = new ArrayList<>();
        footLayoutIds = new ArrayList<>();
        listInterface = new ExpandSimpleList<>(data, headLayoutIds, footLayoutIds);
        headEventImp = new ExpandEventImp<HVo>(this) {
            @Override
            public OnItemClickListener<HVo> getOnItemClickListener(ExpandRecylerAdapter adapter) {
                return adapter.onHeadItemClickListener;
            }

            @Override
            public HVo getEventItem(int pos) {
                return getHeadItem(pos);
            }
        };
        footEventImp = new ExpandEventImp<FVo>(this) {
            @Override
            public OnItemClickListener<FVo> getOnItemClickListener(ExpandRecylerAdapter adapter) {
                return adapter.onFootItemClickListener;
            }

            @Override
            public FVo getEventItem(int pos) {
                return getFootItem(pos);
            }
        };
    }

    public void setHeadLayoutIds(List<HVo> headLayoutIds) {
        this.headLayoutIds = headLayoutIds;
    }

    public void setFootLayoutIds(List<FVo> footLayoutIds) {
        this.footLayoutIds = footLayoutIds;
    }

    public void setOnHeadItemClickListener(OnItemClickListener<HVo> onHeadItemClickListener) {
        this.onHeadItemClickListener = onHeadItemClickListener;
    }

    public void setOnFootItemClickListener(OnItemClickListener<FVo> onFootItemClickListener) {
        this.onFootItemClickListener = onFootItemClickListener;
    }

    @Override
    public ExpandSimpleList<T, HVo, FVo> getListInterface() {
        return (ExpandSimpleList<T, HVo, FVo>) super.getListInterface();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + getHeadCount() + getFootCount();
    }

    public int getHeadCount() {
        return headLayoutIds.size();
    }

    public HVo getHeadItem(int postion) {
        return headLayoutIds.get(postion);
    }

    public int getFootCount() {
        return footLayoutIds.size();
    }

    public FVo getFootItem(int postion) {
        return footLayoutIds.get(postion);
    }

    @Override
    public T getItem(int position) {
        return super.getItem(position - getHeadCount());
    }

    @Override
    public final void onBindViewHolder(RecylerViewHolder holder, int position) {
        final int headLayoutNum = headLayoutIds.size();
        final int footLayoutNum = footLayoutIds.size();
        final int relativeFootPos = position - headLayoutNum - super.getItemCount();
        if (position >= 0 && position < headLayoutNum) {
            onBindHeadViewHolder(holder, position);
        } else if (relativeFootPos >= 0 && relativeFootPos < footLayoutNum) {
            onBindFootViewHolder(holder, relativeFootPos);
        } else {
            super.onBindViewHolder(holder, position - headLayoutNum);
            onBindViewHolderNew(holder, position - headLayoutNum);
        }
    }

    public void onBindViewHolderNew(RecylerViewHolder holder, int position) {

    }

    public abstract int getDefaultHeadVariableId();

    public void onBindHeadViewHolder(RecylerViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(headEventImp);
        if (getDefaultHeadVariableId() == 0) {
            final int headVariableId = getHeadItem(position).getVariableId();
            if (headVariableId > 0)
                holder.getBinding().setVariable(headVariableId, getHeadItem(position));
        } else {
            holder.getBinding().setVariable(getDefaultHeadVariableId(), getHeadItem(position));
        }
        holder.getBinding().executePendingBindings();
    }

    public abstract int getDefaultFootVariableId();

    public void onBindFootViewHolder(RecylerViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(footEventImp);
        if (getDefaultFootVariableId() == 0) {
            final int footVariableId = getFootItem(position).getVariableId();
            if (footVariableId > 0)
                holder.getBinding().setVariable(footVariableId, getFootItem(position));
        } else {
            holder.getBinding().setVariable(getDefaultFootVariableId(), getFootItem(position));
        }
        holder.getBinding().executePendingBindings();
    }

    @Override
    public final int getItemViewType(int position) {
        final int headLayoutNum = headLayoutIds.size();
        final int footLayoutNum = footLayoutIds.size();
        final int relativeFootPos = position - headLayoutNum - super.getItemCount();
        if (position >= 0 && position < headLayoutNum) {
            return getHeadItem(position).getLayoutResId();
        } else if (relativeFootPos >= 0 && relativeFootPos < footLayoutNum) {
            return getFootItem(relativeFootPos).getLayoutResId();
        } else {
            return getItemViewTypeNew(position);
        }
    }

    public int getItemViewTypeNew(int position) {
        return super.getItemViewType(position);
    }

    public abstract class ExpandEventImp<T extends HeadFootAdatperVo> extends EventImp<T, ExpandRecylerAdapter> {

        public ExpandEventImp(ExpandRecylerAdapter adapter) {
            super(adapter);
        }

        public abstract OnItemClickListener<T> getOnItemClickListener(ExpandRecylerAdapter adapter);

        @Override
        public final void onItemClick(View v, int postion, T bean) {
            Logger.getInstance().i(TAG, " === onItemClick === position " + postion);
            if (adapterWeakReference != null) {
                ExpandRecylerAdapter adapter = adapterWeakReference.get();
                if (adapter != null) {
                    OnItemClickListener<T> itemClickListener = getOnItemClickListener(adapter);
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, postion, bean);
                    }
                }
            }
        }
    }

}
