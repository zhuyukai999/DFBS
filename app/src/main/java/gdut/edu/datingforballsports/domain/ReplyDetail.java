package gdut.edu.datingforballsports.domain;

public class ReplyDetail {
    private String userName;
    private int publisherId;
    private String content;
    private int likeNum;
    private boolean ifLike;
    private String createTime;
    private int commentId;

    public ReplyDetail(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public ReplyDetail(String userName, int publisherId, String content, int likeNum, boolean ifLike, String createTime, int commentId) {
        this.userName = userName;
        this.publisherId = publisherId;
        this.content = content;
        this.likeNum = likeNum;
        this.ifLike = ifLike;
        this.createTime = createTime;
        this.commentId = commentId;
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

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getCommentId() {
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

    @Override
    public String toString() {
        return "ReplyDetail{" +
                "userName='" + userName + '\'' +
                ", publisherId=" + publisherId +
                ", content='" + content + '\'' +
                ", likeNum=" + likeNum +
                ", ifLike=" + ifLike +
                ", createTime='" + createTime + '\'' +
                ", commentId=" + commentId +
                '}';
    }
}
