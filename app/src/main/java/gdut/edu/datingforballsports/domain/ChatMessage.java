package gdut.edu.datingforballsports.domain;

import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable {
    private int id;
    private String content;
    private String publishTime;
    private int type;

    private int otherOrChatRoomId;
    private String otherOrChatRoomName;
    private String otherOrChatRoomLogo;

    private int publisherId;
    private String publisherName;
    private String publisherLogo;

    public ChatMessage() {
    }

    public ChatMessage(int otherOrChatRoomId, String content, String publishTime, int type, int publisherId, String otherOrChatRoomName, String publisherName) {
        this.otherOrChatRoomId = otherOrChatRoomId;
        this.content = content;
        this.publishTime = publishTime;
        this.type = type;
        this.publisherId = publisherId;
        this.otherOrChatRoomName = otherOrChatRoomName;
        this.publisherName = publisherName;
    }

    public ChatMessage(int otherOrChatRoomId, String content, String publishTime, int type, int publisherId, String otherOrChatRoomName, String otherOrChatRoomLogo, String publisherName, String publisherLogo) {
        this.otherOrChatRoomId = otherOrChatRoomId;
        this.content = content;
        this.publishTime = publishTime;
        this.type = type;
        this.publisherId = publisherId;
        this.otherOrChatRoomName = otherOrChatRoomName;
        this.otherOrChatRoomLogo = otherOrChatRoomLogo;
        this.publisherName = publisherName;
        this.publisherLogo = publisherLogo;
    }

    public ChatMessage(int id, int otherOrChatRoomId, String content, String publishTime, int type, int publisherId) {
        this.id = id;
        this.otherOrChatRoomId = otherOrChatRoomId;
        this.content = content;
        this.publishTime = publishTime;
        this.type = type;
        this.publisherId = publisherId;
    }

    public ChatMessage(int id, int otherOrChatRoomId, String content, String publishTime, int type, int publisherId, String publisherName, String publisherLogo) {
        this.id = id;
        this.otherOrChatRoomId = otherOrChatRoomId;
        this.content = content;
        this.publishTime = publishTime;
        this.type = type;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.publisherLogo = publisherLogo;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
