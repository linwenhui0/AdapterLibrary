package com.hlibrary.adapter.recyclerAdapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hlibrary.adapter.IListInterface;
import com.hlibrary.adapter.SimpleList;
import com.hlibrary.adapter.model.RecylerViewHolder;
import com.hlibrary.util.Logger;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public abstract class ObjectBaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecylerViewHolder> {
    private final static String TAG = "ObjectBaseRecyclerAdapter";
    protected List<T> data = new ArrayList<>();
    private LayoutInflater inflater;
    protected OnItemClickListener<T> itemClickListener;
    private EventImp<T, ObjectBaseRecyclerAdapter> eventImp;

    protected final int layoutId;
    protected IListInterface<T> listInterface;

    public ObjectBaseRecyclerAdapter(Context context, int layoutId) {
        inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
        listInterface = new SimpleList<>(data, this, ObjectBaseRecyclerAdapter.class);
        eventImp = new EventImp<T, ObjectBaseRecyclerAdapter>(this) {
            @Override
            public T getEventItem(int pos) {
                return getItem(pos);
            }
        };
    }

    public IListInterface<T> getListInterface() {
        return listInterface;
    }

    public T getItem(int position) {
        try {
            return this.data.get(position);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    @Override
    public RecylerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        RecylerViewHolder holder;
        if (binding != null) {
            holder = new RecylerViewHolder(binding.getRoot());
            holder.setBinding(binding);
        } else {
            View v = inflater.inflate(viewType, parent, false);
            holder = new RecylerViewHolder(v);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecylerViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(eventImp);
        ViewDataBinding binding = holder.getBinding();
        if (binding != null) {
            holder.getBinding().setVariable(getVariableId(), getItem(position));
            holder.getBinding().executePendingBindings();
        }
    }

    public abstract int getVariableId();

    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }

    public abstract class EventImp<T, H extends ObjectBaseRecyclerAdapter> implements View.OnClickListener, OnItemClickListener<T> {

        protected WeakReference<H> adapterWeakReference;

        public EventImp(H adapter) {
            adapterWeakReference = new WeakReference<H>(adapter);
        }

        @Override
        public void onClick(View v) {
            Object obj = v.getTag();
            if (obj instanceof Integer) {
                int pos = (Integer) obj;
                T bean = getEventItem(pos);
                onItemClick(v, pos, bean);
            }
        }

        public abstract T getEventItem(int pos);

        @Override
        public void onItemClick(View v, int postion, T bean) {
            Logger.getInstance().i(TAG, " === onItemClick === position " + postion);
            if (adapterWeakReference != null) {
                H adapter = adapterWeakReference.get();
                if (adapter != null) {
                    OnItemClickListener<T> itemClickListener = adapter.itemClickListener;
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, postion, bean);
                    }
                }
            }

        }
    }

}


