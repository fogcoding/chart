package com.fogchart.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;


import com.fogchart.R;
import com.fogchart.bean.point;

import java.util.ArrayList;

/**
 * Created by Andrew on 2016/11/15.
 */

public class cie1960Chart extends BaseChart {


    //说明：由于采用配景图作为图标，位置都是强行适应的，所以在使用的时候，尽量保证背景图片的完整性
    //经过测试，如果采用一屏的画面来显示本图标是没有问题的，各方面表现尚可
    //所以在使用的时候尽可能考虑到只在底部的窄小区域用来表现文字形式的数据
    //图标的情况说明，同CIE1931Chart

    private ArrayList<point> data;

    public cie1960Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public cie1960Chart(Context context) {
        super(context);
    }

    @Override
    public void drawableShape(Canvas canvas) {
    }

    @Override
    public void drawBackground(Canvas canvas) {
        Drawable drawable = getResources().getDrawable(R.drawable.cie_1960);
        drawable.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
        drawable.draw(canvas);

    }

    @Override
    public void drawCurve(Canvas canvas) {
        if (this.data == null) {
            return;
        }
        for (point p:this.data) {
            point point = transToPoint(canvas, p);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(dpToPx(3*getResources().getDisplayMetrics().density));
            paint.setColor(getResources().getColor(R.color.black));
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawCircle(point.getX_pixs(),point.getY_pixs(),5*getResources().getDisplayMetrics().density,paint);

            if (p.getDeclare() != null){
                canvas.drawText(p.getDeclare(),point.getX_pixs(),-5*getResources().getDisplayMetrics().density+point.getY_pixs(),paint);
            }
        }
    }

    @Override
    protected void scale(Canvas canvas) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        drawBackground(canvas);
        drawCurve(canvas);

    }



    public point transToPoint(Canvas canvas, point dataPoint){
        float width = canvas.getWidth();
        float height = canvas.getHeight();
        float x = (float) (width/100.0*(91.2-3.0));
        float y = (float) (height/100.0*(95.2-2.0));
        float perW = (float) (x /6.0);
        float perH = (float) (y /4.0);
        float finalx = (float) (width*3.0/100.0 + perW*dataPoint.getX_pixs()*10.0);
        float finaly = (float) (height*2.0/100.0 + perH*(4.0-dataPoint.getY_pixs()*10.0));
        return new point(finalx,finaly);
    }


    public void setData(ArrayList<point> data) {
        this.data = data;
        invalidate();
    }

    public ArrayList<point> getData() {
        return data;
    }
}
