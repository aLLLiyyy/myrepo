package com.xiaoming.weatherview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * date 2021/1/21 0021 上午 1:48
 * author ming
 **/
public class CWeatherView extends View {
    private int width,height,ws,hs,pw,ph;
    private int ratio=0;
    private List<Integer> data=new ArrayList<Integer>();
    public CWeatherView(Context context) {
        super(context);
    }

    public CWeatherView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CWeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CWeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setData(List<Integer> data){
        this.data=data;
        initSketchup();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width=getMeasuredWidth();
        height=getMeasuredHeight();
        List<Integer> data=new ArrayList<>();
        data.add(8);
        data.add(14);
        data.add(10);
        data.add(5);
        data.add(11);
        setData(data);
    }
    private void initSketchup(){
        ws=data.size()+1;
        hs=0;
        for (int i:data){
            if(i>hs)
                hs=i;
        }
        hs+=2;

        pw=width/ws;
        ph=height/hs;

        ratio=width/(3*ws);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path=new Path();
        int len=data.size();
        int y1=0;
        int y2=0;
        Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setStrokeWidth(10);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setColor(Color.RED);
        for (int i=0;i<len;i++){
            int w=i+1;
            int x=w*pw;
            int y=height-data.get(i)*ph;
            if (i==0){
                path.moveTo(x,y);
                y1=y;
            }else if (i<len-1){
                int grad=(data.get(i+1)-data.get(i-1))/pw;
                int x1=i*pw+ratio;   //左边x
                int x2=x-ratio;
                y2=y+ratio*grad;
                path.cubicTo(x1,y1,x2,y2,x,y);
                y1=y-ratio*grad;
            }else{
                int x1=i*pw+ratio;   //左边x
                int x2=x-ratio;
                y2=y;
                path.cubicTo(x1,y1,x2,y2,x,y);
            }
            canvas.drawPoint(x,y,p);
        }
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        canvas.drawPath(path,paint);

    }
}
