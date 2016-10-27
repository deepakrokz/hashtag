package com.hashtag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "4PLP9SxDRwqFAnnScwi2zLq5v";
    private static final String TWITTER_SECRET = "yQvLoKrmVV2c6wT7HD8bG0v0MvsTPEB6hikzqhrJSPvEL7lEJH";
    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(AppController_HashTag.getSpUserInfo().getBoolean("is_Login", false)){
            finish();
            Intent mIntent = new Intent(LoginActivity.this, HashTagListing.class);
            startActivity(mIntent);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }


        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                AppController_HashTag.getSpUserInfo().edit().putBoolean("is_Login",true).commit();
                AppController_HashTag.getSpUserInfo().edit()
                        .putString("userid", ""+session.getUserId())
                        .putString("username", session.getUserName())
                        .putString("auth", session.getAuthToken().toString())
                        .putString("ID", ""+session.getId()).commit();
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Log.e("", "session.getId()"+session.getId());
                Log.e("", "session.getUserId()"+session.getUserId());
                Log.e("", "session.getAuthToken()"+session.getAuthToken());
                finish();
                Intent mIntent = new Intent(LoginActivity.this, HashTagListing.class);
                startActivity(mIntent);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
