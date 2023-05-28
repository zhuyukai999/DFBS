package gdut.edu.datingforballsports.domain;

import java.io.Serializable;

import java.util.List;

public class Post{
    private int id;
    private int publisherId;
    private String publisherName;
    private String publisherLogo;
    private String content;
    private int likeNum;
    private int commentNum;
    private boolean ifLike;
    private boolean ifCollect;
    private String createTime;
    private String ballType;
    private String city;
    private List<String> imagePaths;

    public Post() {
    }

    public Post(int publisherId, String publisherName, String publisherLogo, String content, int likeNum, int commentNum, boolean ifLike, boolean ifCollect, String createTime, List<String> imagePaths) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.publisherLogo = publisherLogo;
        this.content = content;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.ifLike = ifLike;
        this.ifCollect = ifCollect;
        this.createTime = createTime;
        this.imagePaths = imagePaths;
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

    public boolean isIfCollect() {
        return ifCollect;
    }

    public void setIfCollect(boolean ifCollect) {
        this.ifCollect = ifCollect;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", publisherId=" + publisherId +
                ", publisherName='" + publisherName + '\'' +
                ", publisherLogo='" + publisherLogo + '\'' +
                ", content='" + content + '\'' +
                ", likeNum=" + likeNum +
                ", commentNum=" + commentNum +
                ", ifLike=" + ifLike +
                ", ifCollect=" + ifCollect +
                ", createTime='" + createTime + '\'' +
                ", ballType='" + ballType + '\'' +
                ", city='" + city + '\'' +
                ", imagePaths=" + imagePaths +
                '}';
    }
}
