package dhbkhn.kien.threepartysdk.party;

import android.content.Context;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import dhbkhn.kien.threepartysdk.view.MainMvpView;

/**
 * Created by kiend on 6/21/2017.
 */

public class TwitterSDK {
    MainMvpView mMainMvpView;

    public TwitterSDK(MainMvpView mMainMvpView) {
        this.mMainMvpView = mMainMvpView;
    }

    public TwitterConfig getConfigTwitter(){
        return new TwitterConfig.Builder((Context) mMainMvpView)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("CONSUMER_KEY", "CONSUMER_SECRET"))
                .debug(true)
                .build();
    }
}
