package kr.applepi.summelier.api;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Api {
    public static final String
            CONTRIBUTION = "http://applepi.kr/~summelier/contribution.json",
            AUTH = "http://applepi.kr/~summelier/app/auth.php",
            WELL = "http://applepi.kr/~summelier/app/well.php",
            RANK = "http://applepi.kr/~summelier/app/rank.php",
		    PROFILE = "http://applepi.kr/~summelier/app/profile.php",
		    BOARD = "http://applepi.kr/~summelier/app/board.php",
            PROFILE_UPLOAD = "http://applepi.kr/~summelier/app/profile_upload.php",
            PROFILE_IMAGE_DIR = "http://applepi.kr/~summelier/images/",


    PLATFORM_SUMMELIER = "summelier",
            PLATFORM_KAKAO = "kakao",
            PLATFORM_FACEBOOK = "facbook";


	/*
     * AUTH 기능 관련 메서드들 ***********************
	 *
	 */

    // 로그인(수믈리에)
    public void loginBySummelier(String id, String password, ResultListener res) {
        request(AUTH, "login")
                .and("platform", "summelier")
                .and("id", id)
                .and("password", password)
                .post(res);
    }

    // 로그인 확인
    public void loginCheck(ResultListener res) {
        request(AUTH, "check")
                .post(res);
    }

    // 회원가입
    public void registerBySummelier(
            String id,
            String password,
            String name,
            ResultListener res
    ) {
        request(AUTH, "register")
                .and("platform", PLATFORM_SUMMELIER)
                .and("id", id)
                .and("password", password)
                .and("name", name)
                .post(res);
    }

    // 로그아웃
    public void logout(ResultListener res) {
        request(AUTH, "logout")
                .post(res);
    }

	/*
	 *
	 *
	 * 약수터 관련 APISSSSSSSSS
	 *
	 *
	 */

    public void getWells(ResultListener res, int... wheres) {
        StringBuilder where = new StringBuilder();
        for (int i : wheres)
            where.append(i).append(',');
        if(where.length() > 0)
        {
	        where.deleteCharAt(where.length() - 1);
	        request(WELL, "get_wells")
		        .and("where", where.toString())
		        .post(res);
        }
    }

	public void getAllWells(ResultListener res){
		request(WELL, "get_wells")
				.post(res);
	}

    public void addComment(String content, float rate, int wellId,
                           ResultListener res) {
        request(WELL, "add_comment")
                .and("content", content)
                .and("rate", rate)
                .and("well_id", wellId)
                .post(res);
    }

    public void deleteComment(int commentId,
                              ResultListener res) {
        request(WELL, "delete_comment")
                .and("comment_id", commentId)
                .post(res);
    }

    public void getComments(int wellId,
                            ResultListener res) {
        request(WELL, "get_comments")
                .and("well_id", wellId)
                .post(res);
    }

	/*
	 *
	 *  랭킹 API
	 *
	 */

    public void getCommentRank(ResultListener res) {
        request(RANK, "rank_comments")
                .post(res);
    }

    /*
     *
     * 프로필 얻기
     *
     *
     */
    public void getProfileOf(int memberId, ResultListener res) {
	    request(PROFILE, "data")
			    .and("member_id", memberId)
			    .post(res);
    }
	public void getMyProfile(ResultListener res) {
		request(PROFILE, "me")
				.post(res);
	}


    /*
     *
     * 프사 업로드 ㅁㄴㅇ
     *
     *
     */
    public void uploadProfileImage(File file, ResultListener res) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("profile", file);

        postAsync(PROFILE_UPLOAD, builder.build(), new JsonResListener(res));
    }

	/*


				게시판ㅇㄴㅇㄴㅇㄴ

	  */
	public void getPosts(
			int index,
			int count,
	        ResultListener res
	)
	{
		request(BOARD, "get_posts")
				.and("index", index)
				.and("count", count)
				.post(res);
	}

	public void writePost(
	        String content,
	        ResultListener res
	)
	{
		request(BOARD, "write_post")
				.and("content", content)
				.post(res);
	}


	public void addPostComment(
			int postId,
			String content,
			ResultListener res
	)
	{
		request(BOARD, "add_comment")
				.and("post_id", postId)
				.and("content", content)
				.post(res);
	}

	public void deletePostComment(
			int commentId,
			ResultListener res
	)
	{
		request(BOARD, "add_comment")
				.and("comment_id", commentId)
				.post(res);
	}

	public void getPostComments(
			int postId,
			ResultListener res
	)
	{
		request(BOARD, "get_comments")
				.and("post_id", postId)
				.post(res);
	}


    ///
    ///
    //
    ///
    ///  아래는 Request / Response 관련 구역입니다.
    // 		걍 API 구현은 다 위에 있으니께 위로 가시오
    ////
    //
    ///
    //////


    String _encoding;
    Context _context;
    DefaultHttpClient _client;
    Charset _utf8;

    public String name, profileUrl;

	public static Api get(Context context)
	{
		api._context = context;
		return api;
	}
	private static Api api = new Api();

    private Api() {
        _encoding = "utf-8";
	    _context = null;
        _client = new DefaultHttpClient();
        _utf8 = Charset.forName("UTF-8");
    }


    public Builder request(String url, String method) {
        return new Builder(this, url, method);
    }

    public HttpResponse post(String url, Map<String, String> params) throws Exception {
        HttpPost post = new HttpPost(url);
        //post.setHeader("Content-Type",
        //       "application/x-www-form-urlencoded;charset=UTF-8");

        // Map에 값들을 미리 추가한다.
        JSONObject json = new JSONObject();
        for (Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            json.put(key, value);
        }

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        String jsonText = json.toString();
        Log.d("POST JSON", jsonText);

        pairs.add(new BasicNameValuePair(
                        "json",
                        jsonText
                )
        );

        HttpEntity entity = new UrlEncodedFormEntity(pairs, "UTF-8");

        post.setEntity(entity);

        return _client.execute(post);
    }


    public HttpResponse post(String url, HttpEntity entity) throws Exception {
        HttpPost post = new HttpPost(url);
        post.setEntity(entity);

        return _client.execute(post);
    }


    // 비동기로 문자열 키-값 쌍들로 이루어진 POST 요청을 보냄
    public void postAsync(
            final String url,
            final Map<String, String> params,
            ResponseListener res
    ) {
        AsyncHttpRequest req = new AsyncHttpRequest(
                new AsyncHttpRequest.Request() {
                    @Override
                    public HttpResponse request() throws Exception {
                        return post(url, params);
                    }

                    ;
                },
                res
        );

        req.execute();
    }

    // HttpEntity를 비동기로 POST요청하는것
    public void postAsync(
            final String url,
            final HttpEntity entity,
            ResponseListener res
    ) {
        AsyncHttpRequest req = new AsyncHttpRequest(
                new AsyncHttpRequest.Request() {
                    @Override
                    public HttpResponse request() throws Exception {
                        return post(url, entity);
                    }

                    ;
                },
                res
        );

        req.execute();
    }


    public HttpResponse get(String url, Map<String, String> params) throws Exception {
        StringBuilder text = new StringBuilder();
        text.append(url).append('?');

        // URL 생성
        for (Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            text.append(key).append('=').append(value).append('&');
        }


        HttpGet get = new HttpGet(text.toString());
        return _client.execute(get);
    }

    public HttpResponse get(String url) throws Exception {
        HttpGet get = new HttpGet(url);
        return _client.execute(get);
    }

    public void getAsync(
            final String url,
            final Map<String, String> params,
            ResponseListener res) {
        AsyncHttpRequest req = new AsyncHttpRequest(
                new AsyncHttpRequest.Request() {
                    @Override
                    public HttpResponse request() throws Exception {
                        return get(url, params);
                    }

                    ;
                },
                res
        );

        req.execute();
    }


    public void getAsync(
            final String url,
            ResponseListener res) {
        AsyncHttpRequest req = new AsyncHttpRequest(
                new AsyncHttpRequest.Request() {
                    @Override
                    public HttpResponse request() throws Exception {
                        return get(url);
                    }

                    ;
                },
                res
        );

        req.execute();
    }


    public String toString(HttpResponse res) {
        try {
            return EntityUtils.toString(res.getEntity(), _encoding);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

	/*
	public String toUnescaped(HttpResponse res)
	{
		return StringUtils.
	}
	*/

    public JSONObject toJson(HttpResponse res) {
        try {
            return new JSONObject(toString(res));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String string(String text) {
        return text;/*
		try
		{
			return new String(text.getBytes("EUC-KR"), _encoding);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}*/
    }
}