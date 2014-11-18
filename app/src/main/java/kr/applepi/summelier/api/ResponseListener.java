package kr.applepi.summelier.api;

import org.apache.http.HttpResponse;

public interface ResponseListener
{
	void onResponse(HttpResponse res) throws Exception;
}
