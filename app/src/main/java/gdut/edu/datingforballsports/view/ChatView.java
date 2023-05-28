package gdut.edu.datingforballsports.view;

import java.util.List;

import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.MessageBean;

public interface ChatView extends View_{
    void onChatMsgLoadSuccess(List<ChatMessage> list, String RCmsg);
    void onLoadFail(String RCmsg);
}
