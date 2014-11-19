package kr.applepi.summelier.community;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyunsu on 2014-11-15.
 */
public class BoardData {

    public String profile, name, timestamp, text;
	public int id, authorId, commentsCount;
	public List<String> photos = new ArrayList<String>();

    public BoardData(String profile, String name, String timestamp, int commentsCount, String text) {
        this.profile = profile;
        this.name = name;
        this.timestamp = timestamp;
        this.commentsCount = commentsCount;
        this.text = text;
    }

	public BoardData(){}

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

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
