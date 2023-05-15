package gdut.edu.datingforballsports.domain;

public class ChatMessage {
    private int id;
    private int otherOrChatRoomId;
    private String content;
    private String publishTime;
    private int statue;
    private int publisher;

    private String otherOrChatRoomName;
    private String otherOrChatRoomLogo;

    public ChatMessage() {
    }

    public ChatMessage(int id,int otherOrChatRoomId, String content, String publishTime,int statue,int publisher) {
        this.id = id;
        this.otherOrChatRoomId = otherOrChatRoomId;
        this.content = content;
        this.publishTime = publishTime;
        this.statue = statue;
        this.publisher = publisher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOtherOrChatRoomId() {
        return otherOrChatRoomId;
    }

    public void setOtherOrChatRoomId(int otherOrChatRoomId) {
        this.otherOrChatRoomId = otherOrChatRoomId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getOtherOrChatRoomName() {
        return otherOrChatRoomName;
    }

    public void setOtherOrChatRoomName(String otherOrChatRoomName) {
        this.otherOrChatRoomName = otherOrChatRoomName;
    }

    public String getOtherOrChatRoomLogo() {
        return otherOrChatRoomLogo;
    }

    public void setOtherOrChatRoomLogo(String otherOrChatRoomLogo) {
        this.otherOrChatRoomLogo = otherOrChatRoomLogo;
    }

    public int getStatue() {
        return statue;
    }

    public void setStatue(int statue) {
        this.statue = statue;
    }

    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }
}
