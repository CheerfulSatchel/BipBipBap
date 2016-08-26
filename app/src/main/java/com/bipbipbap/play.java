package com.bipbipbap;

import java.util.Timer;
import java.util.TimerTask;
import metronome.*;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by James Wu on 7/10/2016.
 */
public class play extends Activity{

    private SeekBar seekBar;
    private TextView seekBarStatus;

    final int successColor = Color.GREEN;
    final int defaultColor = Color.WHITE;
    final int failColor = Color.RED;
    final int blinkColor = Color.YELLOW;
    int bpm = 45;
    //This is the lone metronome
    final Metronome metro = new Metronome(bpm);
    //This is the everchanging action star, hank
    Action hank;
    //Initial bpm is set to 45
    //Keepin track of the points won by the user
    int pointsCounter = 0;

    final Timer colorTimer = new Timer();
    final long colorDelay = 100; //Color will change back to default in 0.1 seconds

    //This is an arraylist holding the commands.
    ArrayList<Action> commands = new ArrayList<Action>();

    private void setActivityBackgroundColor(int color) {
        runOnUiThread(()-> {
            this.getWindow().getDecorView().setBackgroundColor(color);
        });
    }

    private void signalActivityBackgroundColor(int color) {
        setActivityBackgroundColor(color);
        colorTimer.schedule(new TimerTask() {
           public void run() {
               setActivityBackgroundColor(defaultColor);
           }
        }, colorDelay);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        Button tapButton = (Button) findViewById(R.id.button);
        Button exitButton = (Button) findViewById(R.id.exitButton);

        //Initialize the seekbar and its counter
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBarStatus = (TextView) findViewById(R.id.seekBarStatus);

        Log.v("AWWW YEEEEAAAAHH ", "POOP");

        System.out.println("LOOK:" + commands);

        seekBarStatus.setText("Level: " + seekBar.getProgress() + "/" + seekBar.getMax());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int level = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                level = progress;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Start tracking seekbar progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarStatus.setText("Level: " + level + "/" + seekBar.getMax());
                Toast.makeText(getApplicationContext(), "Stop tracking seekbar progress", Toast.LENGTH_SHORT).show();
            }
        });
        hank = new Action() {
            private boolean tapped = false;
            private boolean incrementTicks = false;
            private int ticks = 0;

            public void run() {
                System.out.println("IT'S THE SOCIAL-JUSTICE-MOBILE");

                if(incrementTicks){
                    int activityColor = blinkColor;

                    switch(++ticks) {
                        case 4:
                            tapped = false;
                            System.out.println("Tap the Whew Wee Button.");
                            tapButton.setOnClickListener((View v)-> {
                                tapped = true;
                                signalActivityBackgroundColor(successColor);
                            });
                            break;
                        case 8:
                            if (!tapped) {
                                System.out.println("FAILED");
                                activityColor = failColor;
                            }
                            tapButton.setOnClickListener((View v)-> {
                                signalActivityBackgroundColor(failColor);
                            });
                            ticks = 0;
                            break;
                    }
                    signalActivityBackgroundColor(activityColor);
                }

                incrementTicks = !incrementTicks;
                System.out.println("Yee! " + ticks);

            }
        };
        tapButton.setOnClickListener((View v) -> {
            signalActivityBackgroundColor(failColor);
        });

        // Return to MainActivity
        exitButton.setOnClickListener((View v) -> {
            metro.stop();
            colorTimer.cancel();
            this.startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        metro.addAction(hank);
    }
}
