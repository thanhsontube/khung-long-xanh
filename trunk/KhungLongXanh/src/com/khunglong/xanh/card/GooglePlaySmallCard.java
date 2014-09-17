package com.khunglong.xanh.card;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import android.content.Context;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.khunglong.xanh.R;
import com.khunglong.xanh.myfacebook.object.FbCmtData;

public class GooglePlaySmallCard extends Card {

    private FbCmtData dto;

    public GooglePlaySmallCard(Context context, FbCmtData dto) {
        this(context, R.layout.card_commend);
        this.dto = dto;
    }

    public GooglePlaySmallCard(Context context) {
        this(context, R.layout.card_commend);
    }

    public GooglePlaySmallCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {

        CardHeader header = new CardHeader(getContext());
        header.setButtonOverflowVisible(true);
        header.setPopupMenu(R.menu.main, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        addCardHeader(header);

        // Add thumbnail
        CardThumbnail cardThumbnail = new CardThumbnail(mContext);
        cardThumbnail.setDrawableResource(R.drawable.ic_circle_blue);
        addCardThumbnail(cardThumbnail);

        // Add ClickListener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        TextView txtTitle = (TextView) parent.findViewWithTag("title");
        TextView txtUser = (TextView) parent.findViewWithTag("user");
        TextView txtLike = (TextView) parent.findViewWithTag("likes");
        if (dto == null) {
            return;
        }

        if (TextUtils.isEmpty(dto.getMessage())) {

            txtTitle.setText("null message");
        } else {
            txtTitle.setText(dto.getMessage());
        }

        if (dto.getFrom() == null) {
            return;
        }

        if (TextUtils.isEmpty(dto.getFrom().getName())) {

            txtUser.setText("null name");
        } else {
            txtUser.setText(dto.getFrom().getName());
        }

        txtLike.setText(String.valueOf(dto.getLike_count()));

    }
}
