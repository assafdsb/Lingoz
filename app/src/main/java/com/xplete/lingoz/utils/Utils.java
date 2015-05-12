package com.xplete.lingoz.utils;

import android.content.Context;
import android.content.Intent;

import com.xplete.lingoz.R;

public class Utils {
    public static void share(Context context) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getString(R.string.share_message_title));
        intent.putExtra(android.content.Intent.EXTRA_TEXT, context.getString(R.string.share_message_body));
        context.startActivity(Intent.createChooser(intent, "Share via"));
    }

}
