package gdut.edu.datingforballsports.domain;

import java.util.List;

public class CommentDetail {
    private int id;
    private int publisherId;
    private String publisherName;
    private String publisherLogo;
    private String content;
    private int likeNum;
    private int commentNum;
    private boolean ifLike;
    private String createTime;
    private List<ReplyDetail> replyList;

    public CommentDetail() {
    }

    public CommentDetail(int id, int publisherId, String publisherName, String publisherLogo, String content, int likeNum, int commentNum, boolean ifLike, String createTime, List<ReplyDetail> replyList) {
        this.id = id;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.publisherLogo = publisherLogo;
        this.content = content;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.ifLike = ifLike;
        this.createTime = createTime;
        this.replyList = replyList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public boolean isIfLike() {
        return ifLike;
    }

    public void setIfLike(boolean ifLike) {
        this.ifLike = ifLike;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<ReplyDetail> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyDetail> replyList) {
        this.replyList = replyList;
    }
}
