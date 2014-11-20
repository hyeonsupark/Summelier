package kr.applepi.summelier.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.applepi.summelier.R;
import kr.applepi.summelier.api.Api;
import kr.applepi.summelier.api.ResultListener;
import kr.applepi.summelier.util.ActivityPlus;

public class BoardsActivity extends ActivityPlus implements AdapterView.OnItemClickListener{


    private ListView lvBoards;
    private BoardAdapter boardAdapter;
    private ArrayList<BoardData> boardLists;
	Api api;
	int pageIndex = 0, postsInPage = 10, totalPost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_boards);

        initUi();
    }

	@Override
	protected void onResume() {
		super.onResume();
		refreshBoard();
	}

	public void refreshBoard()
	{
		boardAdapter.clear();
		loadPosts();
	}

    private void initUi() {
        lvBoards = (ListView) view_(R.id.boards_lv);
        lvBoards.setOnItemClickListener(this);

        boardLists = new ArrayList<BoardData>();
        boardAdapter = new BoardAdapter(BoardsActivity.this, R.layout.row_board, boardLists);
        lvBoards.setAdapter(boardAdapter);

	    onClick(R.id.boards_btn_write, new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    startActivity(new Intent(BoardsActivity.this, WriteActivity.class));
		    }
	    });
	    onClick(R.id.boards_btn_back, new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    onBackPressed();
		    }
	    });

	    onClick(R.id.BOARDS_NEXT, new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {

			    int newIndex = pageIndex + 1;
			    if(newIndex * postsInPage > totalPost) return;

			    pageIndex = newIndex;
			    refreshBoard();
			    text_(R.id.BOARDS_INDEX, "" + (newIndex + 1));
		    }
	    });
	    onClick(R.id.BOARDS_BEFORE, new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {

			    int newIndex = pageIndex - 1;
			    if(newIndex < 0) return;

			    pageIndex = newIndex;
			    refreshBoard();
			    text_(R.id.BOARDS_INDEX, "" + (newIndex + 1));
		    }
	    });
    }

	private void loadPosts()
	{
		api = Api.get(this);

		api.getPosts(pageIndex * postsInPage, postsInPage,
		new ResultListener() {
		@Override
		public void onResult(boolean ok, JSONObject res) throws Exception {
			if(!ok)
			{
				toast("게시물들을 얻어오지 못했습니다.", Toast.LENGTH_LONG);
				return;
			}

			totalPost = res.getInt("total_post");
			Log.d("총 게시물 수", totalPost + "");
			JSONArray posts = res.getJSONArray("posts");
			for(int i = 0; i < posts.length(); ++i)
				addPost(posts.getJSONObject(i));
			boardAdapter.notifyDataSetChanged();
		}
		});
	}

	private void addPost(JSONObject obj) throws JSONException
	{
		BoardData data = new BoardData();
		data.id = obj.getInt("id");
		data.authorId = obj.getInt("id_writer");
		data.profile = obj.getString("writer_profile_url");
		data.name = obj.getString("writer_name");
		data.text = obj.getString("content");
		data.timestamp = obj.getString("createdTime");
		data.commentsCount = obj.getInt("num_comments");

		JSONArray photoUrls = obj.getJSONArray("photos");
		for(int i = 0; i < photoUrls.length(); ++i)
			data.photos.add(photoUrls.getString(i));

		boardAdapter.add(data);
	}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("position", "position : " + position);
        Log.i("name", "name : " + boardLists.get(position).getName());

	    BoardData data = boardLists.get(position);

        Intent intent = new Intent(BoardsActivity.this, BoardActivity.class);
        intent.putExtra("profile", boardLists.get(position).getProfile());
        intent.putExtra("name", boardLists.get(position).getName());
        intent.putExtra("timestamp", boardLists.get(position).getTimestamp());
	    intent.putExtra("text", boardLists.get(position).getText());
	    intent.putExtra("post_id", data.id);

        startActivity(intent);
    }
}
