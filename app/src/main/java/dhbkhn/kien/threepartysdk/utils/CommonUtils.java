package dhbkhn.kien.threepartysdk.utils;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kiend on 6/21/2017.
 */

public class CommonUtils {
    public static URL getFacebookProfilePicture(String userID){
        URL imageURL = null;
        try {
            imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
            Log.d("IMGURL", "https://graph.facebook.com/" + userID + "/picture?type=large");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return imageURL;
    }
}
