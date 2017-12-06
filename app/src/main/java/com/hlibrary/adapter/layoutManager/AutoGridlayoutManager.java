package com.hlibrary.adapter.layoutManager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by linwenhui on 2015/9/22.
 */
public class AutoGridlayoutManager extends GridLayoutManager {

    private int dividerHeigth;

    public AutoGridlayoutManager(Context context, int spanCount) {
        this(context, spanCount, 0);
    }

    public AutoGridlayoutManager(Context context, int spanCount, int dividerHeigth) {
        super(context, spanCount);
        this.dividerHeigth = dividerHeigth;
    }

    public AutoGridlayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        this(context, spanCount, orientation, reverseLayout, 0);
    }

    public AutoGridlayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout, int dividerHeigth) {
        super(context, spanCount, orientation, reverseLayout);
        this.dividerHeigth = dividerHeigth;
    }

    public void setDividerHeigth(int dividerHeigth) {
        this.dividerHeigth = dividerHeigth;
    }



    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
        if (state.getItemCount() > 0) {
            View view = recycler.getViewForPosition(0);
            if (view != null) {
                view.measure(widthSpec, heightSpec);
                int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                int measuredHeight = view.getMeasuredHeight();
                if (getOrientation() == VERTICAL) {
                    int spanNum = state.getItemCount() / getSpanCount();
                    int lastNum = state.getItemCount() % getSpanCount();
                    if (lastNum > 0)
                        spanNum++;
                    measuredHeight = spanNum * measuredHeight + (spanNum) * dividerHeigth;
                } else {
                    int spanNum = getSpanCount();
                    measuredHeight = spanNum * measuredHeight + (spanNum) * dividerHeigth;
                }
                setMeasuredDimension(measuredWidth, measuredHeight);
            }
        } else {
            setMeasuredDimension(0, 0);
        }
    }
}
