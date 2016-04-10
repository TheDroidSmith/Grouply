package com.firsttread.grouply.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.firsttread.grouply.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton single_btn;
    private ImageButton multi_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        single_btn = (ImageButton) findViewById(R.id.singleButton);
        multi_btn = (ImageButton) findViewById(R.id.multiButton);

        single_btn.setOnClickListener(launchSingleGroupActivity);
        multi_btn.setOnClickListener(launchMultiGroupActivity);

    }




    private View.OnClickListener launchSingleGroupActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,SingleGroup.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener launchMultiGroupActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this,MultiGroup.class);
            startActivity(intent);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
