package dhbkhn.kien.threepartysdk.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterConfig;

import java.util.Arrays;

import dhbkhn.kien.threepartysdk.party.FacebookSDK;
import dhbkhn.kien.threepartysdk.R;
import dhbkhn.kien.threepartysdk.party.GoogleSDK;
import dhbkhn.kien.threepartysdk.party.TwitterSDK;

public class MainActivity extends AppCompatActivity implements MainMvpView{

    private TextView mTvUsernameFb;
    private TextView mTvUsernameGG;
    private TextView mTvUsernameTwitter;

    private ImageButton mBtnLoginFb;
    private ImageButton mBtnLoginGG;
    private ImageButton mBtnLoginTwitter;

    private ImageView mImgAvaFb;
    private ImageView mImgAvaGG;
    private ImageView mImgAvaTwitter;

    // fb sdk
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mAccessTokenTracker;
    private AccessToken mAccessToken;
    private FacebookSDK mFacebookSDK;

    // gg sdk
    private GoogleApiClient mGoogleApiClient;
    private GoogleSDK mGoogleSDK;
    private static final int RC_SIGN_IN = 69;

    // twitter sdk
    private TwitterSDK mTwitterSDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // fb sdk
        mCallbackManager = CallbackManager.Factory.create();
        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        mAccessToken = AccessToken.getCurrentAccessToken();
        mFacebookSDK = new FacebookSDK(this);
        mFacebookSDK.getLoginManager(mCallbackManager, mAccessToken);

        // gg sdk
        mGoogleSDK = new GoogleSDK(this);
        mGoogleApiClient = mGoogleSDK.getGoogleApiClient();

        // twitter sdk
        mTwitterSDK = new TwitterSDK(this);
        TwitterConfig config = mTwitterSDK.getConfigTwitter();
        Twitter.initialize(config);

        // init view
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAccessTokenTracker.stopTracking();
    }

    private void addControls() {
        mTvUsernameFb = (TextView) findViewById(R.id.tvUsernameFb);
        mTvUsernameGG = (TextView) findViewById(R.id.tvUsernameGG);
        mTvUsernameTwitter = (TextView) findViewById(R.id.tvUsernameTwitter);
        mImgAvaFb = (ImageView) findViewById(R.id.imgAvaFb);
        mImgAvaGG = (ImageView) findViewById(R.id.imgAvaGG);
        mImgAvaTwitter = (ImageView) findViewById(R.id.imgAvaTwitter);
        mBtnLoginFb = (ImageButton) findViewById(R.id.btnLoginFb);
        mBtnLoginGG = (ImageButton) findViewById(R.id.btnLoginGG);
        mBtnLoginTwitter = (ImageButton) findViewById(R.id.btnLoginTwitter);
    }

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnLoginFb:
                    LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
                    break;
                case R.id.btnLoginGG:
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                    break;
                case R.id.btnLoginTwitter:
                    break;
            }
        }
    };

    private void addEvents() {
        mBtnLoginFb.setOnClickListener(myClick);
        mBtnLoginGG.setOnClickListener(myClick);
        mBtnLoginTwitter.setOnClickListener(myClick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mGoogleSDK.handleSignInResultGoogle(result);
        }
    }

    @Override
    public void updateUIFacebook(String userId, String username, String linkAva) {
        mTvUsernameFb.setText(username);
        Glide.with(MainActivity.this).load(linkAva).into(mImgAvaFb);
    }

    @Override
    public void updateUIGoogle(String username, String linkAva) {
        mTvUsernameGG.setText(username);
        Glide.with(MainActivity.this).load(linkAva).into(mImgAvaGG);
    }

    @Override
    public void updateUITwitter(String username, String linkAva) {
        mTvUsernameTwitter.setText(username);
        Glide.with(MainActivity.this).load(linkAva).into(mImgAvaTwitter);
    }
}
