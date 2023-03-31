package gdut.edu.datingforballsports.model;

import java.util.List;

import gdut.edu.datingforballsports.domain.Post;
import gdut.edu.datingforballsports.model.Lisentener.ForumListListener;

public class ForumListModel implements Model_{
    //TODO
    //待完善，个性化推荐论坛帖子
    public List<Post> getForumList(int userId, ForumListListener forumListListener){
        if(userId !=-1){
            forumListListener.onSuccess();
            //TODO
            //在这里进行推荐啊啊啊
            return null;
        }else{
            forumListListener.onFails();
            return null;
        }

    }
}
