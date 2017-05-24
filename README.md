### Android自定义View实现水波纹效果

[TOC]

效果图

![enter image description here](http://chuantu.biz/t5/93/1495614694x2728309609.gif)

实现思路

根据效果分析，首先利用Path的 `rQuadTo(float dx1, float dy1, float dx2, float dy2)`先画出静止的波纹，然后添加一个属性动画，利用动画改变path相应的坐标，实现波纹的移动。[path相关类容](https://developer.android.com/reference/android/graphics/Path.html)

具体实现

1. 画出静止的贝塞尔曲线

	效果图
![enter image description here](http://chuantu.biz/t5/93/1495615454x2728309609.png)

```
        mPath.moveTo(-mItemWaveLength , 300 );//
        int halfWaveLen = mItemWaveLength / 2;//波长的半径
        //左右都多移动了一个波 方便左右移动
        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            mPath.rQuadTo(halfWaveLen / 2, -50, halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen / 2, 50, halfWaveLen, 0);
        }
        canvas.drawPath(mPath, mPaint);
```

画波纹的时候左边和右边分别移动一个波纹的宽度，方便波纹移动。

2. 闭合Path路径

	效果图
![enter image description here](http://chuantu.biz/t5/93/1495615743x2728309609.png)    

```
        //闭合曲线
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
```

3. 添加动画

	添加动画实现左右和上下都移动
	
```
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
```
在添加一个方法重置动画

```
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
```
总结

其实这个动画也不难，但是首先得先了解Path相关的API的用法，了解了简单了，所以写下实现的过程，以后自己好复习。

