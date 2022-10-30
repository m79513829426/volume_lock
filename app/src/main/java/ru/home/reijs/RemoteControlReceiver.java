package ru.home.reijs;

import android.content.*;
import android.media.*;
import android.view.*;
import android.widget.*;

public class RemoteControlReceiver extends BroadcastReceiver
 {
    @Override
    public void onReceive(Context context, Intent intent) {
		String msg1 = "1 onReceive";
		Toast toast1 = Toast.makeText(context,msg1,7);
		toast1.show();
        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
            KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (KeyEvent.KEYCODE_MEDIA_PLAY == event.getKeyCode()) {
                // Handle key press.
				String msg = "2 onReceive";
				Toast toast = Toast.makeText(context,msg,7);
				toast.show();
            }
        }
    }
}
