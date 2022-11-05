package ru.spornov91.volume_lock;

import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.SeekBar.*;
import ru.spornov91.volume_lock.*;

public class MainActivity extends Activity 
{
	private static final String CHANNEL_ID = "CID";
	Thread workThread = null;
	int volState;
	Button bstart_service;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		volState = 9;
		volumeChange();
		
		SeekBar seek = findViewById(R.id.select_volume);
		seek.setMax(10);
		seek.setProgress(10);
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					volState = progress;
					volumeChange();
				};
			    @Override
				public void onStartTrackingTouch(SeekBar seekBar) {}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		
		bstart_service = findViewById(R.id.bstart_service);
		bstart_service.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View arg0) {
	            createNotificationChannel();
			    startService();
	        }
		});

    };

	private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
				CHANNEL_ID,
				"Security Channel",
				NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
	};

	private void startService(){
	    Intent serviceIntent = new Intent(this, MainService.class);
	    startForegroundService(serviceIntent);
	};
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch (keyCode) {
			case KeyEvent.KEYCODE_VOLUME_UP:
				volumeChange();
				break;
		}

		return super.onKeyDown(keyCode, event);
	};
	
	public void volumeChange(){
		final Runnable run = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
						int c = am.getStreamVolume(AudioManager.STREAM_MUSIC);
						if(c > volState){
							am.setStreamVolume(AudioManager.STREAM_MUSIC,volState,0);
						}
						Thread.sleep(1000);
					}
				} catch (InterruptedException iex) { }

				workThread = null;
			}};
		if (workThread == null) {
            workThread = new Thread(run);
            workThread.start();
		}
	}
	
    };
