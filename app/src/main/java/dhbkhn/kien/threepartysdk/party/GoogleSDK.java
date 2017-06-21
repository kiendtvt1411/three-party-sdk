package dhbkhn.kien.threepartysdk.party;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import dhbkhn.kien.threepartysdk.view.MainMvpView;

/**
 * Created by kiend on 6/21/2017.
 */

public class GoogleSDK {
    private MainMvpView mMainMvpView;

    public GoogleSDK(MainMvpView mMainMvpView) {
        this.mMainMvpView = mMainMvpView;
    }

    private GoogleSignInOptions getGOS(){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    public GoogleApiClient getGoogleApiClient() {
        return new GoogleApiClient.Builder((Context) mMainMvpView)
                .enableAutoManage((FragmentActivity) mMainMvpView, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, getGOS())
                .build();
    }

    public void handleSignInResultGoogle(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            mMainMvpView.updateUIGoogle(acct.getDisplayName(), acct.getPhotoUrl().toString());
        }
    }
}
