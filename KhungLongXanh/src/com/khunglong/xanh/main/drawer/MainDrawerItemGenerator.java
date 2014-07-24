package com.khunglong.xanh.main.drawer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.khunglong.xanh.MsConstant;
import com.khunglong.xanh.R;
import com.khunglong.xanh.main.drawer.DrawerItemGenerator.DrawerItem;
import com.khunglong.xanh.test.TestFragment;

public class MainDrawerItemGenerator implements
		DrawerItemGenerator<DrawerItem<?>> {

	private final Context context;

	public MainDrawerItemGenerator(Context c) {
		this.context = c.getApplicationContext();
	}

	@Override
	public List<DrawerItem<?>> generateMain() {
		final List<DrawerItem<?>> list = new ArrayList<DrawerItem<?>>();
//		 list.add(new FragmentProfileDrawerItem(0,
//		 context.getText(R.string.home), "profile", "profile", null));
		
		
		// Khung Long XANH
		list.add(new PageChangeDrawerItem(R.drawable.ic_launcher, context.getText(R.string.home), "home", "home",
				"khunglongxanhvietnam"));
		
		//haiVL
		list.add(new PageChangeDrawerItem(R.drawable.haivl, context.getText(R.string.notification),
				"notification", "notification","haivl.com"));
		
		//nhatky
		//hanhphucgiobay
		//nghiemtuc VL
		list.add(new PageChangeDrawerItem(R.drawable.nghiemtucvl, context.getText(R.string.help), "help", "help",
				MsConstant.PAGE_3));
		
		list.add(new FragmentChangeDrawerItem(R.drawable.about_us, context.getText(R.string.about), "about", "about",
				TestFragment.newInstance("about", 9)));
//		list.add(new FragmentChangeDrawerItem(R.drawable.search, context.getText(R.string.search), "search", "search",
//				TestFragment.newInstance("search", 99)));
//		list.add(new FragmentChangeDrawerItem(R.drawable.log_out, context.getText(R.string.logout), "logout", "logout",
//				new MainFragment()));
		// list.add(new FragmentChangeDrawerItem(0,
		// context.getText(R.string.sologan), "sologan", "sologan",
		// new MainFragment()));
		return list;
	}

}