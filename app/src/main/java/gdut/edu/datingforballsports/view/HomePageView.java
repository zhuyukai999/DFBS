package gdut.edu.datingforballsports.view;


import java.util.List;

import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.domain.User;

public interface HomePageView extends View_ {

    void onLoadUserMessageSuccess(User user);

    void onLoadFails(String RCmsg);

}
