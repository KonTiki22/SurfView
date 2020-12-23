package com.example.surfview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Square {
    int x, y;
    int side;
    Canvas canvas;
    Paint paint = new Paint();
    public Square(int x, int y, int side, Canvas canvas) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.canvas = canvas;
        paint.setColor(Color.BLACK);
        canvas.drawRect(x, y, x + side, y + side, paint);
    }

    void update() {
        canvas.drawRect(x, y, x + side, y + side, paint);
    }
    void squareCollide(Ball b) {
        double pvx = b.vx, pvy = b.vy;
        if( y < b.y && b.y < y + side && (b.x + b.radius > x && b.x < x || b.x - b.radius < x + side && b.x > x + side)) {
            b.x = b.x + b.radius > x && b.x < x ? x - b.radius : x + side + b.radius;
            b.vx = -b.vx;
        }
        if( x < b.x && b.x < x + side && (b.y + b.radius > y && b.y < y || b.y - b.radius < y + side && b.y > y + side)) {
            b.y = b.y + b.radius > y && b.y < y? y - b.radius : y + side + b.radius;
            b.vy = -b.vy;
        }
        if(pvx != b.vx || pvy != b.vy) {
            b.changeColor();
        }
    }
}
