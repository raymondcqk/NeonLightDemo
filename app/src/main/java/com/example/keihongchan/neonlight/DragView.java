package com.example.keihongchan.neonlight;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 拖拽控件
 *
 * @author keihong.chan
 * @time 2017/10/25 下午8:08
 */

public class DragView extends android.support.v7.widget.AppCompatImageView {

    private int lastX;
    private int lastY;

    public DragView(Context context) {
        super(context);
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                int newX = (int) event.getRawX();
                int newY = (int) event.getRawY();
                int dx = newX - lastX;
                int dy = newY - lastY;

                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = left + getWidth();
                int bottom = top + getHeight();

                layout(left, top, right, bottom);

                lastX = newX;
                lastY = newY;
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }


        return true;
    }



}
