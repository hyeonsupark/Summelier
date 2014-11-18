package kr.applepi.summelier.review;

/**
 * Created by Hyunsu on 2014-11-13.
 */

public class ReviewData {

    public String name,
            text,
            profileUrl,
		    timestamp;

    public float rating;
	public int id;

	public ReviewData()
	{}

    public ReviewData(
		    String profileUrl,
		    String name,
		    String text,
		    String timestamp,
		    float rating)
    {
	    this.id = 0;
        this.timestamp = timestamp;
        this.profileUrl = profileUrl;
        this.text = text;
        this.name = name;
	    this.rating = rating;
    }
}
