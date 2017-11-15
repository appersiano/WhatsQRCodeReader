package com.appersiano.whatsqrcode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.IntegerRes;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alessandro.persiano on 10/08/2017.
 */

public class FocusView extends View {
    public static final int SCALE_UNIT_TARGET_MIDDLE_LINE = 4;
    private Paint mSemiBlackPaint;
    private Path mPath = new Path();
    private PointF[] points;
    private Paint paint;
    private RectF targetRect;
    private Paint targetPaint;
    private Paint scanLine;
    private float greenAX;
    private float greenAY;
    private float greenBY;
    private boolean invert;


    public FocusView(Context context) {
        super(context);
        initPaints();
    }

    public FocusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaints();
    }

    public FocusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    private void initPaints() {
        targetRect = new RectF();

        mSemiBlackPaint = new Paint();
        mSemiBlackPaint.setColor(Color.TRANSPARENT);
        mSemiBlackPaint.setStrokeWidth(10);

        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);

        scanLine = new Paint();
        scanLine.setStrokeWidth(10);
        scanLine.setColor(Color.GREEN);
        scanLine.setStyle(Paint.Style.STROKE);

        targetPaint = new Paint();
        targetPaint.setStrokeWidth(10);
        targetPaint.setColor(Color.GRAY);
        targetPaint.setStyle(Paint.Style.STROKE);

    }

    public void setScanLineColor(@IntegerRes int color) {
        scanLine.setColor(color);
    }

    public void setTargetColor(int color) {
        targetPaint.setColor(color);
        paint.setColor(color);
    }

    public void setPoints(PointF[] points) {
        this.points = points;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        float unitX = getWidth() / 4;
        greenAX = getWidth() / 4;
        greenAY = getHeight() / 2 - unitX;

        greenBY = getHeight() / 2 - unitX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        final int scaleUnit = 6;

        float unitX = canvas.getWidth() / scaleUnit;

        float widthSize = unitX * (scaleUnit - 2);
        //float unitY = canvas.getHeight() / 4;

        float pointAx = unitX;
        float pointBx = unitX * (scaleUnit - 1);

        float pointAy = canvas.getHeight() / 2 - (widthSize / 2);
        float pointBy = canvas.getHeight() / 2 + (widthSize / 2);


        //Square
        targetRect.set(pointAx, pointAy, pointBx, pointBy);
        canvas.drawRect(targetRect, targetPaint);

        //draw lines
        drawLines(canvas, unitX, widthSize);


        //target line position logic
        if (invert) {
            greenAX = unitX;
            greenAY = greenAY - 5;

            greenBY = greenBY - 5;

            if (greenAY <= pointAy) {
                invert = false;
            }

        } else {
            greenAX = unitX;
            greenAY = greenAY + 5;

            greenBY = greenBY + 5;

            if (greenAY >= pointBy) {
                invert = true;
            }
        }


        //draw animate line
        canvas.drawLine(greenAX, greenAY, canvas.getWidth() - unitX, greenBY, scanLine);


        mPath.addRect(targetRect, Path.Direction.CW);
        mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);

        canvas.drawPath(mPath, mSemiBlackPaint);
        canvas.clipPath(mPath);
        canvas.drawColor(Color.parseColor("#A6000000"));
        ///////////////////


        if (points != null) {
            for (PointF pointF : points) {
                canvas.drawCircle(pointF.x, pointF.y, 10, paint);
            }
        }
    }

    private void drawLines(Canvas canvas, float unitX,float widthSize) {
        float halfScreenX = canvas.getWidth() / 2;
        float halfScreenY = canvas.getHeight() / 2;

        //top line
        float linePointAx = halfScreenX; //ok sempre
        float linePointAy = halfScreenY - (widthSize / 2) - (unitX / 2);

        float linePointBx = halfScreenX;
        float linePointBy = linePointAy + unitX;

        canvas.drawLine(linePointAx, linePointAy, linePointBx, linePointBy, paint);

        //bottom line
        linePointAx = halfScreenX;
        linePointAy = halfScreenY + (widthSize / 2) - (unitX / 2);

        linePointBx = halfScreenX;
        linePointBy = linePointAy + unitX;

        canvas.drawLine(linePointAx, linePointAy, linePointBx, linePointBy, paint);

        //left line
        linePointAx = unitX - (unitX / 2);
        linePointAy = halfScreenY;

        linePointBx = unitX + (unitX / 2);
        linePointBy = halfScreenY;

        canvas.drawLine(linePointAx, linePointAy, linePointBx, linePointBy, paint);

        //right line
        linePointAx = canvas.getWidth() - (unitX / 2);
        linePointAy = halfScreenY;

        linePointBx = canvas.getWidth() - unitX -  (unitX / 2);
        linePointBy = halfScreenY;

        canvas.drawLine(linePointAx, linePointAy, linePointBx, linePointBy, paint);
    }

    public void startAnimation() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
            }
        }, 0, 10);
    }
}