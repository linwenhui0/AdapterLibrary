package com.hlibrary.adapter.itemDecoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by linwenhui on 2017/8/28.
 */
public class RecycleViewDivider extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private int mDividerHeight = 2;//分割线高度，默认为1px
    private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private boolean showLast = true;

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context
     * @param orientation 列表方向
     */
    public RecycleViewDivider(Context context, @IntRange(from = LinearLayoutManager.HORIZONTAL, to = LinearLayoutManager.VERTICAL) int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation 列表方向
     * @param drawableId  分割线图片
     */
    public RecycleViewDivider(Context context, int orientation, @DrawableRes int drawableId) {
        this(context, orientation);
        mDivider = ContextCompat.getDrawable(context, drawableId);
        if (mDivider != null) {
            mDividerHeight = mDivider.getIntrinsicHeight();
        }
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation   列表方向
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线资源文件
     */
    public RecycleViewDivider(Context context, int orientation, int dividerHeight, @DrawableRes int dividerColor) {
        this(context, orientation, dividerHeight, dividerColor, false);
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation      列表方向
     * @param dividerHeight    分割线高度
     * @param dividerColor     分割线颜色
     * @param isColorOrResouce true:颜色值,false:资源文件
     */
    public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor, boolean isColorOrResouce) {
        this(context, orientation);
        if (isColorOrResouce) {
            mDivider = new ColorDrawable(dividerColor);
        } else {
            mDivider = ContextCompat.getDrawable(context, dividerColor);
        }
        mDividerHeight = dividerHeight;
    }

    public RecycleViewDivider setShowLast(boolean showLast) {
        this.showLast = showLast;
        return this;
    }

    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mDividerHeight);
        } else {
            outRect.set(0, 0, mDividerHeight, 0);
        }
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom;
            if (!showLast && i == childSize - 1) {
                bottom = top;
            } else {
                bottom = top + mDividerHeight;
            }
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right;
            if (!showLast && i == childSize - 1) {
                right = left;
            } else {
                right = left + mDividerHeight;
            }
            if (showLast && mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
        }
    }

}
