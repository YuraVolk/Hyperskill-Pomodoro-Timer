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
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private PomodoroTimer timer;
    private TimerView timerView;
    final Context context = this;
    private EditText restTime;
    private EditText workTime;
    public static int workSeconds = 5;
    public static int breakSeconds = 3;

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
                            timerView.stop(workSeconds, false);
                        }

                        timerView.setColor(Color.RED);
                        timerView.start(workSeconds, true);
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
                        timerView.stop(workSeconds, true);
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
                restTime = (EditText) dialog.findViewById(R.id.restInput);
                workTime = (EditText) dialog.findViewById(R.id.workInput);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(dialog);



                alertDialogBuilder.setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                workSeconds = Integer.parseInt(workTime.getText().toString(), 10);
                                                breakSeconds = Integer.parseInt(restTime.getText().toString(), 10);
                                            }
                                        });

                alertDialogBuilder.setCancelable(false)
                        .setNegativeButton("Cancel", (dialogInterface, i) -> {});

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }
}
