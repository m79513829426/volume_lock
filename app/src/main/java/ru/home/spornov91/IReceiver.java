package ru.home.spornov91;

import android.content.*;
import android.widget.*;

import ru.home.spornov91.*;

public class IReceiver extends BroadcastReceiver
 {
    @Override
    public void onReceive(Context context, Intent intent) {
		Toast.makeText(context,"IReceiver",7).show();
		context.startService(new Intent(context, IService.class));
    }
}
