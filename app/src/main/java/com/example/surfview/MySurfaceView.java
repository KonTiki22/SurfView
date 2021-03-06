package com.example.surfview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.Random;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    int touchX,  touchY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = (int)event.getX();
        touchY = (int)event.getY();

        return true;
    }

    class DrawThread extends Thread {
        Random r = new Random();
        Paint paint = new Paint();
        boolean runFlag = true;
        SurfaceHolder holder;
        Canvas canvas;
        int radius = 40;
        int numBalls = 5;
        int colorNum = 5;
        Ball[] balls = new Ball[numBalls];
        Integer[] colors = new Integer[] {Color.RED,  Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};
        public DrawThread(SurfaceHolder holder) {
            this.holder = holder;
        }
        @Override
        public void run() {
            super.run();
            canvas = holder.lockCanvas();
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            /*
            for(int i = 0; i < colorNum; i++ ) {
                int red = r.nextInt(255);
                int green = 255 - red;
                int blue = 255 % (green + red);
                int color = Color.HSVToColor(new float[]{(float)i/colorNum, 1, (float)0.5});
                colors[i] = color;
            }*/

            for(int i = 0; i < numBalls; i++ ) {
                radius = r.nextInt(20) + 40;
                int vx = r.nextInt(15) - 7;
                int vy = r.nextInt(15) - 7;
                int x = r.nextInt(width - (radius + vx + 2)*2) + (radius + vx + 2);
                int y = r.nextInt(height - (radius + vy + 2)*2) + (radius + vy + 2);
                balls[i] = new Ball(x, y, vx, vy,radius,canvas, colors);
            }
            int side = 200;
            int sqColor = Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            Square square = new Square((int) (width-side)/2, (int) (height-side)/2, side, canvas, sqColor);
            holder.unlockCanvasAndPost(canvas);
            boolean win = false;
            while (runFlag) {
                canvas = holder.lockCanvas();
                if( canvas!= null) {
                    canvas.drawColor(Color.WHITE);
                    if(win) {
                        Paint fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                        fontPaint.setTextSize(60);
                        fontPaint.setTextAlign(Paint.Align.CENTER);
                        canvas.drawText("Congrats, you win!", width/2, 500,  fontPaint);
                    }
                    else {
                        int color = balls[0].color;
                        for(Ball ball: balls) {
                            if (touchX > square.x && touchX < square.x + square.side && touchY > square.y && touchY < square.y + square.side) {
                                double px = square.x, py = square.y;
                                square.x = touchX - square.side / 2;
                                square.y = touchY - square.side / 2;
                                square.vx = square.x - px;
                                square.vy = square.y - py;
                            }
                            ball.update();
                            square.update();
                            ball.boxCollide();
                            square.squareCollide(ball);
                            if(ball.color != color) color = -1;
                            for(Ball ball2: balls) {
                                if(ball != ball2) Ball.ballsCollide(ball, ball2);
                            }
                        }
                        if (color != -1) win = true;
                    }
                    holder.unlockCanvasAndPost(canvas);
                    try {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e) { }
                }
            }
        }
    }
    DrawThread thread;
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread = new DrawThread(holder);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        thread.runFlag = false;
        thread = new DrawThread(holder);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        thread.runFlag = false;
    }
}
