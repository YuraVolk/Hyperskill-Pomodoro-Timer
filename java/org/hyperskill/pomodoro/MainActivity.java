package org.hyperskill.pomodoro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        final Button startButton = findViewById(R.id.startButton);
        final Button resetButton = findViewById(R.id.resetButton);
      /*  final TextView time =  findViewById(R.id.textView);
        timer = new PomodoroTimer(this);

        timer.reset(currentSeconds);*/

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerView.start(currentSeconds);
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
                        timerView.start(currentSeconds);
                    }
                });
            }
        });


    }
}
