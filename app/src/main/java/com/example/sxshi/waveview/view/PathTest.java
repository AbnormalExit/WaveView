package com.example.sxshi.waveview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sxshi on 2017/5/24.
 * 对path相关研究
 */

public class PathTest extends View {
    public PathTest(Context context) {
        this(context,null);
    }

    public PathTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        paint.setStrokeWidth(5);//设置画笔宽度

        Path path = new Path();

        path.moveTo(10, 10); //设定起始点
        path.lineTo(10, 100);//第一条直线的终点，也是第二条直线的起点
        path.lineTo(300, 100);//画第二条直线
        path.lineTo(500, 100);//第三条直线
        path.close();//闭环
        canvas.drawPath(path, paint);
        path.reset();
        //先创建两个大小一样的路径
    //第一个逆向生成
        Path CCWRectpath = new Path();
        RectF rect1 =  new RectF(200, 200, 440, 400);
        CCWRectpath.addRect(rect1, Path.Direction.CCW);

    //第二个顺向生成
        Path CWRectpath = new Path();
        RectF rect2 =  new RectF(500, 200, 740, 400);
        CWRectpath.addRect(rect2, Path.Direction.CW);

    //先画出这两个路径
        canvas.drawPath(CCWRectpath, paint);
        canvas.drawPath(CWRectpath, paint);
        //依据路径写出文字
        String text="窗前明月光，疑是地上霜";
        paint.setColor(Color.GREEN);
        paint.setTextSize(35);
        canvas.drawTextOnPath(text, CCWRectpath, 0, 18, paint);//逆时针生成
        canvas.drawTextOnPath(text, CWRectpath, 0, 18, paint);//顺时针生成

        //圆角矩形
        paint.setColor(Color.RED);
        Path roundRectPath = new Path();
        RectF rectf1 =  new RectF(50, 500, 250, 700);
        roundRectPath.addRoundRect(rectf1, 10, 10 , Path.Direction.CCW);

        RectF rectf2 =  new RectF(290, 500, 480, 700);
        float radii[] ={10,15,20,25,30,35,40,45};
        roundRectPath.addRoundRect(rectf2, radii, Path.Direction.CCW);

        canvas.drawPath(roundRectPath, paint);
        path.reset();
        //画圆形
        path.addCircle(200, 850, 100, Path.Direction.CCW);
        canvas.drawPath(path, paint);
        path.reset();
        //画椭圆
        RectF rect =  new RectF(350, 750, 500, 900);
        path.addOval(rect, Path.Direction.CCW);
        canvas.drawPath(path, paint);
        //画出圆弧
        path.reset();
        RectF arcRectf =  new RectF(550, 750, 700, 900);
        path.addArc(arcRectf, 0,90);
        canvas.drawPath(path, paint);
    }
}
