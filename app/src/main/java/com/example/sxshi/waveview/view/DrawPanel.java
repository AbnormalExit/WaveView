package com.example.sxshi.waveview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by sxshi on 2017/5/24.
 */

public class DrawPanel extends View {
    private Paint paint;
    private Path path = new Path();
    private float mPreX, mPreY;

    public DrawPanel(Context context) {
        this(context, null);
    }

    public DrawPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setStrokeWidth(5f);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //1.使用Path.lineTo(float x,float y)实现
/*        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                return true;//拦截事件
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                postInvalidate();//重新绘制
                break;
            default:
                break;
        }*/
        //使用Path.quadTo();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX + event.getX()) / 2;
                float endY = (mPreY + event.getY()) / 2;
                path.quadTo(mPreX, mPreY, endX, endY);
                mPreY = event.getY();
                mPreX = event.getX();
                invalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 重置路徑
     */
    public void reset() {
        path.reset();
//        invalidate();
        postInvalidate();
    }
}
