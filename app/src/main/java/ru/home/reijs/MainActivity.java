package ru.home.reijs;

import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import android.support.v4.content.*;
import android.view.*;
import android.widget.*;

import ru.home.reijs.lib.*;
public class MainActivity extends Activity 
{
	public utils utilites = new utils();
	private RemoteControlReceiver rcr = new RemoteControlReceiver();
	private static final String CHANNEL_ID = "request_channel";
	Button b1,b2;
	TextView e;
	Thread workThread = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		b1 = findViewById(R.id.mb1);
		b2 = findViewById(R.id.mb2);
		e = findViewById(R.id.et1);
		
		b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    
					createNotificationChannel();
                    startService(arg0);
					registerBroadcastReceiver(arg0);
                }
        });
		b2.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0){
				if(workThread.currentThread().isInterrupted()){
				workThread.interrupt();
				}
				//stopService(arg0);
			e.setText(String.format("Версия Android: %s (%d)", 
			Build.VERSION.RELEASE, Build.VERSION.SDK_INT));
		//--> Пример вывода: " Версия Android: 4.0.4 (15) "
				unregisterBroadcastReceiver(arg0);
		};
		});
	
		final Runnable run = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						utilites.volCh((AudioManager)getSystemService(Context.AUDIO_SERVICE));
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
		
    }
	private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
				CHANNEL_ID,
				"Security Channel",
				NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }}
	public void startService(View v) {
        String input = e.getText().toString();

        Intent serviceIntent = new Intent(this, ExampleService.class);
        serviceIntent.putExtra("inputExtra", input);

        ContextCompat.startForegroundService(this, serviceIntent);
    };

    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, ExampleService.class);
        stopService(serviceIntent);
    };
	// регистрируем широковещательный приёмник
    // для намерения "android.intent.action.TIME_TICK".
    // Данное намерение срабатывает каждую минуту
    public void registerBroadcastReceiver(View view) {
        this.registerReceiver(rcr, new IntentFilter(
								  "android.intent.action.ACTION_UP"));
        Toast.makeText(getApplicationContext(), "Приёмник включен",
					   Toast.LENGTH_SHORT).show();
    }

    // Отменяем регистрацию
    public void unregisterBroadcastReceiver(View view) {
        this.unregisterReceiver(rcr);
        Toast.makeText(getApplicationContext(), "Приёмник выключён", Toast.LENGTH_SHORT)
			.show();
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		//utilites.listonkeydown(getApplicationContext(),keyCode, event);
		// TODO: Implement this method
		return super.onKeyDown(keyCode, event);
	}
};
