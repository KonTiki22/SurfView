package com.example.surfview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Square {
    double x, y;
    double vx = 0, vy = 0;
    int side;
    Canvas canvas;
    Paint paint = new Paint();
    public Square(int x, int y, int side, Canvas canvas, int color) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.canvas = canvas;
        paint.setColor(color);
        canvas.drawRect(x, y, x + side, y + side, paint);
    }

    void update() {
        canvas.drawRect((float) x, (float) y, (float) x + side, (float) y + side, paint);
    }
    void squareCollide(Ball b) {
        int er = 5;
        double pvx = b.vx, pvy = b.vy;
        double tl = Math.sqrt(Math.pow(x - b.x, 2) + Math.pow(y - b.y, 2));
        double tr = Math.sqrt(Math.pow(x + side - b.x, 2) + Math.pow(y - b.y, 2));
        double bl = Math.sqrt(Math.pow(x - b.x, 2) + Math.pow(y + side - b.y, 2));
        double br = Math.sqrt(Math.pow(x + side - b.x, 2) + Math.pow(y + side - b.y, 2));

        if( y < b.y && b.y < y + side && (b.x + b.radius > x && b.x < x || b.x - b.radius < x + side && b.x > x + side)) {
            b.x = b.x + b.radius > x && b.x < x ? x - b.radius - er: x + side + b.radius + er;
            b.vx = -b.vx + vx;
            b.vy = b.vy + vy;
        }
        else if( x < b.x && b.x < x + side && (b.y + b.radius > y && b.y < y || b.y - b.radius < y + side && b.y > y + side)) {
            b.y = b.y + b.radius > y && b.y < y? y - b.radius - er: y + side + b.radius + er;
            b.vy = -b.vy + vy;
            b.vx = b.vx + vx;
        }
        else if(tl < b.radius || tr < b.radius || bl < b.radius || br < b.radius) {
            b.x = tl < b.radius || bl < b.radius? x - b.radius - er: x + side + b.radius + er ;
            b.y = tl < b.radius || tr < b.radius? y - b.radius - er: y + side + b.radius + er ;
            b.vx = -b.vx + vx;
            b.vy = -b.vy + vy;
        }
        if(pvx != b.vx || pvy != b.vy) {
            b.changeColor();
        }
    }
}
