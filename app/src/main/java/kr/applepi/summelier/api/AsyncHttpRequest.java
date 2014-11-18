package kr.applepi.summelier.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;

public class AsyncHttpRequest extends AsyncTask<Void, Void, HttpResponse>
{
	public static interface Request 
	{
		HttpResponse request() throws Exception;
	}

	Context ctx;
	Request _req;
	ResponseListener _res;

	public AsyncHttpRequest(Context ctx, Request req, ResponseListener res)
	{
		this.ctx = ctx;
		_req = req;
		_res = res;
	}
	public AsyncHttpRequest(Request req, ResponseListener res)
	{
		_req = req;
		_res = res;
	}
	
	
	@Override
	protected HttpResponse doInBackground(Void... params)
	{
		try
		{
			HttpResponse res = _req.request();
			return res;
		}
		catch (Exception e)
		{
			Log.d("Summelier REST API", "Request Failed, " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(HttpResponse httpResponse) {
		try {
			_res.onResponse(httpResponse);
		}
		catch(Exception e) {
			Log.d("Summelier REST API", "응답을 처리 중 예외가 발생했습니다.");
			e.printStackTrace();
		}
	}
}
