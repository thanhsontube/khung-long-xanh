package com.khunglong.xanh.main.klx;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardView;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.MyApplication;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.json.PageData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class DetailHeaderFragment extends BaseFragment {

    private static final String TAG = "DetailHeaderFragment";
    private TextView txtTitle;
    private ResourceManager resource;
    private FilterLog log = new FilterLog(TAG);
    UniversalCardThumbnail cardThumbnail;
    CardView cardview;

    public static DetailHeaderFragment newInstance() {
        DetailHeaderFragment f = new DetailHeaderFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_image_fragment, container, false);
        txtTitle = (TextView) rootView.findViewWithTag("title");
        resource = ResourceManager.getInstance();

        cardview = (CardView) rootView.findViewById(R.id.mycard);
        Card card = new Card(getActivity());
        CardHeader cardHeader = new CardHeader(getActivity());
        cardHeader.setTitle("tscard");
        card.addCardHeader(cardHeader);

        cardThumbnail = new UniversalCardThumbnail(getActivity());
        cardThumbnail.setExternalUsage(true);
        card.addCardThumbnail(cardThumbnail);

        cardview.setCard(card);

        return rootView;
    }

    public void updateImageAndTitle(PageData pageData) {
        try {
            log.d("log>>>" + "updateImageAndTitle:" + pageData.getName());
            // image
            String link = pageData.getSource();
            // String linkHigh = pageData.getSourceQuality();
            MyApplication app = (MyApplication) getActivity().getApplication();
            // resource.getImageLoader().displayImage(image, img, app.getOptionsContent(), null);
//            resource.getImageLoader().displayImage(image, cardThumbnail.getImageView(), app.getOptionsContent(), null);

            // title
            txtTitle.setText(pageData.getName());
            cardview.getCard().getCardHeader().setTitle(pageData.getName());
            
        } catch (Exception e) {
            log.e("log>>>" + "error updateImageAndTitle:" + e.toString());
        }
    }

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
            // MyApplication app = (MyApplication) getActivity().getApplication();
            // String image =
            // "https://lh5.googleusercontent.com/-squZd7FxR8Q/UyN5UrsfkqI/AAAAAAAAbAo/VoDHSYAhC_E/s96/new%2520profile%2520%25282%2529.jpg";
            // resource.getImageLoader().displayImage(image, cardThumbnail.getImageView(), app.getOptionsContent(),
            // null);

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
            // if (((CardComment) getParentCard()).getCount() % 2 == 0) {
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
}
