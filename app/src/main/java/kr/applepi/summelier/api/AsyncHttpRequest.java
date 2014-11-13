package kr.applepi.summelier.api;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;

public class AsyncHttpRequest extends AsyncTask<Void, Void, HttpResponse>
{
	public static interface Request 
	{
		HttpResponse request() throws Exception;
	}

	Request _req;
	ResponseListener _res;
	
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
			return _req.request();
		}
		catch (Exception e)
		{
			Log.d("Summelier REST API", "Request Failed, " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	protected void onPostExecute(HttpResponse result)
	{
		try
		{
			_res.onReponse(result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		super.onPostExecute(result);
	}

	
}
