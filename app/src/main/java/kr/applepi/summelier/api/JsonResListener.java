package kr.applepi.summelier.api;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonResListener implements ResponseListener
{
	ResultListener res;
	JSONObject json;

	public JsonResListener(ResultListener res)
	{
		this.res = res;
	}
	
	@Override
	public void onResponse(HttpResponse res) throws Exception
	{
		String text = null;
		try {
			text = EntityUtils.toString(
					res.getEntity(), "utf-8");
			json = new JSONObject(text);
		}
		catch(Exception e)
		{
			Log.d("응답", text);
			throw e;
		}
	}

	@Override
	public void onExecute() throws Exception {
		this.res.onResult(json.getBoolean("ok"), json);
	}
}
