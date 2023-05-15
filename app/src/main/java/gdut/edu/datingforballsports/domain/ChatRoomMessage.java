package gdut.edu.datingforballsports.domain;

public class ChatRoomMessage extends ChatMessage {
    private int publisherId;
    private String publisherName;
    private String publisherLogo;

    public ChatRoomMessage() {
    }

    public ChatRoomMessage(int id, int otherOrChatRoomId, String content, String publishTime, int statue,int publisher) {
        super(id, otherOrChatRoomId, content, publishTime, statue,publisher);
    }

    public ChatRoomMessage(int id, int otherOrChatRoomId, String content, String publishTime, int statue,int publisher, int publisherId, String publisherName, String publisherLogo) {
        super(id, otherOrChatRoomId, content, publishTime, statue,publisher);
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.publisherLogo = publisherLogo;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherLogo() {
        return publisherLogo;
    }

    public void setPublisherLogo(String publisherLogo) {
        this.publisherLogo = publisherLogo;
    }
}
