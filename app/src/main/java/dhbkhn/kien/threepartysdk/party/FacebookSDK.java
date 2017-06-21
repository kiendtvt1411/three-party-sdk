package dhbkhn.kien.threepartysdk.party;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import dhbkhn.kien.threepartysdk.utils.CommonUtils;
import dhbkhn.kien.threepartysdk.view.MainMvpView;

/**
 * Created by kiend on 6/21/2017.
 */

public class FacebookSDK {
    private MainMvpView mMainMvpView;

    public FacebookSDK(MainMvpView mMainMvpView) {
        this.mMainMvpView = mMainMvpView;
    }

    public void getLoginManager(CallbackManager callbackManager, final AccessToken accessToken) {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (loginResult != null) {
                    GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                String userId = object.get("id").toString();
                                String username = object.get("name").toString();
                                String link = String.valueOf(CommonUtils.getFacebookProfilePicture(userId));
                                mMainMvpView.updateUIFacebook(userId, username, link);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id, name, link");
                    request.setParameters(parameters);
                    request.executeAsync();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
}
