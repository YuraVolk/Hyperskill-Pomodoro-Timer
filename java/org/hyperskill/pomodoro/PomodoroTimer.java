package org.hyperskill.pomodoro;

import android.app.Activity;
import android.text.format.DateUtils;
import android.widget.TextView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimer {
    private TimerView time;
    private Activity activity;
    private int seconds;
    private Timer timer;

    private final int setInterval() {
        if (seconds == 1) {
            timer.cancel();
        }
        return --seconds;
    }

    public PomodoroTimer(Activity _activity) {
        this.activity = _activity;
        this.time = (TimerView) this.activity.findViewById(R.id.timer);
    }

    void reset(int seconds) {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        //time.setText(DateUtils.formatElapsedTime(seconds));
    }

    void countdown(int seconds, int interval) {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        this.seconds = seconds;
        time.setTime(DateUtils.formatElapsedTime(seconds));
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time.setTime(DateUtils.formatElapsedTime(setInterval()));
                    }
                });
            }
        }, interval, interval);
    }
}
