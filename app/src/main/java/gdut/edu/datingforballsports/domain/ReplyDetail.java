package gdut.edu.datingforballsports.domain;

public class ReplyDetail {
    private String userName;
    private int publisherId;
    private String content;
    private int likeNum;
    private boolean ifLike;
    private String createTime;
    private String commentId;

    public ReplyDetail(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }
    public int getPublisherId() {
        return publisherId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
    public String getCommentId() {
        return commentId;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public boolean isIfLike() {
        return ifLike;
    }

    public void setIfLike(boolean ifLike) {
        this.ifLike = ifLike;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getCreateTime() {
        return createTime;
    }
}
