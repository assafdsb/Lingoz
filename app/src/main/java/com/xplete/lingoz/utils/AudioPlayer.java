package com.xplete.lingoz.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.xplete.lingoz.R;
import com.xplete.lingoz.consts.CONSTS_PREFERENCES;

public class AudioPlayer {

    public static void correctAnswer(Context context) {
        play(context, R.raw.correct_sound);
    }

    public static void wrongAnswer(Context context) {
        play(context, R.raw.fail_sound);
    }

    public static void playLemmaRecording(Context context, int lemma_id) {
        int identifier = context.getResources().getIdentifier("lid_" + lemma_id, "raw", context.getPackageName());
        if (identifier > 0) {
            play(context, identifier);
        }
    }

    private static void play(Context context, int ringtone) {
        int DESIRED_VOLUME = PrefUtils.getAppVolume(context);
        if (DESIRED_VOLUME > 0) {
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
                try {
                    int MAX_VOLUME = CONSTS_PREFERENCES.APP_MAX_VOLUME;
                    float log1 = 1.0f - (float) (Math.log(MAX_VOLUME - DESIRED_VOLUME) / Math.log(MAX_VOLUME));
                    MediaPlayer mp = MediaPlayer.create(context, ringtone);
                    mp.setVolume(log1, log1);
                    mp.start();
                    while (mp.isPlaying()) {
                    }
                    mp.release();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
