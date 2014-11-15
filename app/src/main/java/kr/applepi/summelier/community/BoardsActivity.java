package kr.applepi.summelier.community;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import kr.applepi.summelier.R;
import kr.applepi.summelier.util.ActivityPlus;

public class BoardsActivity extends ActivityPlus {


    private ListView lvBoards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_boards);

        initUi();
    }

    private void initUi() {
        lvBoards = (ListView) view_(R.id.boards_lv);
        onClick(R.id.boards_btn_write, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BoardsActivity.this, WriteActivity.class));
            }
        });
    }
}
