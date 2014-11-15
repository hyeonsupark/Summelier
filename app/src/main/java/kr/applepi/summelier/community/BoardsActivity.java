package kr.applepi.summelier.community;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import kr.applepi.summelier.R;

public class BoardsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_boards);
    }

}
