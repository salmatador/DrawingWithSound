package com.moonstub.kline.micah.touchexample;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

/**
 * Created by Micah on 7/26/2016.
 */
public class SoundPoolManager {

    SoundPool mSoundPool;
    int[] mSoundsID;
    AudioAttributes mAttributes;
    Context mContext;

    public float v = .5f;
    private float rate = 1f;
    private int loop = 0;
    private int p = 1;

    public SoundPoolManager(Context context, int maxStreams, int [] soundsID){
        mContext = context;
        mSoundsID = soundsID;
        createSoundPool(maxStreams);
        loadAudioClips();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createSoundPool(int maxStreams) {
        mAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setMaxStreams(maxStreams)
                .setAudioAttributes(mAttributes)
                .build();
    }

    public void loadAudioClips(){
        mSoundPool.load(mContext, mSoundsID[0], 1);
        mSoundPool.load(mContext, mSoundsID[1], 1);

        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//                soundPool.play(sampleId, 50f, 50f, 1,0,1.0f);
                play(sampleId);
                Log.e("LOAD COMPLETED",
                        soundPool.toString() + " : " + sampleId + " : " + status);
            }
        });
    }

    public int play(final int id){

        int i = mSoundPool.play(id,v,v,p,loop,rate);
        Log.e("PLAYING SOUND VALUE = ", id + "");
        return i;
    }

}
