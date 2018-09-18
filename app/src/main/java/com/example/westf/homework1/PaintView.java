package com.example.westf.homework1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;

public class PaintView extends View {

    public LayoutParams params;
    private Path path = new Path();
    private Paint brush;
    private ArrayList<Path> paths;
    private ArrayList<Paint> brushes;
    int red, green, blue = 0;

    public PaintView(Context context, AttributeSet attr) {
        super(context, attr);
        this.init();
    }

    public void init() {
        path = new Path();
        this.paths = new ArrayList<Path>();
        this.brushes = new ArrayList<Paint>();
        this.createStroke();

        params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    private void createStroke() {

        // For each line that is drawn create a new path
        path = new Path();
        paths.add(path);

        // For each line that is drawn create a new brush
        brush = new Paint();
        brushes.add(brush);
        brush.setColor(Color.rgb(red, green, blue));
        brush.setAntiAlias(true);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(12);
    }

    public void setPaintColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void clear() {
        this.init();
        this.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xVal = event.getX();
        float yVal = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.createStroke();
                path.moveTo(xVal, yVal);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(xVal, yVal);
                break;
        }

        postInvalidate();
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < paths.size(); i++)
            canvas.drawPath(paths.get(i), brushes.get(i));
    }
}
