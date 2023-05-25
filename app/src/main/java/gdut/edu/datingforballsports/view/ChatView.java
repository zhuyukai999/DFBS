package gdut.edu.datingforballsports.view;

import gdut.edu.datingforballsports.domain.MessageBean;

public interface ChatView extends View_{
    void onTrendsLoadSuccess(MessageBean list, String RCmsg);
    void onLoadFail(String RCmsg);
}
