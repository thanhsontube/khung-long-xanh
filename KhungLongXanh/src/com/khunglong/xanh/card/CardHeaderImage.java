package com.khunglong.xanh.card;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import android.content.Context;
import android.preference.PreferenceActivity.Header;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.khunglong.xanh.R;
import com.khunglong.xanh.json.PageData;

public class CardHeaderImage extends Card {

    private PageData dto;
    private ThumbnailHeader thumbnail;

    public CardHeaderImage(Context context) {
        super(context);
        init();
    }

    public CardHeaderImage(Context context, PageData data) {
        super(context);
        this.dto = data;
    }

    public CardHeaderImage(Context context, int innerLayout) {
        super(context, innerLayout);

    }

    private void init() {
        CardHeader cardHeader = new CardHeader(mContext);
        cardHeader.setTitle("KLX ");
        addCardHeader(cardHeader);

        thumbnail = new ThumbnailHeader(mContext);
        thumbnail.setDrawableResource(R.drawable.login_klx);
        addCardThumbnail(thumbnail);
    }

    class ThumbnailHeader extends CardThumbnail {
        private ImageView imageView;

        public ThumbnailHeader(Context context) {
            super(context);

        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View imageView) {
            if (imageView != null) {
                
                this.imageView = (ImageView) imageView;
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params);
            }
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

    }

}
