package org.hyperskill.pomodoro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private PomodoroTimer timer;
    private final int currentSeconds = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button startButton = findViewById(R.id.startButton);
        final Button resetButton = findViewById(R.id.resetButton);
        final TextView time =  findViewById(R.id.textView);
        timer = new PomodoroTimer(this);

        timer.reset(currentSeconds);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.countdown(currentSeconds, 1000);
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
                        timer.reset(currentSeconds);
                    }
                });
            }
        });
    }
}
