package com.android.gametouch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.Collections;

public class Level1 extends AppCompatActivity {

    public RelativeLayout box_1, box_2, box_3, box_4;
    public GridView coursesGV;
    public int tapCount;
    String[] value1 = value1 = new String[]{"0", "1", "2", "3"};
    int ranNew;
    PreciseCountdownTimer preciseCountdownTimer = null;
    TextView countdown;
    boolean Isstart = true;
    boolean isCanceled = false;
    TextView levelTV;

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String tap = intent.getStringExtra("tapCount");
            String start = intent.getStringExtra("start");
            if (tap != null){
                tapCount = Integer.parseInt(tap);
            }

            if (start != null) {
                if (Isstart){
                    if (start.equals("startGame")) {
                        startTick();
                        Isstart = false;
                        Log.d("Startswitch", "iSsTART");
                    }
                }

            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countdown = findViewById(R.id.countdown);
        coursesGV = findViewById(R.id.idGVcourses);
        levelTV = findViewById(R.id.levelTV);
        levelTV.setText(" Level 1 ");

        CountDown countDown = new CountDown();
        countDown.showDialog(Level1.this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));


        ArrayList<Integer> number = new ArrayList<Integer>();


        ArrayList<CourseModel> courseModelArrayList = new ArrayList<CourseModel>();

        for (int i = 0; i < 4; ++i) {
            number.add(i);
            courseModelArrayList.add(new CourseModel(String.valueOf(i)));
        };


        Collections.shuffle(number);
        CourseGVAdapter adapter = new CourseGVAdapter(Level1.this, courseModelArrayList, number.get(0), 4);

        coursesGV.setAdapter(adapter);

    }

    void startTick() {
        preciseCountdownTimer = new PreciseCountdownTimer(5000, 1000, 200) {
            @Override
            public void onTick(long timeLeft) {
                long sec = timeLeft / 1000;

                Level1.this.runOnUiThread(new Runnable() {
                    public void run() {
                        countdown.setTextColor(ContextCompat.getColor(Level1.this, R.color.red));
                        countdown.setText(" "+String.valueOf(sec)+" ");
                    }
                });
            }

            @Override
            public void onFinished() {
                Level1.this.runOnUiThread(new Runnable() {
                    public void run() {
                        countdown.setText(" Finish! ");
                        Intent i = new Intent(Level1.this, GamePause.class);
                        i.putExtra("count", tapCount);
                        i.putExtra("level", 1);
                        Level1.this.startActivity(i);


                    }
                });

            }

            @Override
            public void onPaused() {

            }

            @Override
            public void onResumed() {

            }

        };
        preciseCountdownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (preciseCountdownTimer != null) {
            preciseCountdownTimer.cancel();
            preciseCountdownTimer = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Level1.this, LevelHome.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}