package kr.applepi.summelier.community;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import kr.applepi.summelier.R;
import kr.applepi.summelier.util.ActivityPlus;

public class BoardsActivity extends ActivityPlus implements AdapterView.OnItemClickListener{


    private ListView lvBoards;
    private BoardAdapter boardAdapter;
    private ArrayList<BoardData> boardLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_boards);

        initUi();
    }

    private void initUi() {
        lvBoards = (ListView) view_(R.id.boards_lv);
        lvBoards.setOnItemClickListener(this);

        boardLists = new ArrayList<BoardData>();
        boardAdapter = new BoardAdapter(BoardsActivity.this, R.layout.row_board, boardLists);
        lvBoards.setAdapter(boardAdapter);

        boardLists.add(new BoardData("http://applepi.kr/~summelier/images/empty.png", "박현수", "타임스탬프", "댓글 10개", "으아아아 김희규유우우루욺ㅇ누"));
        boardLists.add(new BoardData("http://applepi.kr/~summelier/images/empty.png", "박현수", "타임스탬프", "댓글 9개", "으아아아 sdfasdfsdfa"));
        boardAdapter.notifyDataSetChanged();

        onClick(R.id.boards_btn_write, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BoardsActivity.this, WriteActivity.class));
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("position", "position : " + position);
        Log.i("name", "name : " + boardLists.get(position).getName());

        Intent intent = new Intent(BoardsActivity.this, BoardActivity.class);
        intent.putExtra("profile", boardLists.get(position).getProfile());
        intent.putExtra("name", boardLists.get(position).getName());
        intent.putExtra("timestamp", boardLists.get(position).getTimestamp());
        intent.putExtra("text", boardLists.get(position).getText());

        startActivity(intent);
    }
}
