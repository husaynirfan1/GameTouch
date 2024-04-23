package com.android.gametouch;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gametouch.db.ListingAdapter;
import com.android.gametouch.db.ScoreModal1;
import com.android.gametouch.db.ViewModalScore;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamePause extends AppCompatActivity {

    public TextView countnumber;
    List<ScoreModal1> listScore;
    ViewModalScore viewModalScore;
    ListingAdapter adapter;
    RecyclerView scoreRV;
    TextView top25TV, levelTV;
    EditText inputUsername;
    MaterialButton submitBtn, nextLevel, homeBtn, viewScoreboard, restartLevel;
    int lvl;
    LinearLayout bottomLayout;
    boolean added = false;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_pause);
        bottomLayout = findViewById(R.id.bottomLayout);
        restartLevel = findViewById(R.id.restartLevel);
        viewScoreboard = findViewById(R.id.viewScoreboard);
        levelTV = findViewById(R.id.levelTV);
        top25TV = findViewById(R.id.top25TV);
        inputUsername = findViewById(R.id.inputUsername);
        submitBtn = findViewById(R.id.submitBtn);
        nextLevel = findViewById(R.id.nextLevel);
        countnumber = findViewById(R.id.countnumber);

        Intent intentsss = new Intent("custom-message");
        intentsss.putExtra("start", "stopGame");
        Intent intent = getIntent();
        Integer count = intent.getIntExtra("count", 0);

        LocalBroadcastManager.getInstance(GamePause.this).sendBroadcast(intentsss);

        Intent intent1 = getIntent();
        lvl = intent1.getIntExtra("level", 0);

        if (lvl != 0) {
            Log.d("levelcount", String.valueOf(lvl));

            levelTV.setText("Level " + String.valueOf(lvl) + " Scoreboard");
            top25TV.setText("You are in Top 25 in Level "+lvl+" !");

            countnumber.setText(String.valueOf(count) + " Tap in Level " + lvl + " !");

            restartLevel.setOnClickListener(v -> {
                Intent i = new Intent();
                i.setClassName(GamePause.this, "com.android.gametouch.Level"+String.valueOf(lvl));
                startActivity(i);
            });
            if (lvl == 5){
                nextLevel.setVisibility(View.GONE);
            }
        }


        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(view -> {
            Intent i = new Intent(GamePause.this, LevelHome.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });

        viewModalScore = new ViewModelProvider(this).get(ViewModalScore.class);

        viewModalScore.findbyLeveL("Level " + lvl).observe(GamePause.this, new Observer<List<ScoreModal1>>() {
            @Override
            public void onChanged(List<ScoreModal1> scoreModal1s) {
                if (scoreModal1s != null) {
                    if (!scoreModal1s.isEmpty()){
                        viewScoreboard.setOnClickListener(v -> {
                            DialogCustom dialogCustom = new DialogCustom();
                            dialogCustom.showDialog(GamePause.this, lvl, scoreModal1s);
                        });

                        ArrayList<Integer> scoreList = new ArrayList<>();
                        for (int i = 0; i < scoreModal1s.size(); i++) {
                            scoreList.add(Integer.parseInt(scoreModal1s.get(i).getScore()));
                        }
                        Log.d("scorelistttt", scoreList.toString());
                        int max = Collections.max(scoreList);
                        int min = Collections.min(scoreList);

                        // store the length of the ArrayList in variable n
                        int n = scoreList.size();
                        Log.d("sizeeee", String.valueOf(n));

                        if (n < 25) {
                            if (count != 0) {

                                if (added){
                                    bottomLayout.setVisibility(View.GONE);
                                } else {
                                    playSound(R.raw.congratssound);
                                    bottomLayout.setVisibility(View.VISIBLE);

                                }

                                submitBtn.setOnClickListener(v -> {

                                    String username = inputUsername.getText().toString();
                                    if (!username.equals("")) {
                                        ScoreModal1 modal1 = new ScoreModal1(username, String.valueOf(count), "Level " + lvl);
                                        viewModalScore.insert(modal1);

                                        added = true;

                                    } else
                                        Toast.makeText(GamePause.this, "Enter username !", Toast.LENGTH_SHORT).show();
                                });
                            }

                        } else {
                            if (count > min || count > max) {
                                if (added){
                                    bottomLayout.setVisibility(View.GONE);
                                } else {
                                    playSound(R.raw.congratssound);
                                    bottomLayout.setVisibility(View.VISIBLE);

                                }
                                Log.d("counterr", min + " : " + max + " : " + count);
                                submitBtn.setOnClickListener(v -> {

                                    String username = inputUsername.getText().toString();
                                    if (!username.equals("")) {
                                        ScoreModal1 modal1 = new ScoreModal1(username, String.valueOf(count), "Level " + lvl);
                                        viewModalScore.insert(modal1);
                                        added = true;


                                    } else
                                        Toast.makeText(GamePause.this, "Enter username !", Toast.LENGTH_SHORT).show();
                                });
                            } else {
                                playSound(R.raw.losing);

                            }
                        }

                    } else {
                        if (count != 0){
                            if (added){
                                bottomLayout.setVisibility(View.GONE);
                            } else {
                                playSound(R.raw.congratssound);
                                bottomLayout.setVisibility(View.VISIBLE);

                            }
                            submitBtn.setOnClickListener(v -> {

                                String username = inputUsername.getText().toString();
                                if (!username.equals("")) {
                                    ScoreModal1 modal1 = new ScoreModal1(username, String.valueOf(count), "Level " + lvl);
                                    viewModalScore.insert(modal1);
                                    added = true;


                                } else
                                    Toast.makeText(GamePause.this, "Enter username !", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            playSound(R.raw.losing);
                        }

                    }



                    Log.d("adapterlist", String.valueOf(scoreModal1s.size()));
                }
            }
        });




        nextLevel.setOnClickListener(v -> {
            getIntent().removeExtra("count");
            getIntent().removeExtra("level");
            Intent i;
            if (lvl == 1) {
                i = new Intent(GamePause.this, Level2.class);
                startActivity(i);
            } else if (lvl == 2) {
                i = new Intent(GamePause.this, Level3.class);
                startActivity(i);
            } else if (lvl == 3) {
                i = new Intent(GamePause.this, Level4.class);
                startActivity(i);
            }else if (lvl == 4) {
                i = new Intent(GamePause.this, Level5.class);
                startActivity(i);
            }


        });

    }
    public void playSound(int _id)
    {
        if(mp != null)
        {
            mp.stop();
            mp.release();
        }
        mp = MediaPlayer.create(this, _id);
        if(mp != null)
            mp.start();
    }
}