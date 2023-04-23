package gdut.edu.datingforballsports.domain;

import java.util.Date;
import java.util.List;

public class Post
{
    private int id;
    private int publisherId;
    private String content;
    private int likeNum;
    private int commentNum;
    private Date createTime;
    private List<String> imagePaths;

    public Post() {
    }

    public Post(int publisherId, String content, int likeNum, int commentNum, Date createTime, List<String> imagePaths) {
        this.publisherId = publisherId;
        this.content = content;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }
}
