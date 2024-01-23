package com.example.checklist;

import android.os.Handler;

public class Throttler {
    public Runnable runnable;
    private Handler handler;
    private int delay;
    private boolean isRunning = false;
    public Throttler(Runnable runnable, int delay){
        this.delay = delay;
        this.runnable = runnable;
        this.handler = new Handler();

    }
    public void execute(){
        if(!isRunning){
            isRunning = true;
            runnable.run();
            handler.postDelayed(()->{
                isRunning = false;
            },delay);
        }
    }
}
