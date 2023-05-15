package gdut.edu.datingforballsports.view;

import java.util.List;

public interface ForumListView extends View_{
    String getToken();
    int getUserId();
    void onForumListLoadSuccess(Object list, String RCmsg);
    void onForumListLoadFail(String RCmsg);
}
