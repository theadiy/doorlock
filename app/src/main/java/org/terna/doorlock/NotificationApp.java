package org.terna.doorlock;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationApp extends Application {

    public static final String CHANNEL_1_ID = "channel1";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }
    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES .O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID,
                    "Channel1",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Notifies about door");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
