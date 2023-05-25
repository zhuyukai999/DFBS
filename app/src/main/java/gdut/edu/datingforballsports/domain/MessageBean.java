package gdut.edu.datingforballsports.domain;

public class MessageBean {
    private int id;
    private int type;
    private int otherOrChatRoomId;
    private String otherOrChatRoomName;
    private String otherOrChatRoomLogo;

    private String publishTime;
    private String coverContent;
    private boolean read;

    public MessageBean() {
    }

    public MessageBean(int type, int otherOrChatRoomId, String otherOrChatRoomName, String otherOrChatRoomLogo, String publishTime, String coverContent) {
        this.type = type;
        this.otherOrChatRoomId = otherOrChatRoomId;
        this.otherOrChatRoomName = otherOrChatRoomName;
        this.otherOrChatRoomLogo = otherOrChatRoomLogo;
        this.publishTime = publishTime;
        this.coverContent = coverContent;
    }

    public MessageBean(int id, int type, int otherOrChatRoomId, String otherOrChatRoomName, String otherOrChatRoomLogo) {
        this.id = id;
        this.type = type;
        this.otherOrChatRoomId = otherOrChatRoomId;
        this.otherOrChatRoomName = otherOrChatRoomName;
        this.otherOrChatRoomLogo = otherOrChatRoomLogo;
    }

    public MessageBean(int type, int otherOrChatRoomId, String otherOrChatRoomName, String otherOrChatRoomLogo) {
        this.type = type;
        this.otherOrChatRoomId = otherOrChatRoomId;
        this.otherOrChatRoomName = otherOrChatRoomName;
        this.otherOrChatRoomLogo = otherOrChatRoomLogo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOtherOrChatRoomId() {
        return otherOrChatRoomId;
    }

    public void setOtherOrChatRoomId(int otherOrChatRoomId) {
        this.otherOrChatRoomId = otherOrChatRoomId;
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

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getCoverContent() {
        return coverContent;
    }

    public void setCoverContent(String coverContent) {
        this.coverContent = coverContent;
    }


}
