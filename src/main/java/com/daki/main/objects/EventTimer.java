package com.daki.main.objects;

import com.daki.main.christmas.TimerRunnable;

import java.util.Date;

public class EventTimer {
    private long startTime;
    private long endTime;
    private TimerRunnable tr;

    public EventTimer(long duration){
        startTime = new Date().toInstant().getEpochSecond();
        endTime = startTime + duration;
    }

    public int getRemainingTime(){
        long remainingTime = endTime - new Date().toInstant().getEpochSecond();
        return remainingTime > 0 ? (int) remainingTime : 0;
    }

    public void startTimer(){
        if (tr != null){
            tr.stopTimer();
        }
        tr = new TimerRunnable(this);
        tr.start();
    }

    public void stopTimer(){
        if (tr != null){
            tr.stopTimer();
        }
    }
}
