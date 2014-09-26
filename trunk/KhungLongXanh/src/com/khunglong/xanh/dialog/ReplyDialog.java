package com.khunglong.xanh.dialog;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.facebook.Session;
import com.khunglong.xanh.MainActivity;
import com.khunglong.xanh.MyApplication;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.Controller;
import com.khunglong.xanh.comments.AnswerFragment;
import com.khunglong.xanh.comments.CmtAdapter;
import com.khunglong.xanh.myfacebook.FbCommentsLoader;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.FbPostCommentsLoader;
import com.khunglong.xanh.myfacebook.FbUserLoader;
import com.khunglong.xanh.myfacebook.object.FbCmtData;
import com.khunglong.xanh.myfacebook.object.FbCmtFrom;
import com.khunglong.xanh.myfacebook.object.FbComments;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ReplyDialog extends DialogFragment {
    private static final String TAG = "ReplyDialog";

    AddPictureDialogListener listener;

    FilterLog log = new FilterLog(TAG);
    private TextView txtTitle;
    private TextView txtName;
    private TextView txtLike;
    private View viewCmt;
    private EditText txtContent;
    private ImageView imgAvatar;
    private ListView cmtListview;
    private CmtAdapter cmtAdapter;
    private List<FbCmtData> cmtListData;
    private Context context;
    private FbLoaderManager mFbLoaderManager;
    private MyApplication app;
    private String id;
    private int position;
    private int cmtPos;

    int like;
    private String title;
    private String name;
    private String image;
    private ViewGroup mEmpty;
    private CardThumbnail cardThumbnail;

    public interface AddPictureDialogListener {
        void onAddPictureDialogOK();
    }

    public static ReplyDialog newInstance(Number like, String title, String name, String image, String id,
            int position, int cmtPos) {
        ReplyDialog f = new ReplyDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("like", (Integer) like);
        bundle.putString("title", title);
        bundle.putString("name", name);
        bundle.putString("image", image);
        bundle.putString("id", id);
        bundle.putInt("position", position);
        bundle.putInt("cmtPos", cmtPos);

        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        app = (MyApplication) getActivity().getApplication();
        mFbLoaderManager = app.getmFbLoaderManager();

        Bundle bundle = getArguments();
        like = bundle.getInt("like");
        title = bundle.getString("title");
        name = bundle.getString("name");
        image = bundle.getString("image");
        id = bundle.getString("id");
        position = bundle.getInt("position");
        cmtPos = bundle.getInt("cmtPos");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog rootView = new Dialog(getActivity());
        rootView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView.setContentView(R.layout.reply_dialog2);
        mEmpty = (ViewGroup) rootView.findViewById(android.R.id.empty);
        mEmpty.setVisibility(View.VISIBLE);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.waiting, mEmpty, true);

        CardView cardView = (CardView) rootView.findViewById(R.id.reply_card_header);
        Card card = new Card(getActivity());
        CardHeader cardHeader = new CardHeader(getActivity());
        cardHeader.setTitle(title);
        cardHeader.setButtonExpandVisible(true);
        cardHeader.setPopupMenu(R.menu.menu_commend, null);
        card.addCardHeader(cardHeader);

        cardThumbnail = new CardThumbnail(getActivity());
        if (image != null) {
            log.d("log>>> " + "image:" + image);
            // ImageLoader imageLoader = ImageLoader.getInstance();
            // imageLoader.displayImage(image, imgAvatar, null, null);

            cardThumbnail.setUrlResource(image);
        }

        card.addCardThumbnail(cardThumbnail);

        cardView.setCard(card);

        // txtTitle = (TextView) rootView.findViewById(R.id.title);
        // txtTitle.setMovementMethod(new ScrollingMovementMethod());
        // txtLike = (TextView) rootView.findViewById(R.id.like);
        // txtTitle.setText("" + like);
        // txtName = (TextView) rootView.findViewById(R.id.name);
        // txtContent = (EditText) rootView.findViewById(R.id.answer_txt_content);
        // imgAvatar = (ImageView) rootView.findViewById(R.id.avatar);
        // txtTitle.setText(title);

        // txtName.setText(name);
        cmtListview = (ListView) rootView.findViewById(R.id.answer_lv);
        cmtListData = new ArrayList<FbCmtData>();
        cmtAdapter = new CmtAdapter(context, cmtListData, true);
        cmtListview.setAdapter(cmtAdapter);
        // viewCmt = rootView.findViewById(R.id.answer_cmt);
        // viewCmt.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // controllerPost.load();
        // }
        // });
        controllerComments.load();
        return rootView;
    }

    public void setOnAddPictureDialogListener(AddPictureDialogListener listener) {
        this.listener = listener;
    }

    private Controller controllerComments = new Controller() {

        @Override
        public void load() {
            mEmpty.setVisibility(View.VISIBLE);
            Bundle params = new Bundle();
            params.putBoolean("summary", true);
            params.putString("filter", "toplevel");
            final String graphPath = id + "/comments";

            mFbLoaderManager.load(new FbCommentsLoader(context, graphPath, params) {

                @Override
                public void onFbLoaderSuccess(FbComments entry) {
                    mEmpty.setVisibility(View.GONE);
                    cmtAdapter.setData(entry.getData(), true);
                }

                @Override
                public void onFbLoaderStart() {
                    mEmpty.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFbLoaderFail(Throwable e) {
                    mEmpty.setVisibility(View.GONE);
                    log.e("log>>>" + "FbCommentsLoader onFbLoaderFail:" + e.toString());
                }
            });
        }
    };

    private void updateComments(FbComments entry, int position) {
        log.d("log>>>" + "==========updateComments position:" + position);
        cmtListData.clear();
        List<FbCmtData> collection = entry.getData();
        cmtListData.addAll(collection);
        cmtAdapter.notifyDataSetChanged();
        for (int i = 0; i < entry.getData().size(); i++) {
            final int pos = i;

            String idFrom = entry.getData().get(i).getFrom().getId();
            String graphPath = idFrom + "/picture";
            Bundle params = new Bundle();
            params.putBoolean("redirect", false);
            params.putInt("width", 100);
            mFbLoaderManager.load(new FbUserLoader(context, graphPath, params) {

                @Override
                public void onFbLoaderSuccess(FbCmtFrom f) {
                    cmtListData.get(pos).getFrom().setSource(f.getSource());
                    cmtAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFbLoaderStart() {
                }

                @Override
                public void onFbLoaderFail(Throwable e) {
                    log.e("log>>>" + "FbUserLoader onFbLoaderFail:" + e.toString());
                }
            });
        }
    }

    Controller controllerPost = new Controller() {

        @Override
        public void load() {
            Bundle params = new Bundle();
            String message = txtContent.getText().toString();
            if (TextUtils.isEmpty(message)) {
                return;
            }
            Session session = Session.getActiveSession();
            List<String> per = session.getPermissions();
            boolean isAllowPost = false;
            for (String string : per) {
                if (string.equals("publish_actions")) {
                    isAllowPost = true;
                }
            }
            if (isAllowPost) {
                Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(getActivity(),
                        Arrays.asList("publish_actions"));
                session.requestNewPublishPermissions(newPermissionsRequest);
            }

            params.putString("message", message);
            final String graphPath = id + "/comments";
            mFbLoaderManager.load(new FbPostCommentsLoader(context, graphPath, params) {

                @Override
                public void onFbLoaderSuccess(String entry) {
                    log.d("log>>>" + "FbPostCommentsLoader cmt success:" + entry);
                    Toast.makeText(getActivity(), "entry", Toast.LENGTH_SHORT).show();
                    MainActivity loginActivity = (MainActivity) getActivity();
                    loginActivity.onBackPressed();
                }

                @Override
                public void onFbLoaderStart() {

                }

                @Override
                public void onFbLoaderFail(Throwable e) {
                    log.e("log>>>" + "FbPostCommentsLoader onFbLoaderFail:" + e.toString());

                }
            });
        }
    };
}
