package org.techtown.smim.ui.notifications;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.techtown.smim.R;

public class ExerciseTimer extends AppCompatActivity {

    private TextView countdownText;
    private ToggleButton start_stop;

    private CountDownTimer countDownTimer;

    private long time = 0;
    private long tempTime = 0;

    private boolean firstState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        countdownText = findViewById(R.id.secTextView);
        start_stop = findViewById(R.id.start_stop);

        start_stop = (ToggleButton) findViewById(R.id.start_stop);
        start_stop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled -> start
                    start_stop.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.red)
                    );

                    if (firstState) {
                        String second = countdownText.getText().toString();
                        time = (Long.parseLong(second) * 1000) + 1000;
                        countdownText.setText(Long.toString(time));
                    } else {
                        time = tempTime;
                    }
                    countDownTimer = new CountDownTimer(time, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            tempTime = millisUntilFinished;
                            updateTimer();
                        }

                        @Override
                        public void onFinish() {
                        }
                    }.start();
                    firstState = false;
                } else {
                    // The toggle is disabled -> pause
                    start_stop.setBackgroundDrawable(
                            getResources().
                                    getDrawable(R.drawable.yellow)
                    );


                    출처: https://bitsoul.tistory.com/37 [Happy Programmer~]
                    countDownTimer.cancel();
                }
            }

            private void updateTimer() {
                int seconds = (int) tempTime % 3600000 % 60000 / 1000;

                countdownText.setText(Integer.toString(seconds));
            }
        });
    }
}
