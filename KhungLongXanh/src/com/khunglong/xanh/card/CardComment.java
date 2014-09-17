package com.khunglong.xanh.card;

import java.lang.ref.WeakReference;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.myfacebook.FbUserLoader;
import com.khunglong.xanh.myfacebook.object.FbCmtData;
import com.khunglong.xanh.myfacebook.object.FbCmtFrom;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class CardComment extends Card {

    private FbCmtData dto;
    CardThumbnailCommend cardThumbnail;
    CardHeader header;

    public CardComment(Context context, FbCmtData dto) {
        this(context, R.layout.card_commend);
        this.dto = dto;
        resource = ResourceManager.getInstance();

        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.chat_emotion_icon)
                .showImageForEmptyUri(R.drawable.chat_emotion_icon).showImageOnFail(R.drawable.chat_emotion_icon)
                .imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(100)).build();
    }

    public CardComment(Context context) {
        this(context, R.layout.card_commend);
    }

    public CardComment(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {

        header = new CardHeader(getContext());
        header.setButtonOverflowVisible(true);
        header.setPopupMenu(R.menu.menu_commend, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                ResourceManager resource = ResourceManager.getInstance();
                if (resource.getSqlite().insertData(dto.getMessage())) {
                    Toast.makeText(mContext, "Lưu thành công:" + dto.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        addCardHeader(header);

        // Add thumbnail
        cardThumbnail = new CardThumbnailCommend(mContext);
        cardThumbnail.setDrawableResource(R.drawable.ic_circle_blue);
        // image
        // if (TextUtils.isEmpty(dto.getFrom().getSource())) {
        // cardThumbnail.setDrawableResource(R.drawable.ic_circle_blue);
        // } else {
        // if (cardThumbnail == null) {
        // return;
        // }
        //
        // if (cardThumbnail.getImageView() == null) {
        // return;
        // }
        // String source = dto.getFrom().getSource();
        // imageLoader.displayImage(source, cardThumbnail.getImageView());
        // }
        addCardThumbnail(cardThumbnail);

        // Add ClickListener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {

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

        // image
        if (TextUtils.isEmpty(dto.getFrom().getSource())) {
            cardThumbnail.setDrawableResource(R.drawable.nghiemtucvl);
        } else {
            if (cardThumbnail == null) {
                return;
            }

            if (cardThumbnail.getImageView() == null) {
                return;
            }
            String source = dto.getFrom().getSource();
            imageLoader.displayImage(source, cardThumbnail.getImageView());
        }

    }

    public class CardThumbnailCommend extends CardThumbnail {

        private ImageView imageView;

        public CardThumbnailCommend(Context context) {
            super(context);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View imageView) {
            // TODO Auto-generated method stub
            super.setupInnerViewElements(parent, imageView);
            this.imageView = (ImageView) imageView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public boolean applyBitmap(View imageView, Bitmap bitmap) {
            // TODO Auto-generated method stub
            return super.applyBitmap(imageView, bitmap);
        }
        
        

    }

    ImageLoader imageLoader;
    private DisplayImageOptions options;
    private boolean isAnswer;
    private ResourceManager resource;
    private WeakReference<ImageView> imageViewReference;

    public void loadDataAvatar(int pos, String idFrom, ImageView imageView) {
        // int pos = (Integer) imageView.getTag();
        String source = dto.getFrom().getSource();
        if (!TextUtils.isEmpty(source)) {
            // Log.v("LOG", "log>>>" + "YESS:" + pos);
            imageLoader.displayImage(source, imageView, options, null);
            return;
        }
        // Log.v("LOG", "log>>>" + "NOO:" + pos);
        final WeakReference<ImageView> imageViewReference = new WeakReference<ImageView>(imageView);
        String graphPath = idFrom + "/picture";
        Bundle params = new Bundle();
        params.putBoolean("redirect", false);
        params.putInt("width", 100);
        resource.getFbLoaderManager().load(new FbUserLoader(mContext, graphPath, params) {

            @Override
            public void onFbLoaderSuccess(FbCmtFrom f) {

                if (imageViewReference != null) {
                    final ImageView imageView = imageViewReference.get();
                    if (imageView != null) {
                        int pos = (Integer) imageView.getTag();
                        Log.v("LOG", "log>>>" + "SUCCESS:" + pos);
                        dto.getFrom().setSource(f.getSource());
                        imageLoader.displayImage(f.getSource(), imageView, options, null);
                    }
                }
            }

            @Override
            public void onFbLoaderStart() {
            }

            @Override
            public void onFbLoaderFail(Throwable e) {
            }
        });
    }

    public FbCmtData getDto() {
        return dto;
    }

    public void setDto(FbCmtData dto) {
        this.dto = dto;
    }

    public void setSource(String source) {
        if (cardThumbnail == null) {
            return;
        }

        if (cardThumbnail.getImageView() == null) {
            return;
        }

        imageLoader.displayImage(source, cardThumbnail.getImageView());
    }
}
