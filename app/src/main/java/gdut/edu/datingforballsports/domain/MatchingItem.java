package gdut.edu.datingforballsports.domain;

import java.util.List;

public class MatchingItem {
    private int id;
    private int requireNum;
    private int currentNum;
    private int publisherId;
    private int otherOrChatRoomId;
    private String otherOrChatRoomName;
    private String otherOrChatRoomLogo;

    private String coverContent;
    private List<Integer> memberList;

    private String ballType;
    private String city;

    public MatchingItem() {
    }

    public MatchingItem(int requireNum, int currentNum, int publisherId, String ballType, String city) {
        this.requireNum = requireNum;
        this.currentNum = currentNum;
        this.publisherId = publisherId;
        this.ballType = ballType;
        this.city = city;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    public String getBallType() {
        return ballType;
    }

    public void setBallType(String ballType) {
        this.ballType = ballType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public List<Integer> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Integer> memberList) {
        this.memberList = memberList;
    }

    public int getRequireNum() {
        return requireNum;
    }

    public void setRequireNum(int requireNum) {
        this.requireNum = requireNum;
    }
}
