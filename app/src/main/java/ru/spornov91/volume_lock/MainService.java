package ru.spornov91.volume_lock;

import android.app.*;
import android.content.*;
import android.os.*;

public class MainService extends Service
 {
	private static final String CHANNEL_ID = "CID";
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new Notification.Builder(this, CHANNEL_ID)
			.setContentTitle("Служба локера")
			.setContentText("Волум замороженый")
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(pendingIntent)
			.build();

        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
