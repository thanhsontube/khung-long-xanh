package com.khunglong.xanh.main.klx;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;
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

public class DetailHeaderFragment extends BaseFragment {

    private static final String TAG = "DetailHeaderFragment";
    private ImageView img;
    private TextView txtTitle;
    private ResourceManager resource;
    private FilterLog log = new FilterLog(TAG);

    public static DetailHeaderFragment newInstance() {
        DetailHeaderFragment f = new DetailHeaderFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_image_fragment, container, false);
        img = (ImageView) rootView.findViewWithTag("image");
        txtTitle = (TextView) rootView.findViewWithTag("title");
        resource = ResourceManager.getInstance();

        CardView cardview = (CardView) rootView.findViewById(R.id.mycard);
        Card card = new Card(getActivity());
        CardHeader cardHeader = new CardHeader(getActivity());
        cardHeader.setTitle("tscard");
        card.addCardHeader(cardHeader);

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
            resource.getImageLoader().displayImage(link, img, app.getOptionsContent(), null);

            // title
            txtTitle.setText(pageData.getName());
        } catch (Exception e) {
            log.e("log>>>" + "error updateImageAndTitle:" + e.toString());
        }
    }
}
