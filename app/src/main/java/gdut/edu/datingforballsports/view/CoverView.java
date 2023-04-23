package gdut.edu.datingforballsports.view;

import gdut.edu.datingforballsports.domain.User;

public interface CoverView extends View_{

    void onLoginSuccess(String RCmsg);

    void onLoginFails(String RCmsg);

}
