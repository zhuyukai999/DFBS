package gdut.edu.datingforballsports.model.Listener;

import java.util.List;

import gdut.edu.datingforballsports.domain.Friend;
import gdut.edu.datingforballsports.domain.Post;

public interface FriendListener {
    void onSuccess(List<Friend> list);

    void onFails(String msg);
}
