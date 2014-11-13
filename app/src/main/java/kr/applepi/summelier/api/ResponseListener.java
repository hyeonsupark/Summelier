package kr.applepi.summelier.api;

import org.apache.http.HttpResponse;

public interface ResponseListener
{
	void onReponse(HttpResponse res) throws Exception;
}
