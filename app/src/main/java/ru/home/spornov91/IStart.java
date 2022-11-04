package ru.home.spornov91;

import android.os.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.view.*;

import ru.home.spornov91.frag.*;
import ru.home.spornov91.*;

public class IStart extends AppCompatActivity implements View.OnClickListener
{
	Fragment frag_home;
	Fragment frag_settings;
	FragmentTransaction ft;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
		
		frag_home = new FragHome();
        ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.sframe, frag_home);
		ft.commit();
	}

	@Override
	public void onClick(View p1)
	{
		switch (p1.getId()) {
            case R.id.bfhome:
				frag_home = new FragHome();
                ft.replace(R.id.sframe, frag_home);
				ft.commit();
                break;
            case R.id.bfsettings:
				frag_settings = new FragSettings();
                ft.replace(R.id.sframe, frag_settings);
				ft.commit();
                break;
			default : break;
		}
	}
}
