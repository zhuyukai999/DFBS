package gdut.edu.datingforballsports.view;

import java.util.List;

import gdut.edu.datingforballsports.domain.Friend;

public interface FriendView extends View_{
    void onLoadSuccess(List<Friend> list);
    void onLoadFail(String RCmsg);
}
