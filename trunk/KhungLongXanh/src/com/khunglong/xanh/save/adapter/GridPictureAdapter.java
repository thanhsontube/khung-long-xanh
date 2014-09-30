package com.khunglong.xanh.save.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.khunglong.xanh.R;

public class GridPictureAdapter extends ArrayAdapter<File> {
    private List<File> list;
    private Context context;
    private AQuery aQuery;

    public GridPictureAdapter(Context context, List<File> list) {
        super(context, 0, list);
        this.list = list;
        this.context = context;
        aQuery = new AQuery(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final Holder holder;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_grid_picture, parent, false);
            holder = new Holder();
            holder.img = (ImageView) v.findViewWithTag("icon");
            holder.txtTitle = (TextView) v.findViewWithTag("title");
            holder.btnPopup = (ImageButton) v.findViewWithTag("popup");
            holder.img.setTag(position);
            v.setTag(holder);

        } else {
            holder = (Holder) v.getTag();
        }

        File dto = list.get(position);
        holder.txtTitle.setText(dto.getName());
        aQuery.id(holder.img).image(dto, 0);

        holder.btnPopup.setOnClickListener(new MyClickListener(position));
        return v;
    }

    static class Holder {
        ImageView img;
        TextView txtTitle;
        ImageButton btnPopup;
    }

    class MyClickListener implements OnClickListener, OnMenuItemClickListener {

        int id;

        public MyClickListener(int id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            PopupMenu popup = new PopupMenu(context, v);
            popup.setOnMenuItemClickListener(this);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_save_picture, popup.getMenu());
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem arg0) {

            return false;
        }

    }
}
