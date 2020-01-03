package org.hyperskill.pomodoro;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private PomodoroTimer timer;
    private final int currentSeconds = 3;
    private TimerView timerView;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerView = (TimerView) findViewById(R.id.timer);
        timerView.setActivty(this);

        timerView.stop(3, false);
        final Button startButton = findViewById(R.id.startButton);
        final Button resetButton = findViewById(R.id.resetButton);
        final Button settingsButton = findViewById(R.id.settingsButton);

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
                        timerView.stop(currentSeconds, true);
                        timerView.clearAnimation();
                        Log.d("timer-stop", Boolean.toString(timerView.isRunning()));
                    }
                });
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View dialog = layoutInflater.inflate(R.layout.dialog_settings, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(dialog);

                alertDialogBuilder.setCancelable(false)
                                .setPositiveButton("Apply",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }
}
