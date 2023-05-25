package gdut.edu.datingforballsports.dao;

import java.util.List;

import gdut.edu.datingforballsports.domain.ChatMessage;
import gdut.edu.datingforballsports.domain.ChatRoomMessage;
import gdut.edu.datingforballsports.domain.MessageBean;

public interface MessageDao {
    public long insertMessageBean(MessageBean messageBean);
    public long deleteMessageBean(int id);
    public List<MessageBean> getAllMessageBean();
    public int getMessageBeanCountByIdAndType(int type,int otherOrChatRoomId);

    public long insertChatMessage(ChatMessage chatMessage);
    public long deleteChatMessage(int otherId);
    public List<ChatMessage> getChatMessage(int otherId);

    public long insertChatRoomMessage(ChatMessage chatRoomMessage);
    public long deleteChatRoomMessage(int chatRoomId);
    public List<ChatMessage> getChatRoomMessage(int chatRoomId);

    public void closeDb();
}
