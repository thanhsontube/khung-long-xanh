package com.khunglong.xanh.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.Session;
import com.khunglong.xanh.MainActivity;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragmentActivity;
import com.khunglong.xanh.inappbilling.IabHelper;
import com.khunglong.xanh.inappbilling.IabResult;
import com.khunglong.xanh.inappbilling.IabUtil;
import com.khunglong.xanh.login.LoginFragment.ILoginListener;

public class MyLoginActivity extends BaseFragmentActivity implements ILoginListener {

    private IabHelper mHelper;
    private static final String TAG = "MyLoginActivity";

    @Override
    protected Fragment createFragmentMain(Bundle savedInstanceState) {
        return new LoginFragment();
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.login_parent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        String base64EncodedPublicKey = IabUtil.key;

        // compute your public key and store it in base64EncodedPublicKey
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null)
                    return;

                // Hooray, IAB is fully set up!
                IabUtil.getInstance().retrieveData(mHelper);
            }
        });
    }

    @Override
    public void onLogin(LoginFragment f, String pageName) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("page", pageName);
        startActivity(intent);
    }

    @Override
    public void onLogout(LoginFragment f, Session session) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHelper != null)
            mHelper.dispose();
        mHelper = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    public IabHelper getHelper() {
        return mHelper;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_purchase, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_donate:
            IabUtil.showBeer(this, mHelper);
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
