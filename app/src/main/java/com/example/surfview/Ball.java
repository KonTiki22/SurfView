package com.example.surfview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Arrays;
import java.util.Random;

public class Ball {
    double x, y;
    double vx, vy, v;
    double radius;
    Integer[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN, Color.BLACK};
    int color;
    Random r = new Random();
    Canvas canvas;
    Paint paint = new Paint();
    public Ball(float x, float y, float vx, float vy, int radius, Canvas canvas) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.v = Math.sqrt(vx*vx + vy*vy);
        this.radius = radius;
        this.canvas = canvas;
        color = colors[r.nextInt(colors.length)];
        paint.setColor(color);
        canvas.drawCircle(x, y, radius, paint);
    }
    void update() {
        x += vx;
        y += vy;
        canvas.drawCircle((float) x,(float) y, (int)radius, paint);
    }

    void changeColor() {
        int index = Arrays.asList(colors).indexOf(paint.getColor());
        color = colors[(index + 1) % colors.length];
        paint.setColor(color);
    }

    void boxCollide() {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        double pvx = vx, pvy = vy;

        if(x < radius || x > width - radius) {
            x = x < radius? radius : width - radius;
            vx = -vx;
        }
        if(y < radius || y > height - radius) {
            y = y < radius? radius : height - radius;
            vy = -vy;
        }

        if(pvx != vx || pvy != vy) {
            //changeColor();
        }
    }



    static void ballsCollide(Ball b1, Ball b2) {
        double dx = b2.x - b1.x;
        double dy = b2.y - b1.y;
        double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        if(distance < b1.radius + b2.radius) {
            double nX = dx / distance;
            double nY = dy / distance;
            double midX = (b1.x + b2.x) / 2;
            double midY = (b1.y + b2.y) / 2;
            b1.x = midX - nX * b1.radius;
            b1.y = midY - nY * b1.radius;
            b2.x = midX + nX * b2.radius;
            b2.y = midY + nY * b2.radius;
            double dVector = (b1.vx - b2.vx) * nX;
            dVector += (b1.vy - b2.vy) * nY;
            double dvx = dVector * nX;
            double dvy = dVector * nY;
            b1.vx -= dvx;
            b1.vy -= dvy;
            b2.vx += dvx;
            b2.vy += dvy;

        }

/*
def particle_collide(b1, b2):
    size = b1.shapesize()[0] * 10
    distance = ((b1.x - b2.x) ** 2 + (b1.y - b2.y) ** 2) ** 0.5
    afi_1 = math.atan2(b2.y - b1.y, b2.x - b1.x)
    an_b1 =  math.atan2(b1.vy, b1.vx)
    an_b2 =  math.atan2(b2.vy, b2.vx)
    if distance <= size * 2:
        while True:
            distance = ((b1.x - b2.x) ** 2 + (b1.y - b2.y) ** 2) ** 0.5
            if distance > size * 2:
                break
            b1.x, b2.x = check(b1.x, b2.x)
            b1.y, b2.y = check(b1.y, b2.y)
        if distance == 0:
            distance = 0.1
        print("Particles collide")
        b1.vx, b1.vy = b1.v*math.cos(an_b1-afi_1), b1.v*math.sin(an_b1-afi_1)
        b2.vx, b2.vy = b2.v*math.cos(an_b2-afi_1), b2.v*math.sin(an_b2-afi_1)
        b1.vx, b2.vx = b2.vx, b1.vx
        b1.vx, b1.vy = b1.vx*math.cos(afi_1)-b1.vy*math.sin(afi_1), b1.vx*math.sin(afi_1)+b1.vy*math.cos(afi_1)
        b2.vx, b2.vy = b2.vx*math.cos(afi_1)-b2.vy*math.sin(afi_1), b2.vx*math.sin(afi_1)+b2.vy*math.cos(afi_1)
* */

    }
}
