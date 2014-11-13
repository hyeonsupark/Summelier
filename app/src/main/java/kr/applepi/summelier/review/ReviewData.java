package kr.applepi.summelier.review;

/**
 * Created by Hyunsu on 2014-11-13.
 */

public class ReviewData {

    public String name,
            text,
            profileUrl;
    public float rating;

    public ReviewData(String profileUrl, String name, String text, float rating) {
        this.profileUrl = profileUrl;
        this.name = name;
        this.text = text;
        this.rating = rating;
    }


}
