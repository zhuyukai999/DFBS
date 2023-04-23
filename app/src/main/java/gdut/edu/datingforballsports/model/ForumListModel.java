package gdut.edu.datingforballsports.model;

import java.util.ArrayList;

import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.model.Listener.ForumListListener;

public class ForumListModel implements Model_ {
    private String msg = null;

    //TODO
    //待完善，个性化推荐论坛帖子
    public void getForumList(int userId, String token, ForumListListener forumListListener) {
        if (userId >= 1) {
            //在这里进行推荐啊啊啊
            ArrayList<Post> list = new ArrayList<>();
            forumListListener.onSuccess(list, msg);
            //TODO
        } else {
            forumListListener.onFails(msg);
        }

    }
}
