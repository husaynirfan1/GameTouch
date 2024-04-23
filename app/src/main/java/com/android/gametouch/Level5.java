package com.android.gametouch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.Collections;

public class Level5 extends AppCompatActivity {
    public GridView coursesGV;
    public int tapCount;
    int ranNew;
    PreciseCountdownTimer preciseCountdownTimer = null;
    TextView countdown;
    boolean Isstart = true;

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
                        Log.d("Startswitch", "iSsTART5");
                    }
                }

            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level5);
        countdown = findViewById(R.id.countdown);
        coursesGV = findViewById(R.id.idGVcourses);
        CountDown countDown = new CountDown();
        countDown.showDialog(Level5.this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        ArrayList<Integer> number = new ArrayList<Integer>();
        ArrayList<CourseModel> courseModelArrayList = new ArrayList<CourseModel>();

        for (int i = 0; i < 36; ++i) {
            number.add(i);
            courseModelArrayList.add(new CourseModel(String.valueOf(i)));
        };
        Collections.shuffle(number);
        CourseGVAdapter adapter = new CourseGVAdapter(Level5.this, courseModelArrayList, number.get(0), 36);

        coursesGV.setAdapter(adapter);
    }

    void startTick() {
        preciseCountdownTimer = new PreciseCountdownTimer(5000, 1000, 200) {
            @Override
            public void onTick(long timeLeft) {
                long sec = timeLeft / 1000;

                Level5.this.runOnUiThread(new Runnable() {
                    public void run() {
                        countdown.setTextColor(ContextCompat.getColor(Level5.this, R.color.red));
                        countdown.setText(" "+String.valueOf(sec)+" ");
                    }
                });
            }

            @Override
            public void onFinished() {
                Level5.this.runOnUiThread(new Runnable() {
                    public void run() {
                        countdown.setText(" Finish! ");

                        Intent i = new Intent(Level5.this, GamePause.class);
                        i.putExtra("count", tapCount);
                        i.putExtra("level", 5);
                        Level5.this.startActivity(i);
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
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Level5.this, LevelHome.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);
    }
}