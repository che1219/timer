package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class TimeListActivity extends AppCompatActivity {

    private TimeListFragment timeListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_list);

        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        Bundle b1= getIntent().getExtras();

        ArrayList<String> info = b1.getStringArrayList("info");

        timeListFragment= (TimeListFragment) getSupportFragmentManager().findFragmentById(R.id.ListFrag);

        timeListFragment.setText(info);
    }

    public void timerSwitchClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);

    }
}