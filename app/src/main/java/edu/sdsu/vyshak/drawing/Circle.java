package edu.sdsu.vyshak.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by vysha on 2/22/2017.
 */

public class Circle {
    private static final String TAG = "circleView";
    float circleStartX;
    float circleStartY;
    float circleRadius;
    Paint circleColor;
    float xDistance = 0;
    float yDistance = 0;

    public float getxDistance() {
        return xDistance;
    }

    public void setxDistance(float xDistance) {
        this.xDistance = xDistance;
    }

    public float getyDistance() {
        return yDistance;
    }

    public void setyDistance(float yDistance) {
        this.yDistance = yDistance;
    }

    public float getCircleStartX() {
        return circleStartX;
    }

    public void setCircleStartX(float circleStartX) {
        this.circleStartX = circleStartX;
    }

    public float getCircleStartY() {
        return circleStartY;
    }

    public void setCircleStartY(float circleStartY) {
        this.circleStartY = circleStartY;
    }

    public float getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public Paint getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(Paint circleColor) {
        this.circleColor = circleColor;
    }

}