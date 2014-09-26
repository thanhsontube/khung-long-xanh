package com.khunglong.xanh.main.drawer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.khunglong.xanh.R;
import com.khunglong.xanh.main.drawer.DrawerItemGenerator.DrawerItem;

public class PageChangeDrawerItem extends DrawerItem<String> {

    protected PageChangeDrawerItem(int icon, CharSequence title, CharSequence id, CharSequence extra, String param) {
        super(icon, title, id, extra, param);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listitem_drawer_item, parent, false);
            convertView.setTag(holder);

            holder.icon = (ImageView) convertView.findViewWithTag("icon");
            holder.title = (TextView) convertView.findViewWithTag("title");

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setImageResource(icon);
        if (title != null) {
            holder.title.setText(title);
            holder.title.setVisibility(View.VISIBLE);
        } else {
            holder.title.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView title;
    }

}
