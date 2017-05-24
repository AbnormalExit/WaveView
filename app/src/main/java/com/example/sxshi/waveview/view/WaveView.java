package com.example.sxshi.waveview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.sxshi.waveview.R;

/**
 * Created by sxshi on 2017/5/24.
 */

public class WaveView extends View {
    private int defaultSize = 500;//控件默认高度
    private Paint mPaint;
    private int mAlpha = 50;// 透明度
    private Path mPath;
    private int mItemWaveLength = 300;//单个波纹的长度
    private int dx, dy;
    private ValueAnimator animator;
    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * init
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(1f);
        mPaint.setAlpha(mAlpha);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        setBackgroundColor(getResources().getColor(R.color.ligthColor));//填充背景色
        mPath.reset();
        mPath.moveTo(-mItemWaveLength + dx, getHeight() + dy);//将起点移动到控件底部
        dy -= 1;
        int halfWaveLen = mItemWaveLength / 2;//波长的半径
        //左右都多移动了一个波 方便左右移动
        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            mPath.rQuadTo(halfWaveLen / 2, -50, halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen / 2, 50, halfWaveLen, 0);
        }
        //闭合曲线
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasure(widthMeasureSpec);
        int height = getMeasure(heightMeasureSpec);
        //保证画出来的是正方形
        if (width < height) {
            setMeasuredDimension(width, width);
        } else {
            setMeasuredDimension(height, height);
        }

    }

    /**
     * 测量布局
     *
     * @param measureSpec
     * @return
     */
    public int getMeasure(int measureSpec) {
        int resultSize = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            resultSize = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            resultSize = Math.min(defaultSize, size);
        } else if (mode == MeasureSpec.UNSPECIFIED) {
            resultSize = size;
        }
        return resultSize;
    }

    /**
     * 开始动画
     */
    public void startAnim() {
        animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    /**
     * 重置path
     */
    public void reset(){
        if (animator!=null&&animator.isRunning()){
            animator.cancel();
        }
        mPath.reset();
        dx=0;
        dy=0;
    }
}
