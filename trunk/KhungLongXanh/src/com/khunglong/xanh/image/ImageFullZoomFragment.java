package com.khunglong.xanh.image;

import java.io.File;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragment;

public class ImageFullZoomFragment extends BaseFragment implements OnTouchListener {

    private int position;
    private ImageView imageView;

    public static ImageFullZoomFragment newInstance(int value) {
        ImageFullZoomFragment f = new ImageFullZoomFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("value", value);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("value");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getActionBar().setHomeButtonEnabled(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().getActionBar().setDisplayShowCustomEnabled(false);

        getActivity().getActionBar().setTitle("Details");
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.image_full_zoom_fragment, container, false);
        imageView = (ImageView) rootView.findViewWithTag("image");
        imageView.setOnTouchListener(this);

        File file = new File(Environment.getExternalStorageDirectory(), "KLX");
        File dto = null;
        if (file.exists()) {
            File[] fs = file.listFiles();
            dto = fs[position];
        }
        aQuery.id(imageView).image(dto, 0);

        return rootView;
    }

    // copy out source

    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f, MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        dumpEvent(event);
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN: // first finger down only
            savedMatrix.set(matrix);
            start.set(event.getX(), event.getY());
            mode = DRAG;
            break;

        case MotionEvent.ACTION_UP: // first finger lifted

        case MotionEvent.ACTION_POINTER_UP: // second finger lifted

            mode = NONE;
            break;

        case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

            oldDist = spacing(event);
            if (oldDist > 5f) {
                savedMatrix.set(matrix);
                midPoint(mid, event);
                mode = ZOOM;
            }
            break;

        case MotionEvent.ACTION_MOVE:

            if (mode == DRAG) {
                matrix.set(savedMatrix);
                matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in
                                                                                      // the matrix of points
            } else if (mode == ZOOM) {
                // pinch zooming
                float newDist = spacing(event);
                if (newDist > 5f) {
                    matrix.set(savedMatrix);
                    scale = newDist / oldDist; // setting the scaling of the
                                               // matrix...if scale > 1 means
                                               // zoom in...if scale < 1 means
                                               // zoom out
                    matrix.postScale(scale, scale, mid.x, mid.y);
                }
            }
            break;
        }

        imageView.setImageMatrix(matrix); // display the transformation on screen

        return true; // indicate event was handled
    }

    /*
     * -------------------------------------------------------------------------- Method: spacing Parameters:
     * MotionEvent Returns: float Description: checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    /*
     * -------------------------------------------------------------------------- Method: midPoint Parameters: PointF
     * object, MotionEvent Returns: void Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /** Show an event in the LogCat view, for debugging */
    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }
}
