package gdut.edu.datingforballsports.view;

import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.MessageBean;

public interface ChatMessageView extends View_{
    void onTrendsLoadSuccess(MessageBean list, String RCmsg);
    void onLoadFail(String RCmsg);
}
