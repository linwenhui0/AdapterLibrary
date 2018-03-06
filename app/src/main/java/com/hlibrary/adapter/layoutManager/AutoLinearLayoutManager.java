package com.hlibrary.adapter.layoutManager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hlibrary.util.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by linwenhui on 2015/9/24.
 */
public class AutoLinearLayoutManager extends LinearLayoutManager {

    private static final String TAG = "AutoLinearLayoutManager";

    private int dividerHeigth;
    private int maxHeigth;
    private int maxItem;
    private int[] mMeasuredDimension = new int[2];
    private List<Integer> goneLineIndexs = new ArrayList<>();

    public AutoLinearLayoutManager(Context context) {
        this(context, VERTICAL, false, 0);
    }

    public AutoLinearLayoutManager(Context context, int dividerHeigth) {
        this(context, VERTICAL, false, dividerHeigth);
    }

    public AutoLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        this(context, orientation, reverseLayout, 0);
    }

    public AutoLinearLayoutManager(Context context, int orientation, boolean reverseLayout, int dividerHeigth) {
        super(context, orientation, reverseLayout);
        this.dividerHeigth = dividerHeigth;
    }

    public void setDividerHeigth(int dividerHeigth) {
        this.dividerHeigth = dividerHeigth;
    }

    public AutoLinearLayoutManager setMaxHeigth(int maxHeigth) {
        this.maxHeigth = maxHeigth;
        return this;
    }

    public AutoLinearLayoutManager setMaxItem(int maxItem) {
        this.maxItem = maxItem;
        return this;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        Logger.getInstance().i(TAG, "count:" + getItemCount() + " === heightSpec " + heightSpec);
        if (maxItem > 0 && maxItem < getItemCount()) {
            super.onMeasure(recycler, state, widthSpec, heightSpec);
            return;
        }
        final int widthMode = View.MeasureSpec.getMode(widthSpec);
        final int heightMode = View.MeasureSpec.getMode(heightSpec);
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);
        int width = 0;
        int height = 0;


        Logger.getInstance().i(TAG, "state:" + state.toString());
        int total = state.getItemCount();
        if (maxItem > 0)
            total = total > maxItem ? maxItem : total;

        for (int i = 0; i < total; i++) {

            try {
                measureScrapChild(recycler, i, widthSpec,
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        mMeasuredDimension);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            if (getOrientation() == HORIZONTAL) {
                width = width + mMeasuredDimension[0];
                height = Math.max(height, mMeasuredDimension[1]);
            } else {
                height = height + mMeasuredDimension[1];
                width = Math.max(width, mMeasuredDimension[0]);
            }
        }
        if (getOrientation() == HORIZONTAL) {
            width += (state.getItemCount() - goneLineIndexs.size()) * dividerHeigth;
        } else {
            height += (state.getItemCount() - goneLineIndexs.size()) * dividerHeigth;
        }

        if (maxHeigth != 0)
            height = height > maxHeigth ? maxHeigth : height;

        if (getOrientation() == HORIZONTAL) {
            switch (heightMode) {
                case View.MeasureSpec.EXACTLY:
                    width = widthSize;
                    break;
                case View.MeasureSpec.AT_MOST:
                    break;
                case View.MeasureSpec.UNSPECIFIED:
                    break;
            }

            setMeasuredDimension(width, heightSize);
        } else {
            switch (heightMode) {
                case View.MeasureSpec.EXACTLY:
                    height = heightSize;
                    break;
                case View.MeasureSpec.AT_MOST:
                    break;
                case View.MeasureSpec.UNSPECIFIED:
                    break;
            }

            setMeasuredDimension(widthSize, height);
        }
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec, int heightSpec, int[] measuredDimension) {
        View view = recycler.getViewForPosition(position);

        // For adding Item Decor Insets to view
//        super.measureChildWithMargins(view, 0, 0);

        if (view != null) {
            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
            int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                    getPaddingTop() + getPaddingBottom(), p.height);
            view.measure(widthSpec, childHeightSpec);
            measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
            measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
            recycler.recycleView(view);
        }
    }
}
