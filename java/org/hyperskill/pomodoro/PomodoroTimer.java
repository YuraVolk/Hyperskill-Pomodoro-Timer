package org.hyperskill.pomodoro;

import android.app.Activity;
import android.widget.TextView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimer {
    private TextView time;
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
        this.time = (TextView) this.activity.findViewById(R.id.textView);
    }

    void countdown(int seconds, int interval) {
        this.seconds = seconds;
        time.setText(Integer.toString(seconds));

        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time.setText(Integer.toString(setInterval()));
                    }
                });
            }
        }, interval, interval);
    }
}
