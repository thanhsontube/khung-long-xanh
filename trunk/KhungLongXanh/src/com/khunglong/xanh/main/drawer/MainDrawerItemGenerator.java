package com.khunglong.xanh.main.drawer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.khunglong.xanh.R;
import com.khunglong.xanh.main.drawer.DrawerItemGenerator.DrawerItem;
import com.khunglong.xanh.save.SaveFragment;

public class MainDrawerItemGenerator implements DrawerItemGenerator<DrawerItem<?>> {

    private final Context context;

    public MainDrawerItemGenerator(Context c) {
        this.context = c.getApplicationContext();
    }

    @Override
    public List<DrawerItem<?>> generateMain() {
        final List<DrawerItem<?>> list = new ArrayList<DrawerItem<?>>();

        // Khung Long XANH
        list.add(new PageChangeDrawerItem(R.drawable.drawer_klx,
                context.getResources().getStringArray(R.array.page1)[1], "home", "home", context.getResources()
                        .getStringArray(R.array.page1)[0]));

        // haiVL
        list.add(new PageChangeDrawerItem(R.drawable.drawer_haivl,
                context.getResources().getStringArray(R.array.page2)[1], "notification", "notification", context
                        .getResources().getStringArray(R.array.page2)[0]));

        // nhatky
        // hanhphucgiobay
        // nghiemtuc VL
        list.add(new PageChangeDrawerItem(R.drawable.drawer_haivl,
                context.getResources().getStringArray(R.array.page3)[1], "help", "help", context.getResources()
                        .getStringArray(R.array.page3)[0]));

        // page 4
        // list.add(new FragmentChangeDrawerItem(R.drawable.hanhphucgiobay, context.getResources().getStringArray(
        // R.array.page4)[1], "help", "help", MainFragment4.newInstance(context.getResources().getStringArray(
        // R.array.page4)[0])));

        // page 4
        list.add(new PageChangeDrawerItem(R.drawable.drawer_nghiemtucvl, context.getResources().getStringArray(
                R.array.page4)[1], "help", "help", context.getResources().getStringArray(R.array.page4)[0]));

        // page 5
        list.add(new PageChangeDrawerItem(R.drawable.drawer_hanhphucgiobay, context.getResources().getStringArray(
                R.array.page5)[1], "help", "help", context.getResources().getStringArray(R.array.page5)[0]));

        // list.add(new PageChangeDrawerItem(R.drawable.androidstorevn, context.getResources().getStringArray(
        // R.array.page6)[1], "help", "help", context.getResources().getStringArray(
        // R.array.page6)[0]));
        //
        // list.add(new PageChangeDrawerItem(R.drawable.vohoailinh, context.getResources().getStringArray(
        // R.array.page7)[1], "help", "help", context.getResources().getStringArray(
        // R.array.page7)[0]));
        //
        // list.add(new PageChangeDrawerItem(R.drawable.tung, context.getResources().getStringArray(
        // R.array.page8)[1], "help", "help", context.getResources().getStringArray(
        // R.array.page8)[0]));
        //
        // list.add(new PageChangeDrawerItem(R.drawable.cristiano, context.getResources().getStringArray(
        // R.array.page9)[1], "help", "help", context.getResources().getStringArray(
        // R.array.page9)[0]));
        //
        // list.add(new PageChangeDrawerItem(R.drawable.leomessi, context.getResources().getStringArray(
        // R.array.page10)[1], "help", "help", context.getResources().getStringArray(
        // R.array.page10)[0]));

        // list.add(new FragmentChangeDrawerItem(R.drawable.about_us, context.getText(R.string.about), "about", "about",
        // TestFragment.newInstance("about", 9)));
        // list.add(new FragmentChangeDrawerItem(R.drawable.search, context.getText(R.string.search), "search",
        // "search",
        // TestFragment.newInstance("search", 99)));
        // list.add(new FragmentChangeDrawerItem(R.drawable.log_out, context.getText(R.string.logout), "logout",
        // "logout",
        // new MainFragment()));
        // list.add(new FragmentChangeDrawerItem(0,
        // context.getText(R.string.sologan), "sologan", "sologan",
        // new MainFragment()));

        list.add(new FragmentChangeDrawerItem(R.drawable.drawer_save_s, "Save", "Save", "Save", new SaveFragment()));
        return list;
    }

}
