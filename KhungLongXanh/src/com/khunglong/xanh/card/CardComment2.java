package com.khunglong.xanh.card;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.khunglong.xanh.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CardComment2 extends Card {

    protected String mTitle;
    protected String mUser;
    protected int mLike;
    protected int mCmt;
    protected int count;
    protected String image;

    DisplayImageOptions options;

    ImageLoader loader;

    public CardComment2(Context context, DisplayImageOptions options) {
        this(context, R.layout.card_commend);
        this.options = options;
    }

    public CardComment2(Context context, DisplayImageOptions options, String link) {
        this(context, R.layout.card_commend);
        this.options = options;
        this.image = link;
        loader = ImageLoader.getInstance();
    }
    
    public CardComment2(Context context) {
        this(context, R.layout.card_commend);
    }

    public CardComment2(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    private void init() {

        // // Add thumbnail
        // UniversalCardThumbnail cardThumbnail = new UniversalCardThumbnail(mContext);
        // cardThumbnail.setExternalUsage(true);
        // addCardThumbnail(cardThumbnail);
        //
        // if (!TextUtils.isEmpty(image)) {
        // loader.displayImage(image, cardThumbnail.getImageView());
        // } else {
        // image =
        // "https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s96/new%2520profile%2520%25282%2529.jpg";
        // loader.displayImage(image, cardThumbnail.getImageView());
        // }
        //
        // // Add ClickListener
        // setOnClickListener(new OnCardClickListener() {
        // @Override
        // public void onClick(Card card, View view) {
        // Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_SHORT).show();
        // }
        // });

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

    public void setImage(String url) {

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        // Retrieve elements
        TextView title = (TextView) parent.findViewWithTag("title");
        TextView secondaryTitle = (TextView) parent.findViewWithTag("user");

        // if (title != null)
        // title.setText(mTitle);
        //
        // if (secondaryTitle != null)
        // secondaryTitle.setText(mUser);

    }

    /**
     * CardThumbnail which uses Universal-Image-Loader Library. If you use an external library you have to provide your
     * login inside #setupInnerViewElements.
     * 
     * This method is called before built-in method. If {@image
     * it.gmariotti.cardslib.library.internal.CardThumbnail#isExternalUsage()} is false it uses the built-in method.
     */
    class UniversalCardThumbnail extends CardThumbnail {

        private ImageView imageView;

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public UniversalCardThumbnail(Context context) {
            super(context);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {
            this.imageView = (ImageView) viewImage;

            /*
             * If your cardthumbnail uses external library you have to provide how to load the image. If your
             * cardthumbnail doesn't use an external library it will use a built-in method
             */

            // It is just an example.
            // In real case you should config better the imageLoader
            // ImageLoader imageLoader = ImageLoader.getInstance();
            // imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
            //
            //
            // //Here you have to set your image with an external library
            // //Only for test, use a Resource Id and a Url
            // if (((GooglePlaySmallCard) getParentCard()).getCount() % 2 == 0) {
            // imageLoader.displayImage("https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s96/new%2520profile%2520%25282%2529.jpg",
            // (ImageView) viewImage,options);
            // } else {
            // imageLoader.displayImage("drawable://" + R.drawable.ic_tris, (ImageView) viewImage,options);
            // }

            /*
             * viewImage.getLayoutParams().width = 96; viewImage.getLayoutParams().height = 96;
             */
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSecondaryTitle() {
        return mUser;
    }

    public void setSecondaryTitle(String secondaryTitle) {
        mUser = secondaryTitle;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
