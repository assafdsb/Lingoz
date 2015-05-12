package com.xplete.lingoz.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.xplete.lingoz.R;
import com.xplete.lingoz.activities.HeadwordDetailActivity;
import com.xplete.lingoz.consts.CONSTS_DICTIONARY;
import com.xplete.lingoz.database.Lemmas;
import com.xplete.lingoz.models.LemmaModel;
import com.xplete.lingoz.utils.AudioPlayer;

import java.util.List;

public class WordOfTheDayBroadcastReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 100;

    @Override
    public void onReceive(Context context, Intent intent) {
        List<LemmaModel> random_lemma = Lemmas.getRandomRows(context, 1);
        if (random_lemma.size() > 0) {
            // set notification's data
            LemmaModel wordOfTheDaylemma = random_lemma.get(0);

            int lemma_id = wordOfTheDaylemma.getId();
            String title = wordOfTheDaylemma.getCaption();
            String contentText = wordOfTheDaylemma.getDefinition();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Intent wordOfTheDayIntent = new Intent(context, HeadwordDetailActivity.class);
            wordOfTheDayIntent.putExtra(CONSTS_DICTIONARY.EXTRA_LEMMA_ID, lemma_id);

            PendingIntent contentIntent = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(wordOfTheDayIntent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setLargeIcon(largeIcon).setSmallIcon(R.drawable.notification_small_icon).setContentTitle(title)
                    .setContentText(contentText).setAutoCancel(true);

            mBuilder.setContentIntent(contentIntent);
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            AudioPlayer.correctAnswer(context);
        }
    }
}
