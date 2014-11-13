package kr.applepi.summelier.api;

import org.json.JSONObject;

public interface ResultListener
{
	void onResult(boolean ok, JSONObject res) throws Exception;
}
