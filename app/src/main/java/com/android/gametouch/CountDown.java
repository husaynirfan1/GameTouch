package com.android.gametouch;/*
 * *
 *  * Created by Husayn on 22/10/2021, 5:04 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 22/10/2021, 2:29 PM
 *
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.gametouch.db.ListingAdapter;
import com.android.gametouch.db.ScoreModal1;

import java.util.List;

public class CountDown {

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.countdownlayout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        TextView count = dialog.findViewById(R.id.countdown);


        PreciseCountdownTimer preciseCountdownTimer = new PreciseCountdownTimer(3000, 1000, 500) {
            @Override
            public void onTick(long timeLeft) {
                long sec = timeLeft / 1000;

                activity.runOnUiThread(new Runnable() {
                    public void run() {

                        count.setText(" "+String.valueOf((sec))+" ");

                    }
                });
            }

            @Override
            public void onFinished() {
                onTick(0);
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        count.setText(" Start! ");

                    }
                });

                Intent intent = new Intent("custom-message");
                intent.putExtra("start", "startGame");

                LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);

                dialog.dismiss();
            }

            @Override
            public void onPaused() {

            }

            @Override
            public void onResumed() {

            }

        };

        preciseCountdownTimer.start();

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }
}
