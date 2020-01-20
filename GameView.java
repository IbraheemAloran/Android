package com.example.mygame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import android.os.Handler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends View {

    Handler handler;
    Runnable runnable;
    final int UPDATE_MILLIS = 30;
    Bitmap background,spikes,meterPic;
    Display display;
    Point point;
    int dWidth, dHeight;
    Rect rect,hitbox,spike,be;
    int energy = 0;
    Bitmap[] sonic;
    Bitmap[] sonicJump;
    Bitmap[] bee;
    ArrayList<Integer> enemies = new ArrayList<Integer>();
    int beecyc = 0;
    int cycle = 0;
    int velocity=0,gravity=3;
    int sonicX,sonicY;
    int jump = 0;
    int attack = 0;
    int spikesX,beeX,beeY,x1,x2;
    int speed = 20;
    Paint myPaint = new Paint();
    Paint myPaint1 = new Paint();
    final Random random = new Random();
    int score = 0;

    public GameView(Context context) {
        super(context);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        spikes = BitmapFactory.decodeResource(getResources(),R.drawable.spikes);
        meterPic = BitmapFactory.decodeResource(getResources(),R.drawable.meterpic);
        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;
        spikesX = dWidth;
        beeX = dWidth;
        x1 = dWidth+100;
        x2 = dWidth+100;
        rect = new Rect(0,0,dWidth,dHeight);
        sonic = new Bitmap[4];
        sonicJump = new Bitmap[4];
        bee = new Bitmap[3];
        bee[0] = BitmapFactory.decodeResource(getResources(),R.drawable.bees0);
        bee[1] = BitmapFactory.decodeResource(getResources(),R.drawable.bees1);
        bee[2] = BitmapFactory.decodeResource(getResources(),R.drawable.bees2);
        beeY = dHeight/2-bee[0].getHeight()/2;
        sonic[0] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicrun0);
        sonic[1] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicrun1);
        sonic[2] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicrun2);
        sonic[3] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicrun3);
        sonicJump[0] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicjump0);
        sonicJump[1] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicjump1);
        sonicJump[2] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicjump2);
        sonicJump[3] = BitmapFactory.decodeResource(getResources(),R.drawable.sonicjump3);
        enemies.add(1);
        enemies.add(2);
        sonicX = dWidth/5-sonic[1].getWidth()/2;
        sonicY = (dHeight*7)/10-sonic[1].getHeight()/2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        score++;
        //spike = new Rect()
        hitbox = new Rect(sonicX,sonicY,sonicX+sonic[1].getWidth()/2,sonicY+sonic[1].getHeight()/2);
        //canvas.drawBitmap(background,0,0,null);
        canvas.drawBitmap(background,null,rect,null);
        /*if(beecyc == 2){
            beecyc = 0;
        }
        beecyc++;*/
        //player.sprite();
        if(cycle == 3){
            cycle = 0;
        }
        cycle++;
        if (jump == 1){
            velocity += gravity;
            sonicY += velocity;
            canvas.drawBitmap(sonicJump[cycle],sonicX,sonicY,null);
        }
        else{
            canvas.drawBitmap(sonic[cycle],sonicX,sonicY,null);
        }
        if(sonicY >= (dHeight*7)/10-sonic[1].getHeight()/2) {
            jump = 0;
        }
        /*if(spikesX >= -120){
            spikesX -= speed;
            beeX -= speed;
        }
        else{
            beeX = dWidth;
            spikesX = dWidth;
        }*/
        //attack();
        drawEnemy(canvas);
        //canvas.drawBitmap(spikes,(float)spikesX,(dHeight*7)/10-spikes.getHeight()/2,null);
        //canvas.drawBitmap(bee[beecyc],(float)beeX,dHeight/2-bee[0].getHeight()/2,null);
        //myPaint.setColor(getResources().getColor(android.R.color.black));
        //canvas.drawRect(hitbox,myPaint);
        myPaint1.setColor(Color.GRAY);
        canvas.drawRect(meterPic.getWidth()-55,55,meterPic.getWidth()+295,155,myPaint1);
        myPaint.setColor(Color.YELLOW);
        canvas.drawRect(meterPic.getWidth()-55,55,meterPic.getWidth()-55+energy*35,155,myPaint);
        canvas.drawBitmap(meterPic,10,10,null);
        handler.postDelayed(runnable,UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            if(jump == 0) {
                jump = 1;
                velocity = -50;
                if (energy < 11) {
                    energy++;
                }
            }
            else{
                attack = 1;
            }

        }
        return super.onTouchEvent(event);
    }

    /*public void attack(){
        if(attack == 1) {
            if (sonicX < beeX) {
                sonicX += 100;
            }
            if (sonicY > beeY) {
                sonicY -= 50;
            }
            if(sonicX >= beeX && sonicY <= beeY){
                velocity -= 20;
                attack = 0;
            }
        }
        else{
            if(sonicX > dWidth/5-sonic[1].getWidth()/2){
                sonicX -= 15;
            }
        }
    }*/

    public void drawEnemy(Canvas c){
        if(enemies.contains(0)){
            //System.out.println("3");
            //int num = random.nextInt(5);
            enemies.set(enemies.indexOf(0),random.nextInt(3));
        }
        if(enemies.contains(1)){
            spike = new Rect(x1,(dHeight*7)/10,x1+spikes.getWidth(),(dHeight*7)/10-spikes.getHeight());
            myPaint.setColor(getResources().getColor(android.R.color.black));
            c.drawRect(spike,myPaint);
            c.drawBitmap(spikes,(float)x1,(dHeight*7)/10-spikes.getHeight()/2,null);
            x1 -= speed;
            if(x1 <= -150){
                enemies.set(enemies.indexOf(1),0);
                x1 = dWidth+100;
            }
        }
        if(enemies.contains(2)) {
            c.drawBitmap(bee[beecyc], x2, dHeight / 2 - bee[0].getHeight() / 2, null);
            x2 -= speed;
            if (beecyc == 2) {
                beecyc = 0;
            }
            beecyc++;
            if (x2 <= -150) {
                enemies.set(enemies.indexOf(2),0);
                x2 = dWidth+100;
            }
        }
        //System.out.println(enemies.get(0));
    }
}
