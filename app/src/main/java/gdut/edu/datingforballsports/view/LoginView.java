package gdut.edu.datingforballsports.view;

import android.os.Handler;

public interface LoginView extends View_ {
    String getUserName();

    String getPassword();

    void onLoginSuccess(int userId,String token);

    void onLoginFails();

}
