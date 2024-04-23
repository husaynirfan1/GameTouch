package com.android.gametouch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.android.gametouch.db.ScoreModal1;
import com.android.gametouch.db.ViewModalScore;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class LevelHome extends AppCompatActivity {

    public MaterialButton startButton, exitBtn;
    ViewModalScore viewModalScore;
    VideoView vidView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_home);
        vidView = findViewById(R.id.vidView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.loop);
        vidView.setVideoURI(uri);
        vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        vidView.start();

        startButton = findViewById(R.id.startButton);
        exitBtn = findViewById(R.id.exitBtn);

        viewModalScore = new ViewModelProvider(this).get(ViewModalScore.class);

        viewModalScore.findbyLeveL("Level 1").observe(LevelHome.this, new Observer<List<ScoreModal1>>() {
            @Override
            public void onChanged(List<ScoreModal1> scoreModal1s) {
                if (scoreModal1s != null) {
                    Log.d("adapterlist", String.valueOf(scoreModal1s.size()));
                }
            }
        });

        startButton.setOnClickListener(v -> {
            Intent i = new Intent(LevelHome.this, Level1.class);
            startActivity(i);
        });

        exitBtn.setOnClickListener(v -> {
            finish();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        vidView.start();
    }
}