package ru.home.spornov91;

import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import android.support.v4.content.*;
import android.support.v7.appcompat.*;
import android.view.*;
import android.widget.*;
import android.widget.CompoundButton.*;
import android.widget.SeekBar.*;
import ru.home.spornov91.lib.*;

import android.support.v7.appcompat.R;
public class IActivity extends Activity 
{
	public IUtilites utilites = new IUtilites();
	private IReceiver rcr = new IReceiver();
	private static final String CHANNEL_ID = "CID";
	Button b1,b2, b3;
	TextView e;
	Thread workThread = null;
	CheckBox chbStack;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		b1 = findViewById(R.id.mb1);
		b2 = findViewById(R.id.mb2);
		b3 = findViewById(R.id.mb4);
		
		e = findViewById(R.id.et1);
		
		SeekBar seek = findViewById(R.id.select_volume);
		seek.setMax(10);
		seek.setProgress(0); // Set it to zero so it will start at the left-most edge
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					TextView text = findViewById(R.id.level_volume);
					text.setText("Уровень громкости: "+Integer.toString(progress));
					if(workThread != null){
						workThread.interrupt();
					}
					volChange(progress);
				};
			    @Override
				public void onStartTrackingTouch(SeekBar seekBar) {}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		
		chbStack = findViewById(R.id.chbStack);
		
		chbStack.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override 
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (buttonView.isChecked()) { 
						createNotificationChannel();
						startService(buttonView);
						// checked
						//Toast.makeText(getApplicationContext(), "Чекит", Toast.LENGTH_SHORT).show();
					} 
					else 
					{
						if(workThread.currentThread().isInterrupted()){
							workThread.interrupt();
						}
						stopService(buttonView);
						//Toast.makeText(getApplicationContext(), "Анчекит", Toast.LENGTH_SHORT).show();
					}
				}

			});
		if(workThread.currentThread().isInterrupted()){
			chbStack.setChecked(true);
		}
		b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
					createNotificationChannel();
                    startService(arg0);
					//registerBroadcastReceiver(arg0);
                }
        });
		b2.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0){
				//if(workThread.currentThread().isInterrupted()){
				workThread.interrupt();
				//}
				stopService(arg0);
			e.setText(String.format("Версия Android: %s (%d)", 
			Build.VERSION.RELEASE, Build.VERSION.SDK_INT));
		//--> Пример вывода: " Версия Android: 4.0.4 (15) "
				//unregisterBroadcastReceiver(arg0);
		};
		});
		
		b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
					Intent intent = new Intent(getApplicationContext(), IHome.class);
					startActivity(intent);
                }
			});
    };
	
	public void volChange(final int vol_base){
		final Runnable run = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						utilites.volCh((AudioManager)getSystemService(Context.AUDIO_SERVICE),vol_base);
						//todo Что там надо делать раз в секунду
						Thread.sleep(1000);
					}
				} catch (InterruptedException iex) { }

				workThread = null;
			}};

		if (workThread == null) {
            workThread = new Thread(run);
            workThread.start();
        }
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
		
	public void startService(View v) {
        String input = e.getText().toString();

        Intent serviceIntent = new Intent(this, IService.class);
        serviceIntent.putExtra("inputExtra", input);

        ContextCompat.startForegroundService(this, serviceIntent);
    };

    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, IService.class);
        stopService(serviceIntent);
    };
	// регистрируем широковещательный приёмник
    // для намерения "android.intent.action.TIME_TICK".
    // Данное намерение срабатывает каждую минуту
    public void registerBroadcastReceiver(View view) {
        this.registerReceiver(rcr, new IntentFilter( "android.intent.action.TIME_TICK"));
        Toast.makeText(getApplicationContext(), "Приёмник включен",Toast.LENGTH_SHORT).show();
    }

    // Отменяем регистрацию
    public void unregisterBroadcastReceiver(View view) {
        this.unregisterReceiver(rcr);
        Toast.makeText(getApplicationContext(), "Приёмник выключён", Toast.LENGTH_SHORT).show();
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		//utilites.onKeyDown(getApplicationContext(),keyCode, event);
		// TODO: Implement this method
		return super.onKeyDown(keyCode, event);
	}
};
