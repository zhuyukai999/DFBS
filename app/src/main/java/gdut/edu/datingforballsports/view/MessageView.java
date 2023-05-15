package gdut.edu.datingforballsports.view;

import gdut.edu.datingforballsports.domain.User;

public interface MessageView extends View_{
    void onLoadSuccess(User user);

    void onLoadFails(String RCmsg);

}
