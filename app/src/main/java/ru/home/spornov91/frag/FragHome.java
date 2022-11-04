package ru.home.spornov91.frag;

import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import ru.home.spornov91.*;

public class FragHome extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		inflater.inflate(R.layout.home,null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
