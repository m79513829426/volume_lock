package ru.home.reijs.lib;

import android.content.*;
import android.media.*;
import android.view.*;
import android.widget.*;

public class utils
{
	    public boolean listonkeydown(Context context,int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_MENU:
				Toast.makeText(context, "Нажата кнопка Меню", Toast.LENGTH_SHORT)
				.show();
				return true;
			case KeyEvent.KEYCODE_SEARCH:
				Toast.makeText(context, "Нажата кнопка Поиск", Toast.LENGTH_SHORT)
				.show();
				return true;
			case KeyEvent.KEYCODE_BACK:
				Toast.makeText(context, "Нажата кнопка Назад", Toast.LENGTH_SHORT)
				.show();
				return true;
				case KeyEvent.KEYCODE_VOLUME_UP:
				Toast.makeText(context, "Нажата кнопка увеличения громкости", Toast.LENGTH_SHORT)
				.show();
				return true;
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				Toast.makeText(context, "Нажата кнопка уменьшения громкости", Toast.LENGTH_SHORT)
				.show();
				return false;
		} 
		return true;
		};
		
		public void volCh(AudioManager am){
			int c = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			if(c > 9){
				//e.setText("v: "+c);
				am.setStreamVolume(AudioManager.STREAM_MUSIC,9,0);
			}
		};
};
