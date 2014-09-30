package com.khunglong.xanh.card;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;

public class CardSave extends Card {

    CardHeader header;
    private String message;

    public CardSave(Context context, String message) {
        this(context, R.layout.card_save);
        this.message = message;

    }

    public CardSave(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {

        header = new CardHeader(getContext());
        header.setButtonOverflowVisible(true);
        header.setPopupMenu(R.menu.menu_save, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                switch (item.getItemId()) {
                case R.id.action_share:
                    Toast.makeText(getContext(), "Coming soon ...", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.action_delete:
                    ResourceManager.getInstance().getSqlite().deleteRow(message);
                    Toast.makeText(getContext(), "Delete successful", Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        listener.delete();
                    }

                case R.id.action_copy:
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(
                            Context.CLIPBOARD_SERVICE);
                    clipboard.setText(message);
                    Toast.makeText(getContext(), "Copy:" + message, Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
                }

            }
        });

        addCardHeader(header);

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        TextView txtTitle = (TextView) parent.findViewWithTag("title");
        txtTitle.setText(message);

    }

    public void setOnListener(IDeleteListener listener) {
        this.listener = listener;
    }

    IDeleteListener listener;

    public interface IDeleteListener {
        public void delete();
    }

}
