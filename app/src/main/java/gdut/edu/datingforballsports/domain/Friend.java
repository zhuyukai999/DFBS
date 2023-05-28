package gdut.edu.datingforballsports.domain;

public class Friend {
    private int friendId;
    private String friendName;

    private String friendLogo;

    public Friend() {
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendLogo() {
        return friendLogo;
    }

    public void setFriendLogo(String friendLogo) {
        this.friendLogo = friendLogo;
    }
}
