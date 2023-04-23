package gdut.edu.datingforballsports.model.Listener;

import java.util.List;

import gdut.edu.datingforballsports.domain.Post;

public interface ForumListListener {
    void onSuccess(List<Post> list, String msg);

    void onFails(String msg);
}
