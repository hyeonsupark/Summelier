package kr.applepi.summelier.api;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonResListener implements ResponseListener
{
	ResultListener res;
	
	public JsonResListener(ResultListener res)
	{
		this.res = res;
	}
	
	@Override
	public void onResponse(HttpResponse res) throws Exception
	{
		String text = EntityUtils.toString(
				res.getEntity(), "utf-8"); 
		try {

			JSONObject json = new JSONObject(text);
			this.res.onResult(json.getBoolean("ok"), json);	
		}
		catch(JSONException e)
		{
			Log.d("Exception Occurred in dispatch result", text);
			e.printStackTrace();
		}
	}

}
