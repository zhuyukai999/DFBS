package gdut.edu.datingforballsports.domain;

public class ChatRoomMessage extends ChatMessage {
    private String publisherName;
    private String publisherLogo;

    public ChatRoomMessage() {
    }

    public ChatRoomMessage(int id, int otherOrChatRoomId, String content, String publishTime, int statue,int publisherId) {
        super(id, otherOrChatRoomId, content, publishTime, statue,publisherId);
    }

    public ChatRoomMessage(int id, int otherOrChatRoomId, String content, String publishTime, int statue,int publisherId, String publisherName, String publisherLogo) {
        super(id, otherOrChatRoomId, content, publishTime, statue,publisherId);
        this.publisherName = publisherName;
        this.publisherLogo = publisherLogo;
    }
}
