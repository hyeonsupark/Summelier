package kr.applepi.summelier.api;

import java.util.Map;
import java.util.TreeMap;

public class Builder {
    Api api;
    String url;
    Map<String, String> params = new TreeMap<String, String>();

    public Builder(Api api, String url, String method) {
        this.api = api;
        this.url = url;
        params.put("method", method);
    }

    public Builder and(String key, Object value) {
        params.put(key, value.toString());
        return this;
    }

    public void post(ResponseListener res) {
        api.postAsync(url, params, res);
    }

    public void get(ResponseListener res) {
        api.getAsync(url, params, res);
    }

    public void post(ResultListener res) {
        api.postAsync(url, params, new JsonResListener(res));
    }

    public void get(ResultListener res) {
        api.getAsync(url, params, new JsonResListener(res));
    }
}
