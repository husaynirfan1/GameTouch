package com.android.gametouch;/*
 * *
 *  * Created by Husayn on 22/10/2021, 5:04 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 22/10/2021, 2:29 PM
 *
 */

import java.util.Timer;
import java.util.TimerTask;

public abstract class PreciseCountdownTimer extends Timer {

    private long totalTime, interval, delay;
    private TimerTask task;
    private long startTime = -1;
    private long timeLeft;
    private boolean restart = false;
    private boolean wasCancelled = false;
    private boolean wasStarted = false;

    public PreciseCountdownTimer(long totalTime, long interval) {
        this(totalTime, interval, 0);
    }


    public PreciseCountdownTimer(long totalTime, long interval, long delay ) {
        super("PreciseCountdownTimer", true);
        this.delay = delay;
        this.interval = interval;
        this.totalTime = totalTime;
        this.task = buildTask(totalTime);
    }

    private TimerTask buildTask(final long totalTime) {
        return new TimerTask() {

            @Override
            public void run() {
                if (startTime < 0 || restart) {
                    startTime = scheduledExecutionTime();
                    timeLeft = totalTime;
                    restart = false;
                } else {
                    timeLeft = totalTime - (scheduledExecutionTime() - startTime);

                    if (timeLeft <= 0) {
                        this.cancel();
                        wasCancelled = true;
                        startTime = -1;
                        onFinished();
                        return;
                    }
                }

                onTick(timeLeft);
            }
        };
    }

    public void start() {
        wasStarted = true;
        this.scheduleAtFixedRate(task, delay, interval);
    }

    public void stop() {
        this.wasCancelled = true;
        this.task.cancel();
    }

    public void restart() {
        if (!wasStarted) {
            start();
        } else if (wasCancelled) {
            wasCancelled = false;
            this.task = buildTask(totalTime);
            start();
        } else {
            this.restart = true;
        }
    }

    public void pause(){
        wasCancelled = true;
        this.task.cancel();
        onPaused();
    }

    public void resume(){
        wasCancelled = false;
        this.task = buildTask(timeLeft);
        this.startTime = - 1;
        start();
        onResumed();
    }

    // Call this when there's no further use for this timer
    public void dispose() {
        this.cancel();
        this.purge();
    }

    public abstract void onTick(long timeLeft);

    public abstract void onFinished();

    public abstract void onPaused();

    public abstract void onResumed();
}
