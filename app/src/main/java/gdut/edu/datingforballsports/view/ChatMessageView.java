package gdut.edu.datingforballsports.view;

import java.util.List;

import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.MessageBean;

public interface ChatMessageView extends View_{
    void onLoadMessageBeanSuccess(List<MessageBean> list, String RCmsg);
    void onLoadFail(String RCmsg);
}
