package dhbkhn.kien.threepartysdk.view;

/**
 * Created by kiend on 6/21/2017.
 */

public interface MainMvpView {

    void updateUIFacebook(String userId, String username, String linkAva);

    void updateUIGoogle(String username, String linkAva);

    void updateUITwitter(String username, String linkAva);
}
