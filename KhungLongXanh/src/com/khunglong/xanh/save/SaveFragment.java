package com.khunglong.xanh.save;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader.OnClickCardHeaderPopupMenuListener;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.card.CardSave;
import com.khunglong.xanh.card.CardSave.IDeleteListener;

public class SaveFragment extends BaseFragment implements IDeleteListener {

    private static final String TAG = "SaveFragment";

    FilterLog log = new FilterLog(TAG);

    private CardListView listview;
    List<Card> cards = new ArrayList<Card>();
    CardArrayAdapter mCardArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getActionBar().setHomeButtonEnabled(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.save_fragment, container, false);
        listview = (CardListView) rootView.findViewWithTag("listview");
        cards.clear();

        Cursor cursor = ResourceManager.getInstance().getSqlite().getData();

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(1);
                CardSave card = new CardSave(getActivity(), title);
                ((CardSave) card).setOnListener(this);

                cards.add(card);
            } while (cursor.moveToNext());
        }
        mCardArrayAdapter = new CardArrayAdapter(context, cards);

        listview.setAdapter(mCardArrayAdapter);
        return rootView;
    }

    OnClickCardHeaderPopupMenuListener listener = new OnClickCardHeaderPopupMenuListener() {

        @Override
        public void onMenuItemClick(BaseCard arg0, MenuItem arg1) {
        }
    };

    @Override
    public void delete() {

        cards.clear();
        Cursor cursor = ResourceManager.getInstance().getSqlite().getData();

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(1);
                CardSave card = new CardSave(getActivity(), title);
                ((CardSave) card).setOnListener(SaveFragment.this);

                cards.add(card);
            } while (cursor.moveToNext());
        }
        mCardArrayAdapter.notifyDataSetChanged();

    }

}
