package gdut.edu.datingforballsports.domain;

public class MatchingItem {
    private int id;
    private int type;
    private int otherOrChatRoomId;
    private String otherOrChatRoomName;
    private String otherOrChatRoomLogo;

    private String coverContent;

    public MatchingItem(int id, int type, int otherOrChatRoomId, String otherOrChatRoomName, String otherOrChatRoomLogo, String coverContent) {
        this.id = id;
        this.type = type;
        this.otherOrChatRoomId = otherOrChatRoomId;
        this.otherOrChatRoomName = otherOrChatRoomName;
        this.otherOrChatRoomLogo = otherOrChatRoomLogo;
        this.coverContent = coverContent;
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

    public String getCoverContent() {
        return coverContent;
    }

    public void setCoverContent(String coverContent) {
        this.coverContent = coverContent;
    }
}
