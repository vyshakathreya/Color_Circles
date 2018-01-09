package edu.sdsu.vyshak.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;




/**
 * Created by vysha on 2/18/2017.
 */

public class ClassDemoView extends View implements View.OnTouchListener {
    private static final String TAG ="Demo View" ;
    private static Paint black;
    private static String Option="Draw";
    int i;
    Canvas canvas;
    static {
        black = new Paint();
        black.setColor(Color.BLACK);
    }
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private float xDifference;
    private float yDifference;
    private float radius;
    private float canvasWidth;
    private float canvasHeight;
    private static Paint colorChosen=black;
    VelocityTracker velocity;
    private float checkX;
    private float checkY;
    private float checkRadius;
    private float xVelocity;
    private float yVelocity;

    ArrayList<Circle> points = new ArrayList<Circle>();
    Circle circle = new Circle();
    Timer everySecond = new Timer();

    public ClassDemoView(Context context, AttributeSet xmlAttributes) {
        super(context, xmlAttributes);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        switch (actionCode) {
            case MotionEvent.ACTION_DOWN:
                return handleActionDown(event);
            case MotionEvent.ACTION_MOVE:
                return handleActionMove(event);
            case MotionEvent.ACTION_UP:
                return handleActionUp(event);
        }
        return false;
    }

    private boolean handleActionDown(MotionEvent event) {
        startX = event.getX();
        startY = event.getY();

        if(Option.equals("Draw")) {
            Log.i(TAG, "handleActionDown: drawing");
        }

        if(Option.equals("Move")) {
            velocity = VelocityTracker.obtain();
            velocity.addMovement(event);
            Log.i(TAG, "handleActionDown: check velocity");
            OnMove();
        }
        return true;
    }

    private boolean handleActionMove(MotionEvent event) {
        endX = event.getX();
        endY = event.getY();
        xDifference=(startX-endX)*(startX-endX);
        yDifference=(startY-endY)*(startY-endY);
        if(Option.equals("Move")) velocity.addMovement(event);
        radius= (float) Math.sqrt(xDifference+yDifference);
        if(radius>startX)
            radius=startX;
        if(radius>startY)
            radius=startY;
        if(radius>(canvasWidth-startX))
            radius=canvasWidth-startX;
        if(radius>(canvasHeight-startY))
            radius=canvasHeight-startY;
        invalidate();
        return true;
    }

    private boolean handleActionUp(MotionEvent event){
        if(Option.equals("Move")) {
            velocity.computeCurrentVelocity(200);
            xVelocity = velocity.getXVelocity();
            yVelocity = velocity.getYVelocity();
            Log.i("rew", "X vel " + velocity.getXVelocity() + " Y vel " + velocity.getYVelocity());
            velocity.recycle();
            velocity = null;
        }
        if(Option.equals("Draw")) {
            canvas.drawCircle(startX, startY, radius, getColorChosen());
            Circle circle = new Circle();
            circle.setCircleStartX(startX);
            circle.setCircleStartY(startY);
            circle.setCircleColor(getColorChosen());
            circle.setCircleRadius(radius);
            points.add(circle);
        }
        if(Option.equals("Delete")){
            Log.i(TAG, "handleActionDown: calling Delete");
            OnDelete();
        }
        Log.i(TAG,"array length"+points.size());
        return true;
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.YELLOW);
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();
        if (Option.equals("Draw")) {
            for (Circle each : points) {
                canvas.drawCircle(each.getCircleStartX(), each.getCircleStartY(), each.getCircleRadius(), each.getCircleColor());
                each.setyDistance(0);
                each.setxDistance(0);
            }
        }

        if(Option.equals("Delete")) {
            for (Circle each : points) {
                each.setyDistance(0);
                each.setxDistance(0);
                canvas.drawCircle(each.getCircleStartX(), each.getCircleStartY(), each.getCircleRadius(), each.getCircleColor());
            }
        }

        for (Circle each : points) {
            if(Option.equals("Move")){
                OnMove();
                canvas.drawCircle(each.getCircleStartX(), each.getCircleStartY(), each.getCircleRadius(), each.getCircleColor());
                postInvalidateDelayed(200);
            }
        }
    }

    private void OnMove() {
        for (int i = 0; i < points.size(); i++) {
            final Circle item = points.get(i);
            checkX = item.getCircleStartX();
            checkY = item.getCircleStartY();
            checkRadius = item.getCircleRadius();
            float xDiff = (startX - checkX) * (startX - checkX);
            float yDiff = (startY - checkY) * (startY - checkY);
            float rad = (float) Math.sqrt(xDiff + yDiff);

            if (rad <= checkRadius  && item.getxDistance() == 0 && item.getyDistance() == 0) {
                // Checking, to let circle continue with its preset velocity when moved back to move mode
                item.setxDistance(xVelocity/200);
                item.setyDistance(yVelocity/200);
            }
            item.setCircleStartX(item.getCircleStartX() + item.getxDistance());
            item.setCircleStartY(item.getCircleStartY() + item.getyDistance());

            if (item.getCircleStartX() >= (canvasWidth - item.getCircleRadius())) {
                item.setCircleStartX(canvasWidth- item.getCircleRadius());
                item.setxDistance(item.getxDistance() * -1);
                Log.i(TAG, "OnMove: "+item.getCircleStartX());
            }

            if (item.getCircleStartX() <= item.getCircleRadius()) {
                item.setCircleStartX(item.getCircleRadius());
                item.setxDistance(item.getxDistance() * -1);
                Log.i(TAG, "OnMove: "+item.getCircleStartX());
            }

            if (item.getCircleStartY() >= (canvasHeight - item.getCircleRadius())) {
                item.setCircleStartY(canvasHeight - item.getCircleRadius());
                item.setyDistance(item.getyDistance() * -1);
                Log.i(TAG, "OnMove: "+item.getCircleStartY());
            }

            if (item.getCircleStartY() <= item.getCircleRadius()) {
                item.setCircleStartY(item.getCircleRadius());
                item.setyDistance(item.getyDistance() * -1);
                Log.i(TAG, "OnMove: "+item.getCircleStartY());
            }
        }
    }

    public void OnDelete(){
        Log.i(TAG, "OnDelete: inside" );
        if(points != null) {
            for (int i=0; i < points.size(); i++) {
                Circle item = points.get(i);
                checkX = item.getCircleStartX();
                checkY = item.getCircleStartY();
                checkRadius = item.getCircleRadius();
                float xDiff=(startX-checkX)*(startX-checkX);
                float yDiff=(startY-checkY)*(startY-checkY);
                float rad= (float) Math.sqrt(xDiff+yDiff);

                //If the user moves our of the circle after selection, consider that as cancel operation.
                if((radius > checkRadius)) {
                    invalidate();
                }else{
                    if(rad <= checkRadius ) {
                        points.remove(i);
                        Log.i(TAG, "OnDelete: " + points.size());
                        invalidate();
                    }
                }
            }
        }
    }

    public static Paint getColorChosen() {
        return colorChosen;
    }

    public static void setColorChosen(Paint colorChosen) {
        ClassDemoView.colorChosen = colorChosen;
    }

    public static String getOption() {
        return Option;
    }

    public static void setOption(String option) {
        Option = option;
    }
}
