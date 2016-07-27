package com.moonstub.kline.micah.touchexample;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class SingleTouchActivity extends AppCompatActivity {

    int[] soundsID = new int[]{R.raw.laser_sound,R.raw.bubble_sound};
    SoundPoolManager mSoundPoolManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ASingleTouchEventView(this));
        mSoundPoolManager = new SoundPoolManager(this, 10, soundsID);
    }

    private class ASingleTouchEventView extends View {

        private Paint mPaint = new Paint();
        private Paint mTextPaint = new Paint();

        Point mEventLocation = new Point();
        Random r = new Random();

        private ArrayList<Path> mPathList = new ArrayList<>();
        private ArrayList<Paint> mPaints = new ArrayList<>();

        public Paint getNewPaint(){
            Paint p = new Paint();
                p.setAntiAlias(true);

                p.setStrokeWidth(r.nextInt(100) + 10f);

                p.setColor(Color.rgb(
                                    r.nextInt(255),
                                    r.nextInt(255),
                                    r.nextInt(255)));
                p.setStyle(Paint.Style.STROKE);

                p.setStrokeJoin(Paint.Join.BEVEL);
            return p;
        }

        public ASingleTouchEventView(Context context) {
            super(context);
            mPathList.add(new Path());
            mPaints.add(mPaint);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            for(int i = 0; i < mPathList.size(); i++){
                canvas.drawPath(mPathList.get(i), mPaints.get(i));
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mSoundPoolManager.play(1);
                    mPaints.add(getNewPaint());
                    mPathList.add(new Path());
                    mPathList.get(mPathList.size()-1).moveTo(eventX,eventY);
                    return true;
                case MotionEvent.ACTION_MOVE:

                    mPathList.get(mPathList.size()-1).lineTo(eventX,eventY);
                    break;
                case MotionEvent.ACTION_UP:
                    Log.e("ACTION UP FIRED", "SHOULD PLAY AUDIO CLIP");
                    mSoundPoolManager.play(2);

                    break;
                default:
                    return false;
            }

            setPoint(eventX, eventY);

            invalidate();
            return true;
        }

        private void setPoint(float eventX, float eventY) {
            mEventLocation.x = (int) eventX;
            mEventLocation.y = (int) eventY;
        }
    }

}
