package kr.applepi.summelier.community;

/**
 * Created by Hyunsu on 2014-11-15.
 */
public class BoardData {

    private String profile, name, timestamp, commentsCount, text;

    public BoardData(String profile, String name, String timestamp, String commentsCount, String text) {
        this.profile = profile;
        this.name = name;
        this.timestamp = timestamp;
        this.commentsCount = commentsCount;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getTimestamp() {
        return timestamp;

    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(String commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
