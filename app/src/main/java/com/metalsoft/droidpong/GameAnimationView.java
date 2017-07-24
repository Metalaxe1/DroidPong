package com.metalsoft.droidpong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by Anthony Ratliff on 7/12/2017.
 */

public class GameAnimationView extends View{
    private final float SCREEN_CONSTANT = getResources().getDisplayMetrics().scaledDensity;
    private int HEIGHT = getResources().getDisplayMetrics().heightPixels;
    private int WIDTH = getResources().getDisplayMetrics().widthPixels;
    private float RADIUS, paddleLength, paddleHeight, ballX, ballY, ballVelX, ballVelY, paddleX, average;
    private int ballsMissed, ballsHit;
    private Handler handler;
    private Paint ballPaint, paddlePaint, scorePaint, courtPaint;
    private boolean showPaddle, running, drawBall, gameOver;
    private SecureRandom rand;
    private DecimalFormat formatter;
    private ArrayList<PongEventListener> listeners;

    public GameAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        running = false;
        showPaddle = false;
        drawBall = false;
        gameOver = false;
        handler = new Handler();
        rand = new SecureRandom();
        formatter = new DecimalFormat("#.0");
        average = 100f;
        ballsMissed = 0;
        ballsHit = 0;
        System.out.println(WIDTH + "\t" + HEIGHT);
        initPaints();
        this.listeners = new ArrayList<>();
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    showPaddle = true;
                    if (!running && !gameOver){
                        notifyAllListeners("Game Started");
                        ballX = RADIUS + rand.nextFloat() * (WIDTH - 2 * RADIUS);
                        ballY = RADIUS;
                        ballVelY = (float) (.015*HEIGHT);
                        if (ballX <= WIDTH/2){
                            ballVelX = (float)(.01*WIDTH);
                        } else {
                            ballVelX = (float) (-.01*WIDTH);
                        }
                        drawBall = true;
                        running = true;
                        invalidate();
                    }
                    paddleX = event.getX() - (paddleLength/2);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE){
                    paddleX = event.getX() - (paddleLength/2);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    showPaddle = false;
                    return true;
                }
                return false;
            }
        });
    }

    // Initialize all of the paint variables and set their properties.
    private void initPaints(){
        ballPaint = new Paint();
        paddlePaint = new Paint();
        scorePaint = new Paint();
        courtPaint = new Paint();
        ballPaint.setAntiAlias(true);
        ballPaint.setColor(Color.YELLOW);
        ballPaint.setStyle(Paint.Style.FILL);
        ballPaint.setStrokeJoin(Paint.Join.ROUND);
        paddlePaint.setAntiAlias(true);
        paddlePaint.setColor(Color.WHITE);
        paddlePaint.setStyle(Paint.Style.FILL);
        paddlePaint.setStrokeJoin(Paint.Join.MITER);
        scorePaint.setAntiAlias(true);
        scorePaint.setColor(Color.WHITE);
        scorePaint.setStyle(Paint.Style.FILL);
        scorePaint.setTextSize(24*SCREEN_CONSTANT);
        scorePaint.setFakeBoldText(true);
        scorePaint.setStrokeJoin(Paint.Join.MITER);
        scorePaint.setTextAlign(Paint.Align.RIGHT);
        courtPaint.setAntiAlias(true);
        courtPaint.setColor(Color.WHITE);
        courtPaint.setStyle(Paint.Style.STROKE);
        courtPaint.setStrokeJoin(Paint.Join.MITER);
    }

    // Initialize all of the screen constants.
    private void initConstants(){
        RADIUS = (float)(.03*WIDTH);
        paddleLength = (float)(.3*WIDTH);
        paddleHeight = (float) (.018*HEIGHT);
        courtPaint.setStrokeWidth(RADIUS);
    }

    // Assign the listener implementing events interface that will receive the events
    public void setCustomObjectListener(PongEventListener listener) {
        this.listeners.add(listener);
    }



    private Runnable r = new Runnable() {
        @Override

        public void run() {
            if (drawBall) {
                // If ball hits paddle.
                if(ballY >= HEIGHT - paddleHeight*8 && ballY <= (HEIGHT-paddleHeight*8) + paddleHeight && showPaddle) {
                    // If ball hits the center of the paddle.
                    if (ballX >= paddleX + (.3*paddleLength) && ballX <= paddleX + (.7*paddleLength)){
                        ballVelY = -ballVelY;
                        int change = returnRandom(3);
                        // Randomly determine the change when hitting the center of the paddle.
                        switch (change){
                            case 0: {
                                // no change
                                change = 0;
                                break;
                            }
                            case 1: {
                                // increase by 5dp.
                                change = 5;
                                break;
                            }
                            case 2: {
                                // increase by 10dp.
                                change = 10;
                            }
                        }
                        // Make the change in X velocity only.
                        switch(returnRandom(2)){
                            case 0: {
                                ballVelX = (float) ballVelX + change;
                                break;
                            }
                            default: {
                                ballVelX = (float) ballVelX - change;
                            }
                        }
                        ballsHit++;
                    }
                    // If ball strikes the paddle in the middle/outer sections
                    if ((ballX >= paddleX + (.2*paddleLength) && ballX < paddleX + (.3*paddleLength)) || (ballX > paddleX + (.7*paddleLength) && ballX <= paddleX + (.8*paddleLength))){
                        ballVelY = -ballVelY;
                        if (ballVelX > 0) {
                            ballVelX = ballVelX + 10;
                        } else ballVelX = ballVelX - 10;
                        ballsHit++;
                    }
                    // If ball strikes the paddle in the outer sections
                    if ((ballX >= paddleX && ballX < paddleX + (.2*paddleLength)) || (ballX > paddleX + (.8*paddleLength) && ballX <= paddleX + paddleLength)){
                        ballVelY = -ballVelY;
                        if (ballVelX > 0) {
                            ballVelX = -ballVelX - 15;
                        } else ballVelX = -ballVelX + 15;
                        ballsHit++;
                    }
                }
                // Left side of the court was hit.
                if (ballX <= RADIUS) {
                    ballVelX = -ballVelX;
                    ballX = ballX + 5;
                }
                // Right side of the court was hit.
                if (ballX >= WIDTH - RADIUS){
                    ballVelX = -ballVelX;
                    ballX = ballX - 5;
                }
                // Top was hit.
                if (ballY < RADIUS) {
                    ballVelY = -ballVelY;
                }
                // Volley was missed.
                if (ballY >= HEIGHT + RADIUS) {
                    ballsMissed++;
                    drawBall = false;
                    if (ballsMissed == 10){
                        notifyAllListeners("Game Over");
                        running = false;
                        gameOver = true;
                    } else {
                        handler.postDelayed(missedBall, 1000);
                    }
                }
                ballX += ballVelX;
                ballY += ballVelY;
            }
            if (ballsMissed+ballsHit != 0){
                average = ballsHit/(float)(ballsMissed+ballsHit)*100;
            }
            // Redraw the canvas with changes.
            invalidate();
        }
    };

    private Runnable missedBall = new Runnable() {
        @Override
        public void run() {
            notifyAllListeners("Missed Ball");
            showPaddle = false;
            running = false;
            invalidate();
        }
    };


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw outer rectangle as court boundaries and center line.
        canvas.drawRect(0f, 0f, WIDTH, HEIGHT, courtPaint);
        canvas.drawLine(0f, HEIGHT/2f, WIDTH, HEIGHT/2f, courtPaint);

        // Draw center circle.
        canvas.drawCircle(WIDTH/2, HEIGHT/2, RADIUS*4, courtPaint);

        scorePaint.setColor(Color.WHITE);
        canvas.drawText("Average Score: " + formatter.format(average) + "%", (float) (WIDTH / 2) + (120*SCREEN_CONSTANT), (float) 60*SCREEN_CONSTANT, scorePaint);

        if (drawBall) {
            canvas.drawCircle((float) ballX, (float) ballY, RADIUS, ballPaint);
        }
        if (showPaddle) {
            canvas.drawRect(paddleX, (float) (HEIGHT*.85), (float) paddleX + paddleLength, (float) (HEIGHT*.85) + paddleHeight, paddlePaint);
        }

        if (!drawBall) {
            scorePaint.setColor(Color.GREEN);
            canvas.drawText("Balls Hit: " + String.valueOf(ballsHit), (float) (WIDTH / 2) + (75*SCREEN_CONSTANT), (float) 120*SCREEN_CONSTANT, scorePaint);
            scorePaint.setColor(Color.RED);
            canvas.drawText("Balls Missed: " + String.valueOf(ballsMissed), (float) (WIDTH / 2) + (75*SCREEN_CONSTANT), (float) 160*SCREEN_CONSTANT, scorePaint);
        }
        if (running) {
            handler.post(r);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        HEIGHT = MeasureSpec.getSize(heightMeasureSpec);
        WIDTH = MeasureSpec.getSize(widthMeasureSpec);
        initConstants();
    }

    private int returnRandom(int size){
        SecureRandom temp = new SecureRandom();
        return temp.nextInt(size);
    }

    private void notifyAllListeners(String message){
        for (int c = 0; c < listeners.size(); c++) {
            listeners.get(c).onDataLoaded(message);
        }
    }

    public interface PongEventListener {
        public void onObjectReady(String title);
        public void onDataLoaded(String data);
    }
}

