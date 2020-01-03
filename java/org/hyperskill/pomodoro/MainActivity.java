package org.hyperskill.pomodoro;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private PomodoroTimer timer;
    private final int currentSeconds = 3;
    private TimerView timerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerView = (TimerView) findViewById(R.id.timer);
        timerView.setActivty(this);

        timerView.stop(3, false);
        final Button startButton = findViewById(R.id.startButton);
        final Button resetButton = findViewById(R.id.resetButton);
      /*  final TextView time =  findViewById(R.id.textView);
        timer = new PomodoroTimer(this);

        timer.reset(currentSeconds);*/
        timerView.setColor(Color.YELLOW);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (timerView.isRunning()) {
                            return;
                        } else {
                            timerView.stop(currentSeconds, false);
                        }

                        timerView.setColor(Color.RED);
                        timerView.start(currentSeconds, true);
                     //   timer.countdown(currentSeconds, 1000);
                    }
                });
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //timer.reset(currentSeconds);

                        timerView.stop(currentSeconds, true);
                        timerView.clearAnimation();
                        Log.d("timer-stop", Boolean.toString(timerView.isRunning()));
                    }
                });
            }
        });


    }
}
