package com.nirmalhk7.nirmalhk7;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.List;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class common {
    Context mContext;
    public common(Context context)
    {
        mContext=context;
    }
    public void hideKeyboard(View view){
        InputMethodManager imm= (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm!=null) {
            if (view == null)
                view = new View(mContext);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "nirmalhk7";
            String description = "Official Life Management app of Nirmal Khedkar";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("NIRMALHK7", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void createNotification(List<NotificationCompat.Action> actionList,String notificationTitle,String notificationSubtitle)
    {
        createNotificationChannel();
        Intent snoozeIntent = new Intent(mContext, MainActivity.class);
        snoozeIntent.setAction("CLKICK");
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(mContext, 0, snoozeIntent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "NIRMALHK7")
                .setSmallIcon(R.drawable.ic_app)
                .setContentTitle(notificationTitle)
                .setContentText(notificationSubtitle)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
//                .addAction(R.drawable.ic_marksmanager, "CLICK",
//                        snoozePendingIntent);
        for(int i=0;i<actionList.size();++i)
        {
            builder.addAction(actionList.get(i));
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        notificationManager.notify(1, builder.build());

    }
}
