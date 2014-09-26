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

//        list.add(new PageChangeDrawerItem(R.drawable.drawer_gai,
//                context.getResources().getStringArray(R.array.page0)[1], "home", "home", context.getResources()
//                        .getStringArray(R.array.page0)[0]));

        // Khung Long XANH
        list.add(new PageChangeDrawerItem(R.drawable.drawer_klx,
                context.getResources().getStringArray(R.array.page1)[1], "home", "home", context.getResources()
                        .getStringArray(R.array.page1)[0]));

        // // haiVL
        // list.add(new PageChangeDrawerItem(R.drawable.drawer_haivl,
        // context.getResources().getStringArray(R.array.page2)[1], "notification", "notification", context
        // .getResources().getStringArray(R.array.page2)[0]));
        //
        // list.add(new PageChangeDrawerItem(R.drawable.drawer_haivl,
        // context.getResources().getStringArray(R.array.page3)[1], "help", "help", context.getResources()
        // .getStringArray(R.array.page3)[0]));
        //
        // // page 4
        // list.add(new PageChangeDrawerItem(R.drawable.drawer_nghiemtucvl, context.getResources().getStringArray(
        // R.array.page4)[1], "help", "help", context.getResources().getStringArray(R.array.page4)[0]));
        //
        // // page 5
        // list.add(new PageChangeDrawerItem(R.drawable.drawer_hanhphucgiobay, context.getResources().getStringArray(
        // R.array.page5)[1], "help", "help", context.getResources().getStringArray(R.array.page5)[0]));

        list.add(new FragmentChangeDrawerItem(R.drawable.drawer_save_s, "Save", "Save", "Save", new SaveFragment()));
        return list;
    }

}
