package com.wuyr.coffeedrawabletest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.wuyr.coffeedrawable.CoffeeDrawable;

/**
 * @author wuyr
 * @github https://github.com/wuyr/CoffeeHeader
 * @since 2019-07-02 下午2:32
 */
public class CoffeeHeader extends View implements RefreshHeader {

    private CoffeeDrawable mDrawable;
    private RefreshState state = RefreshState.None;
    private int coffeeColor;
    private int cupBodyColor;
    private int cupBottomColor;
    private int handColor;
    private int stickColor;
    private int cupRadius;

    public CoffeeHeader(Context context) {
        this(context, null);
    }

    public CoffeeHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoffeeHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CoffeeHeader, defStyleAttr, 0);
        coffeeColor = a.getColor(R.styleable.CoffeeHeader_coffeeColor, 0);
        cupBodyColor = a.getColor(R.styleable.CoffeeHeader_cupBodyColor, 0);
        cupBottomColor = a.getColor(R.styleable.CoffeeHeader_cupBottomColor, 0);
        handColor = a.getColor(R.styleable.CoffeeHeader_handColor, 0);
        stickColor = a.getColor(R.styleable.CoffeeHeader_stickColor, 0);
        cupRadius = a.getDimensionPixelSize(R.styleable.CoffeeHeader_cupRadius, 0);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDrawable.draw(canvas);
        if (state != RefreshState.None) {
            invalidate();
        }
    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        mDrawable = CoffeeDrawable.create(getMeasuredWidth(), getMeasuredHeight(),
                cupRadius > 0 ? cupRadius : getMeasuredWidth() / 15);
        if (coffeeColor != 0) {
            mDrawable.setCoffeeColor(coffeeColor);
        }
        if (cupBodyColor != 0) {
            mDrawable.setCupBodyColor(cupBodyColor);
        }
        if (cupBottomColor != 0) {
            mDrawable.setCupBottomColor(cupBottomColor);
        }
        if (handColor != 0) {
            mDrawable.setHandColor(handColor);
        }
        if (stickColor != 0) {
            mDrawable.setStickColor(stickColor);
        }
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        mDrawable.finish();
        return mDrawable.getFinishAnimationDuration();
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        mDrawable.start();
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        state = newState;
        if (newState == RefreshState.None) {
            mDrawable.reset();
        }
        invalidate();
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        if (isDragging || state == RefreshState.None || state == RefreshState.PullDownToRefresh || state == RefreshState.PullDownCanceled) {
            mDrawable.setProgress(percent <= .5F ? 0 : (percent - .5F) * 2);
        }
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }
}
