package gdut.edu.datingforballsports.domain;

public class Post
{
    private int publisherId;
    private int postId;
    private String text;
    private String topic;
    private String imageUrl;
    private int likeNum;
    private int collectionNum;
    private int commentNum;

    public Post(int publisherId, int postId, String text, String topic, int likeNum, int collectionNum, int commentNum) {
        this.publisherId = publisherId;
        this.postId = postId;
        this.text = text;
        this.topic = topic;
        this.likeNum = likeNum;
        this.collectionNum = collectionNum;
        this.commentNum = commentNum;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        this.collectionNum = collectionNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
}
