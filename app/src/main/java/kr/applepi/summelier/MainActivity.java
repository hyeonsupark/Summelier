package kr.applepi.summelier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    private ImageButton btnRegional, btnLoaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initUi();
    }

    private void initUi() {
        btnRegional = (ImageButton) findViewById(R.id.main_btn_regional);
        btnLoaction = (ImageButton) findViewById(R.id.main_btn_location);

        btnRegional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
	            Intent mIntent = new Intent(MainActivity.this, RegionActivity.class);
	            startActivity(mIntent);
            }
        });

        btnLoaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(mIntent);
            }
        });

    }

}
